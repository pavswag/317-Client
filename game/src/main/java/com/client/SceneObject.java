package com.client;

import com.client.definitions.ObjectDefinition;
import com.client.definitions.SeqDefinition;
import com.client.definitions.VarBit;

final class SceneObject extends Renderable {
	private int animationFrame;
	private final int[] anIntArray1600;
	private final int anInt1601;
	private final int anInt1602;
	private final int anInt1603;
	private final int anInt1604;
	private final int anInt1605;
	private final int anInt1606;
	private SeqDefinition seq;
	private int cycleDelay;
	public static Client clientInstance;
	private final int anInt1610;
	private final int anInt1611;
	private final int anInt1612;

	/*
	 * private ObjectDef method457() { int i = -1; if(anInt1601 != -1) { try {
	 * VarBit varBit = VarBit.overlays[anInt1601]; int k = varBit.anInt648; int l =
	 * varBit.anInt649; int i1 = varBit.anInt650; int j1 =
	 * client.anIntArray1232[i1 - l]; i = clientInstance.variousSettings[k] >> l
	 * & j1; } catch(Exception ex){} } else if(anInt1602 != -1) i =
	 * clientInstance.variousSettings[anInt1602]; if(i < 0 || i >=
	 * anIntArray1600.length || anIntArray1600[i] == -1) return null; else
	 * return ObjectDef.forID(anIntArray1600[i]); }
	 */
	private ObjectDefinition method457() {
		int i = -1;
		if (anInt1601 != -1 && anInt1601 < VarBit.cache.length) {
			VarBit varBit = VarBit.cache[anInt1601];
			int k = varBit.anInt648;
			int l = varBit.anInt649;
			int i1 = varBit.anInt650;
			int j1 = Client.anIntArray1232[i1 - l];
			i = clientInstance.variousSettings[k] >> l & j1;
		} else if (anInt1602 != -1
				&& anInt1602 < clientInstance.variousSettings.length)
			i = clientInstance.variousSettings[anInt1602];
		if (i < 0 || i >= anIntArray1600.length || anIntArray1600[i] == -1)
			return null;
		else
			return ObjectDefinition.lookup(anIntArray1600[i]);
	}

	@Override
	public Model getRotatedModel() {
		int frameId = -1;
		if (seq != null) {
			if(seq.isSkeletalAnimation()) {
				int length = seq.getSkeletalLength();
				animationFrame++;
				if (animationFrame >= length) {
					seq = null;
					//animation_frame = 0;
				}

				if (seq != null) {
					frameId = seq.getSkeletalId();
				}
			} else {
				int step = Client.loopCycle - cycleDelay;
				if (step > 100 && seq.getFrameStep() > 0) {
					step = 100;
				}
				while (step > seq.getDuration(animationFrame)) {
					step -= seq.getDuration(animationFrame);
					animationFrame++;
					if (animationFrame < seq.getFrameCount())
						continue;

					animationFrame -= seq.getFrameStep();
					if (animationFrame >= 0 && animationFrame < seq.getFrameCount())
						continue;

					seq = null;
					break;
				}
				cycleDelay = Client.loopCycle - step;
				if (seq != null) {
					frameId = seq.isSkeletalAnimation() ? seq.getSkeletalId() : seq.getFrameIDs()[animationFrame];
				}
			}
		}
		ObjectDefinition class46;
		if (anIntArray1600 != null)
			class46 = method457();
		else
			class46 = ObjectDefinition.lookup(anInt1610);
		if (class46 == null) {
			return null;
		} else {
			return class46.modelAt(anInt1611, anInt1612, anInt1603, anInt1604, anInt1605, anInt1606, frameId, seq, animationFrame);
		}
	}

	public SceneObject(int i, int j, int k, int l, int i1, int j1, int k1,
					   int l1, boolean flag) {
		anInt1610 = i;
		anInt1611 = k;
		anInt1612 = j;
		anInt1603 = j1;
		anInt1604 = l;
		anInt1605 = i1;
		anInt1606 = k1;
		if (l1 != -1) {
			try {
				seq = SeqDefinition.get(l1);
				animationFrame = 0;
				cycleDelay = Client.loopCycle;
				if (flag && seq.frameStep != -1) {
					animationFrame = (int) (Math.random() * seq.frameCount);
					cycleDelay -= (int) (Math.random() * seq
							.getDuration(animationFrame));
				}
			}catch (Exception e) {

			}
		}
		ObjectDefinition class46 = ObjectDefinition.lookup(anInt1610);
		anInt1601 = class46.varpID;
		anInt1602 = class46.varbitID;
		anIntArray1600 = class46.configs;
	}
}