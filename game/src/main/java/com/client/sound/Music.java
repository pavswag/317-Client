package com.client.sound;

import lombok.Getter;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 15/03/2024
 */
@Getter
public class Music {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int musicId;
    private int mapRegion;
    private int order;
    private boolean members;

    public Music(int x1, int y1, int x2, int y2, int musicId, int mapRegion, int order, boolean members) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.musicId = musicId;
        this.mapRegion = mapRegion;
        this.order = order;
        this.members = members;
    }

    public static int getMusicIdFromBoundary(int x, int y) {

        for (Music song : AreaSounds.songs) {
            if (x >= song.getX1() && x <= song.getX2() && y >= song.getY1() && y <= song.getY2()) {
                return song.getMusicId();
            }
        }

        return 0;
    }
}
