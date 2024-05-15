package com.client.definitions.skeletal;

import com.client.InputBuffer;

public class Tkf {

    public int kti;

    public float vAtTkf;

    public float etDelta = Float.MAX_VALUE;

    public float evDelta = Float.MAX_VALUE;

    public float btDelta = Float.MAX_VALUE;

    public float bvDelta = Float.MAX_VALUE;

    Tkf nTkf;

    Tkf() {
    }

    void decode(InputBuffer buffer, int version) {
        this.kti = buffer.readSignedShort();
        this.vAtTkf = buffer.readFloat();
        this.etDelta = buffer.readFloat();
        this.evDelta = buffer.readFloat();
        this.btDelta = buffer.readFloat();
        this.bvDelta = buffer.readFloat();
    }
}