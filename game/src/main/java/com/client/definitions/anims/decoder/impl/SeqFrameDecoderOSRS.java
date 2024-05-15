package com.client.definitions.anims.decoder.impl;

import com.client.InputBuffer;
import com.client.definitions.SeqBase;
import com.client.definitions.SeqFrame;
import com.client.definitions.anims.decoder.Decoder;
import com.client.definitions.anims.decoder.other.TransformType;

import java.util.List;
import java.util.Map;

public class SeqFrameDecoderOSRS implements Decoder<SeqFrame> {

    @Override
    public void decode(byte[] data, SeqBase transform, Map<Integer, List<SeqFrame>> definitions, int frameGroupId, int frameFileId) {
        try {
            InputBuffer dataBuffer = new InputBuffer(data);
            InputBuffer indicesBuffer = new InputBuffer(data);
            SeqFrame def = new SeqFrame();
            definitions.get(frameGroupId).set(frameFileId, def);

            def.setBase(transform);

            int transformGroupId = indicesBuffer.readUnsignedShort();
            int frameCount = indicesBuffer.readUnsignedByte();

            def.setFrameFileId(frameFileId);
            def.getBase().setGroupId(transformGroupId);

            def.setFrameCount(frameCount);

            dataBuffer.incrementPosition(3 + frameCount);

            int[] groups = new int[frameCount];
            int[] xDeltas = new int[frameCount];
            int[] yDeltas = new int[frameCount];
            int[] zDeltas = new int[frameCount];

            int lastI = -1;
            int groupCount = 0;
            for (int i = 0; i < frameCount; i++) {
                int flag = indicesBuffer.readUnsignedByte();

                if (flag <= 0) {
                    continue;
                }

                if (def.getBase().getTypes()[i] != TransformType.ORIGIN.getValue()) {
                    for (int var10 = i - 1; var10 > lastI; --var10) {
                        if (def.getBase()
                                .getTypes()[var10] == TransformType.ORIGIN.getValue()) {
                            groups[groupCount] = var10;
                            xDeltas[groupCount] = 0;
                            yDeltas[groupCount] = 0;
                            zDeltas[groupCount] = 0;
                            groupCount++;
                            break;
                        }
                    }
                }

                groups[groupCount] = i;
                int defaultValue = 0;
                if (def.getBase().getTypes()[i] == TransformType.SCALE.getValue()) {
                    defaultValue = 128;
                }

                if ((flag & 1) != 0) {
                    xDeltas[groupCount] = dataBuffer.readSignedSmart();
                } else {
                    xDeltas[groupCount] = defaultValue;
                }

                if ((flag & 2) != 0) {
                    yDeltas[groupCount] = dataBuffer.readSignedSmart();
                } else {
                    yDeltas[groupCount] = defaultValue;
                }

                if ((flag & 4) != 0) {
                    zDeltas[groupCount] = dataBuffer.readSignedSmart();
                } else {
                    zDeltas[groupCount] = defaultValue;
                }

                if (def.getBase().getTypes()[i] == TransformType.ROTATION.getValue()) {
                    xDeltas[groupCount] = ((xDeltas[groupCount] & 0xff) << 3) + (xDeltas[groupCount] >> 8 & 0x7);
                    yDeltas[groupCount] = ((yDeltas[groupCount] & 0xff) << 3) + (yDeltas[groupCount] >> 8 & 0x7);
                    zDeltas[groupCount] = ((zDeltas[groupCount] & 0xff) << 3) + (zDeltas[groupCount] >> 8 & 0x7);
                }

                lastI = i;
                groupCount++;
            }

            if (dataBuffer.getPosition() != dataBuffer.getArray().length) {
                throw new RuntimeException();
            }

            def.setBaseCount(groupCount);
            def.setBases(groups);
            def.setXModifier(xDeltas);
            def.setYModifier(yDeltas);
            def.setZModifier(zDeltas);

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
