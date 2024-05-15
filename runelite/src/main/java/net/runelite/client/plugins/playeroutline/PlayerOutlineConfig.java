package net.runelite.client.plugins.playeroutline;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("playeroutline")
public interface PlayerOutlineConfig extends Config
{
    @Alpha
    @ConfigItem(
            keyName = "playerOutlineColor",
            name = "Outline Color",
            description = "The color for the players outline",
            position = 0
    )
    default Color playerOutlineColor()
    {
        return new Color(0x3D000000, true);
    }

    @ConfigItem(
            keyName = "borderWidth",
            name = "Border Width",
            description = "Width of the player outline border",
            position = 1
    )
    default int borderWidth()
    {
        return 4;
    }

    @ConfigItem(
            keyName = "outlineFeather",
            name = "Outline Feather",
            description = "Specify between 0-4 how much the player outline should be faded",
            position = 2
    )
    @Range(
            max = 4
    )
    default int outlineFeather()
    {
        return 4;
    }
}