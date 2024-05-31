package com.client.discord;


import com.client.Client;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class RPC {
    public static String AppId = "1003641398689533982";

    public static String line1 = "Grimoire";

    public static String line2 = "";

    public static String image = "ex_logo";

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static int currentRegion = 0;
    public static long SkillTimeout = 0;

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static void init() {
        if (!isWindows())
            return;
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
                System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
            }).build();
        DiscordRPC.discordInitialize(AppId, handlers, true);
        createNewPresence();
    }

    public static void createNewPresence() {
        DiscordRichPresence rich = new DiscordRichPresence
                .Builder("Playing Grimoire")
                .setDetails("In Menu")
                .setBigImage("ex_logo", "")
                .setStartTimestamps(System.currentTimeMillis())
                .build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public static void updatePresence(String details) {
        DiscordRichPresence rich = new DiscordRichPresence
                .Builder("Playing Grimoire")
                .setDetails(details)
                .setBigImage("ex_logo", "")
                .setStartTimestamps(System.currentTimeMillis())
                .build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public static void tickRegion() {
        final int regionX = Client.localPlayer.getAbsoluteX() >> 3;
        final int regionY = Client.localPlayer.getAbsoluteY() >> 3;
        int regionID = ((regionX / 8) << 8) + (regionY / 8);

        if (currentRegion == regionID) {
            return;
        }

        if (regionID > 0) {
            DiscordGameEventType discordGameEventType = DiscordGameEventType.fromRegion(regionID);
            if (discordGameEventType == null) {
                updatePresence(DiscordGameEventType.IN_GAME.state);
            } else {
                updatePresence(discordGameEventType.state);
                currentRegion = regionID;
            }
        }
    }
}
