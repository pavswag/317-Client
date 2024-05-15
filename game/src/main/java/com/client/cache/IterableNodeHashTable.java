package com.client.cache;

import com.client.Linkable;
import net.runelite.api.Node;
import net.runelite.rs.api.RSIterableNodeHashTable;

import java.util.Iterator;

public final class IterableNodeHashTable implements Iterable, RSIterableNodeHashTable {
    int size;
    Linkable[] buckets;
    Linkable currentGet;
    Linkable current;
    int index = 0;

    public IterableNodeHashTable(int var1) {
        this.size = var1;
        this.buckets = new Linkable[var1];
        for (int var2 = 0; var2 < var1; ++var2) {
            Linkable var3 = this.buckets[var2] = new Linkable();
            var3.previous = var3;
            var3.next = var3;
        }
    }

    public Linkable get(long var1) {
        Linkable var3 = this.buckets[((int) (var1 & ((long) (this.size - 1))))];
        for (this.currentGet = var3.previous; var3 != this.currentGet; this.currentGet = this.currentGet.previous) {
            if (this.currentGet.key == var1) {
                Linkable var4 = this.currentGet;
                this.currentGet = this.currentGet.previous;
                return var4;
            }
        }
        this.currentGet = null;
        return null;
    }

    @Override
    public void put(Node node, long hash) {
        insert((Linkable) node, hash);
    }

    public void insert(Linkable var1, long var2) {
        if (var1.next != null) {
            var1.remove();
        }
        Linkable var4 = this.buckets[((int) (var2 & ((long) (this.size - 1))))];
        var1.next = var4.next;
        var1.previous = var4;
        var1.next.previous = var1;
        var1.previous.next = var1;
        var1.key = var2;
    }

    public void clear() {
        for (int var1 = 0; var1 < this.size; ++var1) {
            Linkable var2 = this.buckets[var1];
            while (true) {
                Linkable var3 = var2.previous;
                if (var3 == var2) {
                    break;
                }
                var3.remove();
            }
        }
        this.currentGet = null;
        this.current = null;
    }
    public Linkable first() {
        this.index = 0;
        return this.next();
    }
    public Linkable next() {
        Linkable var1;
        if (this.index > 0 && this.buckets[this.index - 1] != this.current) {
            var1 = this.current;
            this.current = var1.previous;
            return var1;
        } else {
            do {
                if (this.index >= this.size) {
                    return null;
                }
                var1 = this.buckets[this.index++].previous;
            } while (var1 == this.buckets[this.index - 1] );
            this.current = var1.previous;
            return var1;
        }
    }

    public Iterator iterator() {
        return new IterableNodeHashTableIterator(this);
    }
}