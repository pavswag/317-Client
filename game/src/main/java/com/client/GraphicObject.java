package com.client;

import com.client.definitions.GraphicsDefinition;
import com.client.definitions.SeqDefinition;
import net.runelite.api.Animation;
import net.runelite.api.coords.LocalPoint;
import net.runelite.rs.api.RSGraphicsObject;
import net.runelite.rs.api.RSSequenceDefinition;
import org.jetbrains.annotations.Nullable;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

class GraphicObject extends Renderable implements RSGraphicsObject {
	private int gfxId;

	protected SeqDefinition sequenceDefinition;
	public GraphicObject(int plane, int j, int l, int definitionId, int height, int k1, int xPos) {
		gfxId = definitionId;
		gfxToDisplay = GraphicsDefinition.cache[definitionId];
		sequenceDefinition = getDisplayedAnimation();
		this.gfxHeightLevel = plane;
		x = xPos;
		y = k1;
		this.z = height;
		gfxDisplayCycle = j + l;
		isFinished = false;
	}

	protected Model getDisplayedModel() {
		return gfxToDisplay.getModel();
	}
	
	protected SeqDefinition getDisplayedAnimation() {
		return gfxToDisplay.animationSequence;
	}

	@Override
	public Model getRotatedModel() {
		Model model = getDisplayedModel();
		if (model == null) {
			return null;
		}
		SeqDefinition animationSequence = getDisplayedAnimation();
		if(animationSequence == null || animationSequence.isEmpty())
			return null;
		int frame = (this.frame < animationSequence.frameIDs.length) ? animationSequence.frameIDs[this.frame] : -1;
		Model model_1 = new Model(true, Frame.noAnimationInProgress(frame), false, model);
		if (!isFinished) {
			model_1.generateBones();
			model_1.interpolate(frame);
			model_1.groupedTriangleLabels = null;
			model_1.groupedVertexLabels = null;
		}
			if (gfxToDisplay.resizeXY != 128 || gfxToDisplay.resizeZ != 128)
				model_1.scale(gfxToDisplay.resizeXY, gfxToDisplay.resizeXY,
						gfxToDisplay.resizeZ);
			if (gfxToDisplay.rotation != 0) {
				if (gfxToDisplay.rotation == 90)
					model_1.rotate90Degrees();
				if (gfxToDisplay.rotation == 180) {
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				}
				if (gfxToDisplay.rotation == 270) {
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				}
			}
		model_1.light(64 + (gfxToDisplay.modelBrightness),
				850 + (gfxToDisplay.modelShadow), -30, -50, -30, true);
		return model_1;
	}

	public void update(int animationCycle) {
		SeqDefinition seqDefinition = getDisplayedAnimation();
		if (this.gfxToDisplay == null || seqDefinition == null || seqDefinition.isEmpty()) {
			if(!(this instanceof RuneLiteObjectImpl))
				this.isFinished = true;
			return;
		}
		for (this.cycle += animationCycle; this.cycle > seqDefinition.getDuration(this.frame); ) {
			this.cycle -= seqDefinition.getDuration(this.frame) + 1;
			this.frame++;
			if (this.frame >= seqDefinition.frameCount) {
				this.frame = 0;
				if (isLooping()) {
					setFrame(0);
					setFrameCycle(0);
				} else {
					this.isFinished = true;
				}
			}
		}
	}

	protected boolean isLooping() {
		return false;
	}

	public int gfxHeightLevel;
	public int x;
	public int y;
	public int z;
	public final int gfxDisplayCycle;
	public boolean isFinished;
	private GraphicsDefinition gfxToDisplay;
	private int frame;
	private int cycle;

	@Override
	public LocalPoint getLocation() {
		return new LocalPoint(x, y);
	}

	@Nullable
	@Override
	public Animation getAnimation() {
		return getDisplayedAnimation();
	}

	@Override
	public int getAnimationFrame() {
		return frame;
	}

	@Override
	public int getId() {
		return gfxToDisplay.id;
	}

	@Override
	public void setId(int id) {
		this.gfxId = id;
		this.gfxToDisplay = GraphicsDefinition.fetch(id);
		this.sequenceDefinition = this.gfxToDisplay.animationSequence;
	}
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getStartCycle() {
		return this.gfxDisplayCycle;
	}

	@Override
	public int getLevel() {
		return gfxHeightLevel;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public boolean finished() {
		return this.isFinished;
	}

	@Override
	public int getFrame() {
		return this.frame;
	}

	@Override
	public void setFrame(int frame) {
		this.frame = frame;
	}

	@Override
	public int getFrameCycle() {
		return this.cycle;
	}

	@Override
	public void setFrameCycle(int frameCycle) {
		this.cycle = frameCycle;
	}

	@Override
	public void setFinished(boolean finished) {
		this.isFinished = finished;
	}

	@Override
	public void setLevel(int level) {
		this.gfxHeightLevel = level;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setHeight(int height) {
		this.z = height;
	}

	@Override
	public RSSequenceDefinition getSequenceDefinition() {
		return null;
	}

	@Override
	public void setSequenceDefinition(RSSequenceDefinition sequenceDefinition) {
		this.sequenceDefinition = (SeqDefinition) sequenceDefinition;
	}
}