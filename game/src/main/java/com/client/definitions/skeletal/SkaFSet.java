package com.client.definitions.skeletal;

import com.client.InputBuffer;
import com.client.definitions.SeqBase;
import com.client.definitions.anims.datastructure.DualNode;
import com.client.definitions.anims.datastructure.EvictingDualNodeHashTable;
import com.client.definitions.anims.decoder.Decoder;
import com.client.definitions.anims.decoder.impl.SeqBaseDecoderOSRS;
import com.client.util.math.Matrix4f;
import com.client.util.math.Quaternionf;
import com.displee.cache.index.Index;

public class SkaFSet extends DualNode {

    int pfid;

    public TO[][] tt = null;
    public TO[][] vt = null;

    public SeqBase seqBase;

    int fid = 0;

    boolean hExisting;

    public static EvictingDualNodeHashTable cache = new EvictingDualNodeHashTable(100);

    public SkaFSet(Index frameIndex, Index baseIndex, int pfid, boolean useAsFile) {
        this.pfid = pfid;
        byte[] frameData = frameIndex.archive(pfid >> 16)
                .file(pfid & 0xFFFF)
                .getData();
        InputBuffer frameBuffer = new InputBuffer(frameData);
        int v = frameBuffer.readUnsignedByte();
        int bGId = frameBuffer.readUnsignedShort();
        byte[] baseData;
        if (useAsFile) {
            baseData = baseIndex.archive(0).file(bGId).getData();
        } else {
            baseData = baseIndex.archive(bGId).file(0).getData();
        }
        Decoder<SeqBase> baseDecoder = new SeqBaseDecoderOSRS();
        seqBase = new SeqBase();
        seqBase.setGroupId(bGId);
        InputBuffer baseBuffer = new InputBuffer(baseData);
        baseDecoder.decode(baseBuffer, seqBase);
        this.decode(frameBuffer, v);
    }

    public static SkaFSet getSkaf(Index frameIndex, Index baseIndex, int skeletalId) {
        SkaFSet skaf = ((SkaFSet) (cache.get(skeletalId)));
        if (skaf == null) {
            skaf = tryLoadSkaf(frameIndex, baseIndex, skeletalId, false);
            if (skaf != null) {
                cache.put(skaf, skeletalId);
            }
        }
        return skaf;
    }

    public static SkaFSet tryLoadSkaf(Index frameIndex, Index baseIndex, int packedFrameId, boolean useAsFile) {
        boolean loaded = true;
        byte[] frameData = frameIndex.archive(packedFrameId >> 16 & 0xFFFF)
                .file(packedFrameId & 0xFFFF)
                .getData();
        if (frameData == null) {
            loaded = false;
            return null;
        } else {
            int baseGroupId = (frameData[1] & 255) << 8 | frameData[2] & 255;
            byte[] baseData;
            if (useAsFile) {
                baseData = baseIndex.archive(0).file(baseGroupId).getData();
            } else {
                baseData = baseIndex.archive(baseGroupId).file(0).getData();
            }
            if (baseData == null) {
                loaded = false;
            }
            if (!loaded) {
                return null;
            } else {
                try {
                    return new SkaFSet(frameIndex, baseIndex, packedFrameId, useAsFile);
                } catch (Exception e) {
                    return null;
                }
            }
        }
    }

    void decode(InputBuffer buffer, int version) {
        buffer.readUnsignedShort();
        buffer.readUnsignedShort();
        this.fid = buffer.readUnsignedByte();
        int tc = buffer.readUnsignedShort();
        this.vt = new TO[this.seqBase.getABW()
                .getEsLength()][];
        this.tt = new TO[this.seqBase.getLength()][];
        for (int index = 0; index < tc; index++) {
            int tId = buffer.readUnsignedByte();
            ToType to = ToType.lookUpById(tId);
            int ti = buffer.readSignedSmart();
            int tcmpId = buffer.readUnsignedByte();
            ToCmp tcmp = ToCmp.lookup(tcmpId);
            TO curTo = new TO();
            curTo.dc(buffer, version);
            int count = to.getDimensions();
            TO[][] TOS;
            if (to == ToType.TV) {
                TOS = this.vt;
            } else {
                TOS = this.tt;
            }
            if (TOS[ti] == null) {
                TOS[ti] = new TO[count];
            }
            TOS[ti][tcmp.component()] = curTo;
            if (to == ToType.TT) {
                this.hExisting = true;
            }
        }
    }

    public int getFid() {
        return this.fid;
    }

    public boolean hExisting() {
        return this.hExisting;
    }

    public void du(int cT, AB cB, int ti, int fid) {
        Matrix4f clm = Matrix4f.get();
        this.ur(clm, ti, cB, cT);
        this.us(clm, ti, cB, cT);
        this.ut(clm, ti, cB, cT);
        cB.setClm(clm);
        clm.r();
    }

    void ur(Matrix4f clm, int transformIndex, AB cB, int cT) {
        float[] eA = cB.getEa(this.fid);
        float e1 = eA[0];
        float e2 = eA[1];
        float e3 = eA[2];
        if (this.vt[transformIndex] != null) {
            TO to1 = this.vt[transformIndex][0];
            TO to2 = this.vt[transformIndex][1];
            TO to3 = this.vt[transformIndex][2];
            if (to1 != null) {
                e1 = to1.gv(cT);
            }
            if (to2 != null) {
                e2 = to2.gv(cT);
            }
            if (to3 != null) {
                e3 = to3.gv(cT);
            }
        }
        Quaternionf xrq = Quaternionf.take();
        xrq.faa(1.0F, 0.0F, 0.0F, e1);
        Quaternionf yrq = Quaternionf.take();
        yrq.faa(0.0F, 1.0F, 0.0F, e2);
        Quaternionf zrq = Quaternionf.take();
        zrq.faa(0.0F, 0.0F, 1.0F, e3);

        Quaternionf frq = Quaternionf.take();
        frq.mp(zrq);
        frq.mp(xrq);
        frq.mp(yrq);

        Matrix4f rm = Matrix4f.get();
        rm.sfq(frq);
        clm.mp(rm);

        xrq.release();
        yrq.release();
        zrq.release();
        frq.release();
        rm.r();
    }

    void ut(Matrix4f clm, int ti, AB cB, int cT) {
        float[] gt = cB.getTs(this.fid);
        float x = gt[0];
        float y = gt[1];
        float z = gt[2];
        if (this.vt[ti] != null) {
            TO to1 = this.vt[ti][3];
            TO to2 = this.vt[ti][4];
            TO to3 = this.vt[ti][5];
            if (to1 != null) {
                x = to1.gv(cT);
            }
            if (to2 != null) {
                y = to2.gv(cT);
            }
            if (to3 != null) {
                z = to3.gv(cT);
            }
        }
        clm.values[12] = x;
        clm.values[13] = y;
        clm.values[14] = z;
    }

    void us(Matrix4f clm, int ti, AB cB, int cT) {
        float[] scale = cB.getSc(this.fid);
        float x = scale[0];
        float y = scale[1];
        float z = scale[2];
        if (this.vt[ti] != null) {
            TO to1 = this.vt[ti][6];
            TO to2 = this.vt[ti][7];
            TO to3 = this.vt[ti][8];
            if (to1 != null) {
                x = to1.gv(cT);
            }
            if (to2 != null) {
                y = to2.gv(cT);
            }
            if (to3 != null) {
                z = to3.gv(cT);
            }
        }
        Matrix4f sm = Matrix4f.get();
        sm.sc(x, y, z);
        clm.mp(sm);
        sm.r();
    }
}