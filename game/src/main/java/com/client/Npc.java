package com.client;

import com.client.definitions.NpcDefinition;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
import com.client.definitions.GraphicsDefinition;
import com.client.definitions.SeqDefinition;
import com.client.definitions.SeqFrame;
import com.client.features.settings.Preferences;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.rs.api.*;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public final class Npc extends Entity implements RSNPC {

	private Model getAnimatedModel() {
		SeqDefinition primarySeq = null;
		SeqDefinition secondarySeq = null;
		if ((super.primarySeqID >= 0) && (super.primarySeqDelay == 0)) {
			primarySeq = SeqDefinition.get(super.primarySeqID);
			boolean primarySeqIsSkeletal = primarySeq.isSkeletalAnimation();
			int primaryTransformID = primarySeqIsSkeletal ? -1 : primarySeq.getFrameIDs()[super.primarySeqFrame];
			int secondaryTransformID = -1;
			if ((super.secondarySeqID >= 0) && (super.secondarySeqID != super.seqStandID)) {
				secondarySeq = SeqDefinition.get(super.secondarySeqID);
				boolean secondarySeqIsSkeletal = primarySeq.isSkeletalAnimation();
				secondaryTransformID = secondarySeqIsSkeletal ? -1 : secondarySeq.getFrameIDs()[super.secondarySeqFrame];
			}
			// double anim
			if (primarySeq.isSkeletalAnimation() || (secondarySeq != null && secondarySeq.isSkeletalAnimation())) {
				return desc.getAnimatedModelSkeletal(primarySeq, secondarySeq, primarySeqFrame, secondarySeqFrame);
			}
			return desc.getAnimatedModel(secondaryTransformID, primaryTransformID, SeqDefinition.get(super.primarySeqID)
					.getMasks());
		}

		int transformID = -1;

		if (super.secondarySeqID >= 0) {
			secondarySeq = SeqDefinition.get(super.secondarySeqID);
			transformID = secondarySeq.isSkeletalAnimation() ? -1 : secondarySeq.getFrameIDs()[super.secondarySeqFrame];
		}

		if (secondarySeq != null && secondarySeq.isSkeletalAnimation()) {
			return desc.getAnimatedModelSkeletal(primarySeq, secondarySeq, primarySeqFrame, secondarySeqFrame);
		}

		return desc.getAnimatedModel(-1, transformID, null);
	}

	@Override
	public Model getRotatedModel() {
		if (desc == null)
			return null;
		Model model = getAnimatedModel();
		if (model == null)
			return null;
		super.height = model.modelBaseY;
		Iterator spotAnimIterator = spotAnims.iterator();
		while(spotAnimIterator.hasNext()) {
			EntitySpotAnim graphicObject = (EntitySpotAnim) spotAnimIterator.next();

			if(graphicObject.getId() == -1) {
				graphicObject.remove();
				continue;
			}
			GraphicsDefinition spotAnim = GraphicsDefinition.cache[graphicObject.getId()];
			Model model_2 = spotAnim.getModel();
			if (model_2 != null) {
				SeqDefinition seq = spotAnim.animationSequence;
				Model model_3 = new Model(true, SeqFrame.noAnimationInProgress(graphicObject.getFrame()), false, model_2);
				model_3.offsetBy(0, -graphicObject.getHeight(), 0);
				if(graphicObject.getFrame() != -1) {
					if (seq != null && seq.isSkeletalAnimation()) {
						model_3.playSkeletal(seq, graphicObject.getFrame());
					} else {
						model_3.generateBones();
						model_3.interpolate(spotAnim.animationSequence.frameIDs[graphicObject.getFrame()]);
					}
				}

				model_3.groupedTriangleLabels = null;
				model_3.groupedVertexLabels = null;
				if (spotAnim.resizeXY != 128 || spotAnim.resizeZ != 128)
					model_3.scale(spotAnim.resizeXY, spotAnim.resizeXY,
							spotAnim.resizeZ);
				// model_3.method479(64 + spotAnim.anInt413, 850 +
				// spotAnim.anInt414, -30, -50, -30, true);
				model_3.light(64 + spotAnim.modelBrightness,
						850 + spotAnim.modelShadow, -30, -50, -30, true);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_3 };
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}
		if (desc.size == 1)
			model.singleTile = true;
		return model;
	}

	@Override
	public boolean isVisible() {
		if (Client.hidepets) {
			if (desc != null) {
				if (npcPetType == 2 || npcPetType == 1) {
					return false;
				}
			}
		}
		return desc != null;
	}

	Npc() {

	}

	public boolean isShowMenuOnHover() {
		return npcPetType == 0 || npcPetType == 2 && !Preferences.getPreferences().hidePetOptions;
	}

	public int npcPetType;
	public NpcDefinition desc;
	public boolean deadnpc;

	@Override
	public int getCombatLevel() {
		return desc.getCombatLevel();
	}

	@Nullable
	@Override
	public NPCComposition getTransformedComposition() {
		return getComposition();
	}

	@Override
	public void onDefinitionChanged(NPCComposition composition) {

	}

	@Override
	public int getId() {
		return desc.getId();
	}

	@Nullable
	@Override
	public String getName() {
		return desc.getName();
	}

	@Override
	public Actor getInteracting() {
		int index = interactingEntity;
		if (index == -1 || index == 65535)
		{
			return null;
		}
		Client client = Client.instance;

		if (index < 32768)
		{
			NPC[] npcs = client.getCachedNPCs();
			return npcs[index];
		}

		index -= 32768;
		Player[] players = client.players;
		return players[index];
	}

	@Override
	public int getHealthRatio() {
		return Math.round(this.currentHealth / this.maxHealth);
	}

	@Override
	public int getHealthScale() {
		return 0;
	}

	@Override
	public WorldPoint getWorldLocation() {
		return WorldPoint.fromLocal(Client.instance, getLocalLocation());
	}

	@Override
	public LocalPoint getLocalLocation() {
		return new LocalPoint(getX(), getY());
	}

	@Override
	public void setIdleRotateLeft(int animationID) {

	}

	@Override
	public void setIdleRotateRight(int animationID) {

	}

	@Override
	public void setWalkAnimation(int animationID) {

	}

	@Override
	public void setWalkRotateLeft(int animationID) {

	}

	@Override
	public void setWalkRotateRight(int animationID) {

	}

	@Override
	public void setWalkRotate180(int animationID) {

	}

	@Override
	public void setRunAnimation(int animationID) {

	}

	@Override
	public Polygon getCanvasTilePoly() {

		int size = desc.size;

		return Perspective.getCanvasTileAreaPoly(Client.instance, this.getLocalLocation(), size);
	}

	@Nullable
	@Override
	public Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
		return Perspective.getCanvasTextLocation(Client.instance, graphics, getLocalLocation(), text, zOffset);
	}

	@Override
	public Point getCanvasImageLocation(BufferedImage image, int zOffset) {
		return Perspective.getCanvasImageLocation(Client.instance, getLocalLocation(), image, zOffset);
	}

	@Override
	public Point getCanvasSpriteLocation(SpritePixels sprite, int zOffset) {
		return null;
	}

	@Override
	public Point getMinimapLocation() {
		return Perspective.localToMinimap(Client.instance, getLocalLocation());
	}

	@Override
	public Shape getConvexHull() {
		RSModel model = getModel();
		if (model == null)
			return null;
		int size = 1;
		LocalPoint localPoint = new LocalPoint(size * 64 - 64 + getX(), size * 64 - 64 + getY());
		int var4 = Perspective.getTileHeight(Client.instance, localPoint, Client.instance.getPlane());
		return model.getConvexHull(getX(), getY(), getOrientation(), var4);
	}

	@Override
	public WorldArea getWorldArea() {
		return new WorldArea(x,y, desc.size, this.height, Client.instance.getPlane());
	}

	@Override
	public boolean isDead() {
		return deadnpc;
	}

	@Override
	public boolean isMoving() {
		return false;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public int getRSInteracting() {
		return 0;
	}

	@Override
	public String getOverheadText() {
		return null;
	}

	@Override
	public void setOverheadText(String overheadText) {

	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int[] getPathX() {
		return this.pathX;
	}

	@Override
	public int[] getPathY() {
		return this.pathY;
	}

	@Override
	public int getAnimation() {
		return primarySeqID;
	}

	@Override
	public void setAnimation(int animation) {
		this.primarySeqID = animation;
	}

	@Override
	public int getAnimationFrame() {
		return primarySeqFrame;
	}

	@Override
	public int getActionFrame() {
		return 0;
	}

	@Override
	public void setAnimationFrame(int frame) {

	}

	@Override
	public void setActionFrame(int frame) {

	}

	@Override
	public int getActionFrameCycle() {
		return 0;
	}

	@Override
	public int getGraphic() {
		return 0;
	}

	@Override
	public void setGraphic(int id) {

	}

	@Override
	public int getSpotAnimFrame() {
		return 0;
	}

	@Override
	public void setSpotAnimFrame(int id) {

	}

	@Override
	public int getSpotAnimationFrameCycle() {
		return 0;
	}

	@Override
	public int getIdlePoseAnimation() {
		return 0;
	}

	@Override
	public void setIdlePoseAnimation(int animation) {

	}

	@Override
	public int getPoseAnimation() {
		return 0;
	}

	@Override
	public void setPoseAnimation(int animation) {

	}

	@Override
	public int getPoseFrame() {
		return 0;
	}

	@Override
	public void setPoseFrame(int frame) {

	}

	@Override
	public int getPoseFrameCycle() {
		return 0;
	}

	@Override
	public int getLogicalHeight() {
		return Client.instance.getPlane();
	}

	@Override
	public int getOrientation() {
		return orientation;
	}

	@Override
	public int getCurrentOrientation() {
		return orientation;
	}

	@Override
	public RSIterableNodeDeque getHealthBars() {
		return null;
	}

	@Override
	public int[] getHitsplatValues() {
		return new int[0];
	}

	@Override
	public int[] getHitsplatTypes() {
		return new int[0];
	}

	@Override
	public int[] getHitsplatCycles() {
		return new int[0];
	}

	@Override
	public int getIdleRotateLeft() {
		return 0;
	}

	@Override
	public int getIdleRotateRight() {
		return 0;
	}

	@Override
	public int getWalkAnimation() {
		return 0;
	}

	@Override
	public int getWalkRotate180() {
		return 0;
	}

	@Override
	public int getWalkRotateLeft() {
		return 0;
	}

	@Override
	public int getWalkRotateRight() {
		return 0;
	}

	@Override
	public int getRunAnimation() {
		return 0;
	}

	@Override
	public void setDead(boolean dead) {
		deadnpc = dead;
	}

	@Override
	public IterableHashTable<ActorSpotAnim> getSpotAnims() {
		return this.spotAnims;
	}


	@Override
	public boolean hasSpotAnim(int customGfxID) {
		Iterator ir = spotAnims.iterator();
		while(ir.hasNext()) {
			EntitySpotAnim graphicObject = (EntitySpotAnim) ir.next();
			if(graphicObject.getId() == customGfxID)
				return true;
		}
		return false;
	}

	@Override
	public void removeSpotAnim(int hash) {
		Iterator ir = spotAnims.iterator();
		while(ir.hasNext()) {
			EntitySpotAnim graphicObject = (EntitySpotAnim) ir.next();
			if(graphicObject.getHash() == hash) {
				graphicObject.remove();
				ir.remove();
			}
		}
	}

	@Override
	public void createSpotAnim(int customGfxID, int customGfxID1, int i, int i1) {
		EntitySpotAnim entitySpotAnim = new EntitySpotAnim();
		entitySpotAnim.setId(customGfxID);
		entitySpotAnim.setHeight(i);
		entitySpotAnim.setCycle(Client.instance.getGameCycle() + i1);
		spotAnims.insert(entitySpotAnim, entitySpotAnim.getHash());
	}

	@Override
	public int getPathLength() {
		return 0;
	}

	@Override
	public int getOverheadCycle() {
		return 0;
	}

	@Override
	public void setOverheadCycle(int cycle) {

	}

	@Override
	public int getPoseAnimationFrame() {
		return 0;
	}

	@Override
	public void setPoseAnimationFrame(int frame) {

	}

	@Override
	public RSNPCComposition getComposition() {
		return desc;
	}

	private int index;
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int id) {
		this.index = id;
	}

	@Override
	public RSNode getNext() {
		return null;
	}

	@Override
	public long getHash() {
		return 0;
	}

	@Override
	public RSNode getPrevious() {
		return null;
	}

	@Override
	public void onUnlink() {

	}

	@Override
	public int getModelHeight() {
		return 0;
	}

	@Override
	public void setModelHeight(int modelHeight) {

	}

	@Override
	public RSModel getModel() {
		return getRotatedModel();
	}

	@Override
	public void draw(int orientation, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y, int z, long hash) {

	}
}
