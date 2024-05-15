package com.client;

import com.client.definitions.SeqDefinition;
import net.runelite.api.Animation;
import net.runelite.api.Client;
import net.runelite.api.GraphicsObject;
import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.RuneLiteObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GraphicsObjectCreated;

public class RuneLiteObjectImpl extends GraphicObject implements RuneLiteObject {
    private Model model;

    private SeqDefinition animation;

    private boolean shouldLoop;

    private int orientation;

    public RuneLiteObjectImpl(int heightLevel, int currentLoopCycle, int gfxDisplayDelay, int gfxId, int z, int y, int x) {
        super(heightLevel, currentLoopCycle, gfxDisplayDelay, gfxId, z, y, x);
    }

    public void setModel(Model model) {
        this.model = (Model)model;
    }

    public void setAnimation(Animation animation) {
        setFrame(0);
        setFrameCycle(0);
        this.animation = (SeqDefinition) animation;
    }

    protected com.client.Model getDisplayedModel() {
        return (com.client.Model) this.model;
    }

    protected SeqDefinition getDisplayedAnimation() {
        return this.animation;
    }

    public void setShouldLoop(boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
    }

    protected boolean isLooping() {
        return this.shouldLoop;
    }

    public void setLocation(LocalPoint point, int plane) {
        setX(point.getX());
        setY(point.getY());
        setLevel(plane);
        setHeight(Perspective.getTileHeight(com.client.Client.instance, point, plane));
    }

    public void setActive(boolean active) {
        if (this.isFinished == active) {
            this.isFinished = !active;
            if (active) {
                setFrame(0);
                setFrameCycle(0);
                com.client.Client client = com.client.Client.instance;
                client.getCallbacks().post(new GraphicsObjectCreated(this));
                client.incompleteAnimables.insertHead(this);
            } else {
                unlinkSub();
            }
        }
    }

    public boolean isActive() {
        return !this.isFinished;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public int getRadius() {
        return 0;
    }

    @Override
    public void setRadius(int radius) {

    }

    @Override
    public boolean drawFrontTilesFirst() {
        return false;
    }

    @Override
    public void setDrawFrontTilesFirst(boolean drawFrontTilesFirst) {

    }
}
