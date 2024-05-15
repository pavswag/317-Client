package net.runelite.api.events;

import lombok.Value;
import net.runelite.api.Skill;

@Value
public class StatChanged
{
	private final Skill skill;
	private final int xp;
	private final int level;
	private final int boostedLevel;
}

