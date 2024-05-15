/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.playerindicators;

import com.google.inject.Provides;
import lombok.Value;
import net.runelite.api.*;
import net.runelite.api.clan.ClanTitle;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ChatIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static net.runelite.api.FriendsChatRank.UNRANKED;
import static net.runelite.api.MenuAction.*;

@PluginDescriptor(
        name = "Player Indicators",
        description = "Highlight players on-screen and/or on the minimap",
        tags = {"highlight", "minimap", "overlay", "players"}
)
public class PlayerIndicatorsPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private PlayerIndicatorsConfig config;

    @Inject
    private PlayerIndicatorsOverlay playerIndicatorsOverlay;

    @Inject
    private PlayerIndicatorsTileOverlay playerIndicatorsTileOverlay;

    @Inject
    private PlayerIndicatorsMinimapOverlay playerIndicatorsMinimapOverlay;

    @Inject
    private PlayerIndicatorsService playerIndicatorsService;

    @Inject
    private Client client;

    @Inject
    private ChatIconManager chatIconManager;

    @Provides
    PlayerIndicatorsConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PlayerIndicatorsConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(playerIndicatorsOverlay);
        overlayManager.add(playerIndicatorsTileOverlay);
        overlayManager.add(playerIndicatorsMinimapOverlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(playerIndicatorsOverlay);
        overlayManager.remove(playerIndicatorsTileOverlay);
        overlayManager.remove(playerIndicatorsMinimapOverlay);
    }

    //@Subscribe
    public void onClientTick(ClientTick clientTick) {
        if (client.isMenuOpen()) {
            return;
        }

        MenuEntry[] menuEntries = client.getMenuEntries();
        if (menuEntries == null)
            return;
        boolean modified = false;

        for (MenuEntry entry : menuEntries) {
            int type = entry.getType().getId();

            if (type >= MENU_ACTION_DEPRIORITIZE_OFFSET) {
                type -= MENU_ACTION_DEPRIORITIZE_OFFSET;
            }

            if (type == WALK.getId()
                    || type == SPELL_CAST_ON_PLAYER.getId()
                    || type == ITEM_USE_ON_PLAYER.getId()
                    || type == PLAYER_FIRST_OPTION.getId()
                    || type == PLAYER_SECOND_OPTION.getId()
                    || type == PLAYER_THIRD_OPTION.getId()
                    || type == PLAYER_FOURTH_OPTION.getId()
                    || type == PLAYER_FIFTH_OPTION.getId()
                    || type == PLAYER_SIXTH_OPTION.getId()
                    || type == PLAYER_SEVENTH_OPTION.getId()
                    || type == PLAYER_EIGTH_OPTION.getId()
                    || type == RUNELITE_PLAYER.getId()) {
                Player[] players = client.getCachedPlayers();
                Player player = null;

                int identifier = (int) entry.getIdentifier();

                // 'Walk here' identifiers are offset by 1 because the default
                // identifier for this option is 0, which is also a player index.
                if (type == WALK.getId()) {
                    identifier--;
                }

                if (identifier >= 0 && identifier < players.length) {
                    player = players[identifier];
                }

                if (player == null) {
                    continue;
                }

                Decorations decorations = getDecorations(player);

                if (decorations == null) {
                    continue;
                }

                String oldTarget = entry.getTarget();
                String newTarget = decorateTarget(oldTarget, decorations);

                entry.setTarget(newTarget);
                modified = true;
            }
        }

        if (modified) {
            client.setMenuEntries(menuEntries);
        }
    }

    // Use a higher priority so that player menu entries added by other sources are added to the player's submenu
    @Subscribe(priority = 1)
    public void onMenuOpened(MenuOpened event)
    {
        if (config.condensePlayerOptions())
        {
            condensePlayerOptions();
        }
    }

    private void condensePlayerOptions()
    {
        MenuEntry[] menuEntries = client.getMenuEntries();
        MenuEntry parentMenu = null;
        Player prev = null;
        for (int i = menuEntries.length - 1; i >= 0; --i)
        {
            MenuEntry menuEntry = menuEntries[i];
            MenuAction type = menuEntry.getType();

            Player player = menuEntry.getPlayer();
            if (player != null && type != MenuAction.ITEM_USE_ON_PLAYER)
            {
                System.out.println(player.getName());
                if (prev != player)
                {
                    // This works by making the top most player menu the submenu, then adding a new
                    // menu with a copy of what this one was.
                    MenuEntry copy = client.createMenuEntry(-1)
                            .setIdentifier(menuEntry.getIdentifier())
                            .setOption(menuEntry.getOption())
                            .setTarget(menuEntry.getTarget())
                            .setType(menuEntry.getType())
                            .setParam0(menuEntry.getParam0())
                            .setParam1(menuEntry.getParam1())
                            .setDeprioritized(menuEntry.isDeprioritized());
                    System.out.println(copy.getOption() + "/" + copy.getType());
                    menuEntry.setOption("");
                    menuEntry.setType(MenuAction.RUNELITE_SUBMENU);
                    menuEntry.setDeprioritized(false);

                    parentMenu = menuEntry;
                    menuEntry = copy;
                }

                menuEntry.setParent(parentMenu);
            }

            prev = player;
        }
    }

    private Decorations getDecorations(Player player) {
        int image = -1;
        Color color = null;

        if (Arrays.stream(client.playerlist()).anyMatch(s -> player.getName().equalsIgnoreCase(s)) && config.highlightFriends()) {
            color = config.getFriendColor();
        } else if (!player.isFriendsChatMember() && !player.isClanMember() && config.highlightOthers()) {
            color = config.getOthersColor();
        }

        if (image == -1 && color == null) {
            return null;
        }

        return new Decorations(image, color);
    }

    private String decorateTarget(String oldTarget, Decorations decorations) {
        String newTarget = oldTarget;

        if (decorations.getColor() != null) {
            // strip out existing <col...
            int idx = oldTarget.indexOf('>');
            if (idx != -1) {
                newTarget = oldTarget.substring(idx + 1);
            }

            newTarget = ColorUtil.prependColorTag(newTarget, decorations.getColor());
        }

        if (decorations.getImage() != -1) {
            newTarget = "<img=" + decorations.getImage() + ">" + newTarget;
        }

        return newTarget;
    }

    @Value
    private static class Decorations {
        private final int image;
        private final Color color;
    }
}
