package com.client;


import net.runelite.rs.api.RSNode;

public class Linkable implements RSNode {

	@Override
	public RSNode getNext() {
		return next;
	}

	@Override
	public long getHash() {
		return key;
	}

	@Override
	public RSNode getPrevious() {
		return previous;
	}

	public void remove() {
		if (next == null) {
		} else {
			next.previous = previous;
			previous.next = next;
			previous = null;
			next = null;
		}
	}

	@Override
	public void onUnlink() {

	}

	public Linkable() {
	}

	public long key;
	public Linkable previous;
	public Linkable next;
}
