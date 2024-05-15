package net.runelite.client.plugins.playeroutline;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Player Outline",
        description = "A simple plugin that outlines the player allowing you to see the player behind objects.",
        tags = "highlight, player, outline, color"
)
public class PlayerOutlinePlugin extends Plugin
{
    @Inject
    PlayerOutlineOverlay playerOutlineOverlay;
    @Inject
    private OverlayManager overlayManager;

    @Override
    protected void startUp()
    {
        overlayManager.add(playerOutlineOverlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(playerOutlineOverlay);
    }

    @Provides
    PlayerOutlineConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PlayerOutlineConfig.class);
    }
}