package com.client;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Deque {

	public Deque() {
		head = new Linkable();
		head.previous = head;
		head.next = head;
	}

	public void insertHead(Linkable node) {
		if (node.next != null)
			node.remove();
		node.next = head.next;
		node.previous = head;
		node.next.previous = node;
		node.previous.next = node;
	}

	public void insertTail(Linkable node) {
		if (node == null) {
			return;
		}
		if (node.next != null)
			node.remove();
		node.next = head;
		node.previous = head.previous;
		node.next.previous = node;
		node.previous.next = node;
	}

	public final void method894(Linkable class3, Linkable class3_27_) {
		if (class3.previous != null) {
			class3.remove();
		}

		class3.next = class3_27_;
		class3.previous = class3_27_.previous;
		class3.previous.next = class3;
		class3.next.previous = class3;
	}

	
	public Linkable popHead() {
		Linkable node = head.previous;
		if (node == head) {
			return null;
		} else {
			node.remove();
			return node;
		}
	}

	public Linkable reverseGetFirst() {
		Linkable node = head.previous;
		if (node == head) {
			current = null;
			return null;
		} else {
			current = node.previous;
			return node;
		}
	}

	public Linkable getFirst() {
		Linkable node = head.next;
		if (node == head) {
			current = null;
			return null;
		} else {
			current = node.next;
			return node;
		}
	}

	public Linkable reverseGetNext() {
		Linkable node = current;
		if (node == head) {
			current = null;
			return null;
		} else {
			current = node.previous;
			return node;
		}
	}

	public Linkable getNext() {
		Linkable node = current;
		if (node == head) {
			current = null;
			return null;
		}
		current = node.next;
		return node;
	}

	public void removeAll() {
		if (head.previous == head)
			return;
		do {
			Linkable node = head.previous;
			if (node == head)
				return;
			node.remove();
		} while (true);
	}

	public final Linkable head;
	private Linkable current;
}
