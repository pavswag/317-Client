package com.client.definitions.anims.decoder.impl;

import com.client.InputBuffer;
import com.client.definitions.SeqBase;
import com.client.definitions.anims.decoder.Decoder;
import com.client.definitions.skeletal.ABW;

public class SeqBaseDecoderOSRS implements Decoder<SeqBase> {

    @Override
    public void decode(InputBuffer buffer, SeqBase definition) {
        int amount = buffer.readUnsignedByte();
        definition.setLength(amount);
        int[] types = new int[amount];
        int[][] groupLabels = new int[amount][];

        for (int i = 0; i < amount; i++) {
            types[i] = buffer.readUnsignedByte();
        }

        for (int i = 0; i < amount; i++) {
            groupLabels[i] = new int[buffer.readUnsignedByte()];
        }

        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < groupLabels[i].length; j++) {
                groupLabels[i][j] = buffer.readUnsignedByte();
            }
        }

        if (buffer.getPosition() < buffer.getArray().length) {
            int length = buffer.readUnsignedShort();
            if (length > 0) {
                ABW abw = new ABW(buffer, length);
                definition.setABW(abw);
            }
        }

        definition.setTypes(types);
        definition.setGroupLabels(groupLabels);
    }
}