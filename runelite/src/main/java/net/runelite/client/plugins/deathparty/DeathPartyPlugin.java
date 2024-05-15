package net.runelite.client.plugins.deathparty;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.ActorDeath;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.List;
import java.util.Timer;

@PluginDescriptor(
        name = "Death Party",
        description = "We're having fun!",
        tags = {"death", "party", "fireworks"}
)
@Slf4j
public class DeathPartyPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private ConfigManager configManager;

    @Inject
    private DeathPartyConfig config;

    @Inject
    private OverlayManager overlayManager;

    private boolean editorMode;
    private Timer refreshTimer;

    @Provides
    DeathPartyConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(DeathPartyConfig.class);
    }

    @Subscribe
    public void onActorDeath(ActorDeath actorDeath)
    {
        Actor actor = actorDeath.getActor();
        if (actor instanceof Player)
        {
            List<Player> players = client.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                if (player != null) {
                    final int fireworksGraphic = config.showFireworks().getGraphicId();

                    if (fireworksGraphic == -1)
                    {
                        return;
                    }

                    player.setGraphic(fireworksGraphic);
                    player.setSpotAnimFrame(0);
                }
            }
        }
    }
}
