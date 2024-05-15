package net.runelite.client.plugins.feed;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("feed")
public interface FeedConfig extends Config
{
	@ConfigItem(
		keyName = "includeGroot",
		name = "Include Groot Spawns",
		description = "Configures whether Groot Spawns are displayed",
		position = 0
	)
	default boolean includeGrootSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeDonor",
			name = "Include Donor Boss Spawns",
			description = "Configures whether Donor Boss Spawns are displayed",
			position = 1
	)
	default boolean includeDonorSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeVote",
			name = "Include Vote Boss Spawns",
			description = "Configures whether Vote Boss Spawns are displayed",
			position = 2
	)
	default boolean includeVoteSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeHespori",
			name = "Include Hespori Boss Spawns",
			description = "Configures whether Hespori Boss Spawns are displayed",
			position = 3
	)
	default boolean includeHesporiSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeWildy",
			name = "Include Wildy Boss Spawns",
			description = "Configures whether Wildy Boss Spawns are displayed",
			position = 4
	)
	default boolean includeWildySpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeStar",
			name = "Include Shooting Star Spawns",
			description = "Configures whether Shooting Star Spawns are displayed",
			position = 5
	)
	default boolean includeStarSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "includeSeasonal",
			name = "Include Seasonal notifications",
			description = "Configures whether Seasonal notifications are displayed",
			position = 6
	)
	default boolean includeSeasonalSpawns()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Notification Sound",
			name = "Notify on event spawn",
			description = "Configures whether Sound notification is enabled.",
			position = 7
	)
	default boolean notificationSounds()
	{
		return true;
	}

	@ConfigItem(
			keyName = "Tray Notification",
			name = "Tray Notification event spawn",
			description = "Configures whether Tray Notification is enabled.",
			position = 8
	)
	default boolean trayNotification()
	{
		return true;
	}
}
