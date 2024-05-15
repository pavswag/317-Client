package com.client.definitions;

import com.client.osrs.OSRSCacheLoader;
import lombok.Data;
import net.runelite.api.Animation;
import net.runelite.api.Skeleton;

import java.util.Map;

import static com.client.definitions.SeqDefinition.Defaults.*;

@Data
public class SeqDefinition implements Animation {
    public int id;
    public int frameCount = DEFAULT_FRAME_COUNT;
    public int[] frameIDs;

    public int[] secondaryFrameIds;
    public int[] delays;
    public int frameStep = DEFAULT_LOOP_DELAY;
    public int[] masks;
    public boolean[] booleanMasks;
    public boolean stretches = DEFAULT_ONE_SQUARE_ANIMATION;
    public boolean isEmpty = false;
    public int forcedPriority = DEFAULT_FORCED_PRIORITY;
    public int leftHandItem = DEFAULT_LEFT_HAND_ITEM;
    public int rightHandItem = DEFAULT_RIGHT_HAND_ITEM;
    public int loopCount = DEFAULT_FRAME_STEP;
    public int moveStyle = DEFAULT_RESET_WHEN_WALK;
    public int idleStyle = DEFAULT_PRIORITY;
    public int replyMode = DEFAULT_DELAY_TYPE;
    public int[] chatFrameIds;
    public int[] soundEffects;
    public int skeletalId = DEFAULT_SKELETAL_ID;
    public int rangeBegin = DEFAULT_RANGE_BEGIN;
    public int rangeEnd = DEFAULT_RANGE_END;

    public int maxLoops = 99;

    private Map<Integer, Integer> soundRelated;

    public int getSkeletalLength() {
        return this.rangeEnd - this.rangeBegin;
    }

    public boolean isSkeletalAnimation() {
        return skeletalId >= 0;
    }

    public static SeqDefinition get(int id) {
        return OSRSCacheLoader.getSeqDef(id);
    }

    public static int length() {
        return OSRSCacheLoader.getSeqDefs().getDefinitions().length;
    }

    public int getDuration(int i) {
        int j = isSkeletalAnimation() ? 1 : delays[i];
        if (j == 0)
            j = 1;
        return j;
    }

    public void postDecode() {
        if (frameCount == 0) {
            frameCount = 1;
            frameIDs = new int[1];
            frameIDs[0] = -1;
            secondaryFrameIds = new int[1];
            secondaryFrameIds[0] = -1;
            delays = new int[1];
            delays[0] = -1;
            isEmpty = true;
        }

        if (moveStyle == -1) {
            moveStyle = (masks == null) ? 0 : 2;
        }
        if (idleStyle == -1) {
            idleStyle = (masks == null) ? 0 : 2;
            return;
        }
        idleStyle = 0;
    }

    @Override
    public Skeleton getSkeleton() {
        return null;
    }

    @Override
    public int getTransformCount() {
        return 0;
    }

    @Override
    public int[] getTransformTypes() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorX() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorY() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorZ() {
        return new int[0];
    }

    @Override
    public boolean isShowing() {
        return false;
    }

    public static class Defaults {
        public static final int DEFAULT_FRAME_COUNT = 0;
        public static int DEFAULT_LOOP_DELAY = -1;
        public static boolean DEFAULT_ONE_SQUARE_ANIMATION;
        public static int DEFAULT_FORCED_PRIORITY = 5;
        public static int DEFAULT_LEFT_HAND_ITEM = -1;
        public static int DEFAULT_RIGHT_HAND_ITEM = -1;
        public static int DEFAULT_FRAME_STEP = 99;
        public static int DEFAULT_RESET_WHEN_WALK = -1;
        public static int DEFAULT_PRIORITY = -1;
        public static int DEFAULT_DELAY_TYPE = 2;
        public static int DEFAULT_SKELETAL_ID = -1;
        public static int DEFAULT_RANGE_BEGIN = 0;
        public static int DEFAULT_RANGE_END = 0;
    }
}

