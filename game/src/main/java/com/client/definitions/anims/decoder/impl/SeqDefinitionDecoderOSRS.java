package com.client.definitions.anims.decoder.impl;

import com.client.InputBuffer;
import com.client.definitions.SeqDefinition;
import com.client.definitions.anims.decoder.Decoder;

import java.util.Arrays;
import java.util.HashMap;

public class SeqDefinitionDecoderOSRS implements Decoder<SeqDefinition> {
    @Override
    public void decode(InputBuffer buffer, SeqDefinition definition) {
        while (true) {
            int opcode = buffer.readUnsignedByte();

            if (opcode == 0) {
                break;
            }

            switch (opcode) {
                case 1:
                    int frameCount = buffer.readUnsignedShort();
                    definition.setFrameCount(frameCount);

                    int[] delays = new int[frameCount];
                    int[] primaryFrameIds = new int[frameCount];
                    int[] secondaryFrameIds = new int[frameCount];
                    Arrays.fill(secondaryFrameIds,-1);
                    for (int index = 0; index < frameCount; index++) {
                        delays[index] = buffer.readUnsignedShort();
                    }

                    for (int index = 0; index < frameCount; index++) {
                        primaryFrameIds[index] = buffer.readUnsignedShort();
                    }

                    for (int index = 0; index < frameCount; index++) {
                        primaryFrameIds[index] += buffer.readUnsignedShort() << 16;
                    }

                    definition.setDelays(delays);
                    definition.setFrameIDs(primaryFrameIds);
                    definition.setSecondaryFrameIds(secondaryFrameIds);
                    break;
                case 2:
                    definition.setFrameStep(buffer.readUnsignedShort());
                    break;
                case 3:
                    int count = buffer.readUnsignedByte();
                    int[] masks = new int[count + 1];
                    boolean[] booleanMasks = new boolean[256];
                    for (int i = 0; i < count; i++) {
                        masks[i] = buffer.readUnsignedByte();
                        booleanMasks[masks[i]] = true;
                    }
                    //preserving int masks until i tested this works
                    masks[count] = 0x98967f;
                    definition.setMasks(masks);
                    definition.setBooleanMasks(booleanMasks);
                    break;
                case 4:
                    definition.setStretches(true);
                    break;
                case 5:
                    definition.setForcedPriority(buffer.readUnsignedByte());
                    break;
                case 6:
                    definition.setLeftHandItem(buffer.readUnsignedShort());
                    break;
                case 7:
                    definition.setRightHandItem(buffer.readUnsignedShort());
                    break;
                case 8:
                    definition.setLoopCount(buffer.readUnsignedByte());
                    break;
                case 9:
                    definition.setMoveStyle(buffer.readUnsignedByte());
                    break;
                case 10:
                    definition.setIdleStyle(buffer.readUnsignedByte());
                    break;
                case 11:
                    definition.setReplyMode(buffer.readUnsignedByte());
                    break;
                case 12:
                    count = buffer.readUnsignedByte();
                    int[] chatFrameIds = new int[count];
                    for (int i = 0; i < count; i++) {
                        chatFrameIds[i] = buffer.readUnsignedShort();
                    }
                    for (int i = 0; i < count; i++) {
                        chatFrameIds[i] += buffer.readUnsignedShort() << 16;
                    }
                    definition.setChatFrameIds(chatFrameIds);
                    break;
                case 13:
                    count = buffer.readUnsignedByte();
                    int[] soundEffects = new int[count];
                    for (int i = 0; i < count; i++) {
                        buffer.readUnsignedShort();
                        buffer.readUnsignedByte();
                        buffer.readUnsignedByte();
                        buffer.readUnsignedByte();
                        //soundEffects[i] = buffer.read24BitInt();
                    }
                    definition.setSoundEffects(soundEffects);
                    break;

                case 14:
                    definition.setSkeletalId(buffer.readInt());
                    break;

                case 15:
                    count = buffer.readUnsignedShort();
                    definition.setSoundRelated(new HashMap<>(count));
                    for (int i = 0; i < count; i++) {
                        int var1 = buffer.readUnsignedShort();
                        buffer.readUnsignedShort();
                        buffer.readUnsignedByte();
                        buffer.readUnsignedByte();
                        buffer.readUnsignedByte();
                        //int var2 = buffer.read24BitInt();
                        // definition.getSoundRelated().put(var1, var2);
                    }
                    break;
                case 16:
                    definition.setRangeBegin(buffer.readUnsignedShort());
                    definition.setRangeEnd(buffer.readUnsignedShort());
                    break;
                case 17:
                    boolean[] bMasks = new boolean[256];

                    count = buffer.readUnsignedByte();
                    for (int index = 0; index < count; index++) {
                        bMasks[buffer.readUnsignedByte()] = true;
                    }
                    definition.setBooleanMasks(bMasks);
                    break;
            }
        }
    }
}
