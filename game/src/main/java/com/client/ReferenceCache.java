package com.client;

import com.client.sign.Signlink;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class ReferenceCache {

	public ReferenceCache(int i) {
		emptyNodeSub = new Cacheable();
		nodeSubList = new NodeSubList();
		initialCount = i;
		spaceLeft = i;
		nodeCache = new NodeCache();
	}

	public Cacheable get(long l) {
		Cacheable nodeSub = (Cacheable) nodeCache.findNodeByID(l);
		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}
		return nodeSub;
	}

	public void put(Cacheable nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				Cacheable nodeSub_1 = nodeSubList.popTail();
				if(nodeSub_1 != null) {
					nodeSub_1.remove();
					nodeSub_1.unlinkSub();
					if (nodeSub_1 == emptyNodeSub) {
						Cacheable nodeSub_2 = nodeSubList.popTail();
						nodeSub_2.remove();
						nodeSub_2.unlinkSub();
					}
				} else {
					unlinkAll();
				}
			} else {
				spaceLeft--;
			}
			nodeCache.removeFromCache(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			runtimeexception.printStackTrace();
			Signlink.reporterror("47547, " + nodeSub + ", " + l + ", "
					+ (byte) 2 + ", " + runtimeexception.toString());

		}
		//If we reach here, we've ran out of space
		unlinkAll();
	}

	public void unlinkAll() {
		do {
			Cacheable nodeSub = nodeSubList.popTail();
			if (nodeSub != null) {
				nodeSub.remove();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

	private final Cacheable emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final NodeCache nodeCache;
	private final NodeSubList nodeSubList;
}
