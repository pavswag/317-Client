package com.client.definitions.skeletal;

import com.client.InputBuffer;

public class ABW {
    AB[] es;

    int mc;

    public ABW(InputBuffer buffer, int length) {
        this.es = new AB[length];
        this.mc = buffer.readUnsignedByte();
        for (int index = 0; index < this.es.length; index++) {
            AB bone = new AB(this.mc, buffer, false);
            this.es[index] = bone;
        }
        try {
            this.ip();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    private void ip() {
        AB[] es = this.es;
        for (AB ab : es) {
            if (ab.b_pid >= 0) {
                ab.p_b = this.es[ab.b_pid];
            }
        }
    }

    public int getEsLength() {
        return this.es.length;
    }

    public AB getAB(int bi) {
        return this.es[bi];
    }

    public AB[] getEs() {
        return this.es;
    }

    public void ut(SkaFSet skaFSet, int tick) {
        this.ut(skaFSet, tick, null, false);
    }

    public void ut(SkaFSet sk, int ct, boolean[] bm, boolean state) {
        int fid = sk.getFid();
        int ti = 0;
        AB[] es = this.getEs();
        for (AB cB : es) {
            if (bm == null || state == bm[ti]) {
                sk.du(ct, cB, ti, fid);
            }
            ti++;
        }
    }
}