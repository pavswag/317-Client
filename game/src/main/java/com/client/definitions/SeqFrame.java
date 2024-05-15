package com.client.definitions;

import com.client.osrs.OSRSCacheLoader;
import lombok.Data;

@Data
public class SeqFrame {
    private int frameFileId;
    private int[] xModifier;
    private int[] yModifier;
    private int[] zModifier;
    private int[] bases;
    private int baseCount;
    private SeqBase base;
    private int frameCount;

    public static SeqFrame get(int id) {
        return OSRSCacheLoader.getSeqFrame(id);
    }

    public static boolean isLoaded(int id) {
        return OSRSCacheLoader.getSeqFrameDefs().getDefinitions().containsKey(id >> 16);
    }

    public static SeqFrame forID(int id) {
        return OSRSCacheLoader.getSeqFrame(id);
    }

    public static boolean noAnimationInProgress(int frame) {
        return frame == -1;
    }
}