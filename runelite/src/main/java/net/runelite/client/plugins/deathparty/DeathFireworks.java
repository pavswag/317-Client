package net.runelite.client.plugins.deathparty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeathFireworks
{
    NONE("None", -1),
    NORMAL("Normal", 199),
    NINETY_NINE("99", 1388),
    MAX_TOTAL("Max total", 1389),
    ;

    private final String name;
    private final int graphicId;

    @Override
    public String toString()
    {
        return getName();
    }
}
