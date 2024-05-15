package com.client;

import com.client.cache.IterableNodeHashTable;
import com.client.definitions.SeqDefinition;

public class Entity extends Renderable {

	public boolean isLocalPlayer() {
		return this == Client.localPlayer;
	}

	public int getAbsoluteX() {
		int x = Client.baseX + (this.x - 6 >> 7);
		if (this instanceof Npc) {
			return x - ((Npc) this).desc.size / 2;
		}
		return x;
	}

	public int getAbsoluteY() {
		int y = Client.baseY + (this.y - 6 >> 7);
		if (this instanceof Npc) {
			return y - ((Npc) this).desc.size / 2;
		}
		return y;
	}

	public int getDistanceFrom(Entity entity) {
		return getDistanceFrom(entity.getAbsoluteX(), entity.getAbsoluteY());
	}

	public int getDistanceFrom(int x2, int y2) {
		int x = (int) Math.pow(getAbsoluteX() - x2, 2.0D);
		int y = (int) Math.pow(getAbsoluteY() - y2, 2.0D);
		return (int) Math.floor(Math.sqrt(x + y));
	}

	public final void setPos(int i, int j, boolean flag) {
		if (primarySeqID != -1 && SeqDefinition.get(primarySeqID).idleStyle == 1)
			primarySeqID = -1;
		if (!flag) {
			int k = i - pathX[0];
			int l = j - pathY[0];
			if (k >= -8 && k <= 8 && l >= -8 && l <= 8) {
				if (smallXYIndex < 9)
					smallXYIndex++;
				for (int i1 = smallXYIndex; i1 > 0; i1--) {
					pathX[i1] = pathX[i1 - 1];
					pathY[i1] = pathY[i1 - 1];
					aBooleanArray1553[i1] = aBooleanArray1553[i1 - 1];
				}

				pathX[0] = i;
				pathY[0] = j;
				aBooleanArray1553[0] = false;
				return;
			}
		}
		smallXYIndex = 0;
		remaining_steps = 0;
		anInt1503 = 0;
		pathX[0] = i;
		pathY[0] = j;
		x = pathX[0] * 128 + anInt1540 * 64;
		y = pathY[0] * 128 + anInt1540 * 64;
	}

	public final void method446() {
		smallXYIndex = 0;
		remaining_steps = 0;
	}

	public final void updateHitData(int j, int k, int l, int source) {
		for (int i1 = 0; i1 < 4; i1++)
			if (hitsLoopCycle[i1] <= l) {
				hitArray[i1] = k;
				hitMarkTypes[i1] = j;
				hitsLoopCycle[i1] = l + 70;
				hitSource[i1] = source - 32768;
				return;
			}
	}

	public final void moveInDir(boolean flag, int i) {
		int j = pathX[0];
		int k = pathY[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1)
			k++;
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3)
			j--;
		if (i == 4)
			j++;
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6)
			k--;
		if (i == 7) {
			j++;
			k--;
		}
		if (primarySeqID != -1 && SeqDefinition.get(primarySeqID).idleStyle == 1)
			primarySeqID = -1;
		if (smallXYIndex < 9)
			smallXYIndex++;
		for (int l = smallXYIndex; l > 0; l--) {
			pathX[l] = pathX[l - 1];
			pathY[l] = pathY[l - 1];
			aBooleanArray1553[l] = aBooleanArray1553[l - 1];
		}
		pathX[0] = j;
		pathY[0] = k;
		aBooleanArray1553[0] = flag;
	}

	public int entScreenX;
	public int entScreenY;

	public boolean isVisible() {
		return false;
	}

	Entity() {
		pathX = new int[10];
		pathY = new int[10];
		interactingEntity = -1;
		anInt1504 = 32;
		anInt1505 = -1;
		height = 200;
		seqStandID = -1;
		standTurnAnimIndex = -1;
		hitArray = new int[4];
		hitMarkTypes = new int[4];
		hitsLoopCycle = new int[4];
		hitSource = new int[4];
		secondarySeqID = -1;
		graphicId = -1;
		primarySeqID = -1;
		loopCycleStatus = -1000;
		textCycle = 100;
		anInt1540 = 1;
		dynamic = false;
		aBooleanArray1553 = new boolean[10];
		walkAnimIndex = -1;
		turn180AnimIndex = -1;
		turn90CWAnimIndex = -1;
		turn90CCWAnimIndex = -1;
	}

	public final int[] pathX;
	public final int[] pathY;
	public int interactingEntity;
	int anInt1503;
	int anInt1504;
	int anInt1505;
	public String textSpoken;
	public String lastForceChat;
	public int height;
	private int turnDirection;
	int seqStandID;
	int standTurnAnimIndex;
	int anInt1513;
	final int[] hitArray;
	final int[] hitMarkTypes;
	final int[] hitsLoopCycle;

	final int[] hitSource;
	int secondarySeqID;
	int secondarySeqFrame;
	int secondarySeqCycle;
	int graphicId;
	int graphicFrame;
	int currentAnimationTimeRemaining;
	int graphic_cycle;
	int graphicHeight;
	public int nextAnimationFrame;
	public int nextIdleFrame;
	public int nextGraphicFrame;
	int smallXYIndex;
	public int primarySeqID;
	int primarySeqFrame;
	int primarySeqCycle;
	int primarySeqDelay;
	int animationLoops;
	int anInt1531;
	public int loopCycleStatus;
	public int currentHealth;
	public int maxHealth;
	int textCycle;
	int anInt1537;
	int anInt1538;
	int anInt1539;
	int anInt1540;
	boolean dynamic;
	int remaining_steps;
	int anInt1543;
	int anInt1544;
	int anInt1545;
	int anInt1546;
	int initiate_movement;
	int cease_movement;
	int forceMovementDirection;
	public int x;
	public int y;
	int orientation;
	final boolean[] aBooleanArray1553;
	int walkAnimIndex;
	int turn180AnimIndex;
	int turn90CWAnimIndex;
	int turn90CCWAnimIndex;


	public IterableNodeHashTable spotAnims = new IterableNodeHashTable(500);

	public int getTurnDirection() {
		return turnDirection;
	}

	public void setTurnDirection(int turnDirection) {
		this.turnDirection = turnDirection;
	}
}
