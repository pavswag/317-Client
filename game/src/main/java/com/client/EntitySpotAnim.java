package com.client;

import net.runelite.api.ActorSpotAnim;

import java.util.Random;

public class EntitySpotAnim extends Linkable implements ActorSpotAnim {
    private int id;
    private int height;
    private int frame = -1;
    private int cycle;
    private long hash;
    public int currentAnimationTimeRemaining;
    public int nextGraphicFrame;
    private static final Random hashGenerator = new Random();
    public EntitySpotAnim() {
        this.hash = (long) (hashGenerator.nextInt(Integer.MAX_VALUE) * 2.5);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getFrame() {
        return frame;
    }

    @Override
    public void setFrame(int frame) {
        this.frame = frame;
    }

    @Override
    public int getCycle() {
        return cycle;
    }

    @Override
    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    @Override
    public long getHash() {
        return hash;
    }
}
