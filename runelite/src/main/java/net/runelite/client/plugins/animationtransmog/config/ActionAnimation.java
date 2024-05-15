package net.runelite.client.plugins.animationtransmog.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionAnimation
{
    DEFAULT("Default"),
    ARCANECHOP("Arcane Chop"),
    ARCANEMINE("Arcane Mine"),
    BLASTMINE("Blast Mine"),
    DIG("Dig"),
    HEADBANG("Headbang"),
    SMOOTHSCATTER("Smooth Scatter"),
    BRUTAL("Brutal");


    private final String option;

    @Override
    public String toString()
    {
        return option;
    }
}