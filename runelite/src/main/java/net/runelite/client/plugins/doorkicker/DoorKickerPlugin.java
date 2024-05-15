package net.runelite.client.plugins.doorkicker;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import javax.inject.Inject;

@PluginDescriptor(
        name = "Door Kicker",
        description = "Assert your dominance against the doors"
)
@Slf4j
public class DoorKickerPlugin extends Plugin
{
    @Inject
    private Client client;

    private LocalPoint doorLocation;

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked e)
    {
        if (e.getMenuOption().equals("Open") && e.getMenuTarget().endsWith("Door"))
        {
            doorLocation = LocalPoint.fromScene(e.getParam0(), e.getParam1());
        }
        else
        {
            doorLocation = null;
        }
    }

    @Subscribe
    public void onClientTick(ClientTick e)
    {
        if (doorLocation != null)
        {
            Player local = client.getLocalPlayer();
            if (doorLocation.distanceTo(local.getLocalLocation()) <= Perspective.LOCAL_TILE_SIZE)
            {
                local.setAnimation(423);
                local.setActionFrame(0);
                doorLocation = null;
            }
        }
    }
}
