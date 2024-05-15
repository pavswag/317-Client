package com.client;

import com.client.sign.Signlink;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


final class NodeCache {

	public NodeCache() {
		int i = 1024;// was parameter
		size = i;
		cache = new Linkable[i];
		for (int k = 0; k < i; k++) {
			Linkable node = cache[k] = new Linkable();
			node.previous = node;
			node.next = node;
		}

	}

	public Linkable findNodeByID(long l) {
		Linkable node = cache[(int) (l & size - 1)];
		for (Linkable node_1 = node.previous; node_1 != node; node_1 = node_1.previous)
			if (node_1.key == l)
				return node_1;

		return null;
	}

	public void removeFromCache(Linkable node, long l) {
		try {
			if (node.next != null)
				node.remove();
			Linkable node_1 = cache[(int) (l & size - 1)];
			node.next = node_1.next;
			node.previous = node_1;
			node.next.previous = node;
			node.previous.next = node;
			node.key = l;
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("91499, " + node + ", " + l + ", " + (byte) 7
					+ ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private final int size;
	private final Linkable[] cache;
}
