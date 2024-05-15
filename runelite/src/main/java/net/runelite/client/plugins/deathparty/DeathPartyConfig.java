package net.runelite.client.plugins.deathparty;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(DeathPartyConfig.GROUP)
public interface DeathPartyConfig extends Config
{
    String GROUP = "deathparty";

    @ConfigItem(
            keyName = "showFireworks",
            name = "Level-up fireworks style",
            description = "Type of fireworks which will be triggered a nearby player dies"
    )
    default DeathFireworks showFireworks()
    {
        return DeathFireworks.NINETY_NINE;
    }
}
