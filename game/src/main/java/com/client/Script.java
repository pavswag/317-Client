package com.client;

import java.util.LinkedHashMap;
import java.util.Map;

import com.client.osrs.CacheWrapper;
import lombok.Data;

import static net.runelite.api.scripts.Opcodes.*;

@Data
public class Script
{
    public static Script[] scripts;
    public String field1008;

    public static void loadScripts(CacheWrapper cacheWrapper) {
        scripts = new Script[cacheWrapper.getIndex(12).archives().length];
        for(int i = 0; i < scripts.length; i++) {
            scripts[i] = decode(i, cacheWrapper.getCache().data(12, i));
            if(scripts[i].field1008 != null && !scripts[i].field1008.isEmpty())
                System.out.println("Loaded script [" + scripts[i].field1008 + "]");
        }
        System.out.println("Loaded [" + scripts.length + "] CS2 Scripts");
    }


    public static Script decode(int id, byte[] bytes) {
        Script def = new Script();
        def.setId(id);
        if(bytes == null || bytes.length < 1)
            return def;
        try {
            Buffer in = new Buffer(bytes);

            in.setOffset(in.getLength() - 2);
            int switchLength = in.readUnsignedShort();

            // 2 for switchLength + the switch data + 12 for the param/vars/stack data
            int endIdx = in.getLength() - 2 - switchLength - 12;
            in.setOffset(endIdx);
            int numOpcodes = in.readInt();
            int localIntCount = in.readUnsignedShort();
            int localStringCount = in.readUnsignedShort();
            int intStackCount = in.readUnsignedShort();
            int stringStackCount = in.readUnsignedShort();

            int numSwitches = in.readUnsignedByte();
            if (numSwitches > 0) {
                Map<Integer, Integer>[] switches = new Map[numSwitches];
                def.setSwitches(switches);

                for (int i = 0; i < numSwitches; ++i) {
                    switches[i] = new LinkedHashMap<>();

                    int count = in.readUnsignedShort();
                    while (count-- > 0) {
                        int key = in.readInt();
                        int pcOffset = in.readInt();

                        switches[i].put(key, pcOffset);
                    }
                }
            }

            def.setLocalIntCount(localIntCount);
            def.setLocalStringCount(localStringCount);
            def.setIntStackCount(intStackCount);
            def.setStringStackCount(stringStackCount);

            in.setOffset(0);
            def.field1008 = in.readNullTerminatedString();

            int[] instructions = new int[numOpcodes];
            int[] intOperands = new int[numOpcodes];
            String[] stringOperands = new String[numOpcodes];

            def.setInstructions(instructions);
            def.setIntOperands(intOperands);
            def.setStringOperands(stringOperands);

            int opcode;
            for (int i = 0; in.currentPosition < endIdx; instructions[i++] = opcode) {
                opcode = in.readUnsignedShort();
                if (opcode == SCONST) {
                    stringOperands[i] = in.readNullTerminatedString();
                } else if (opcode < 100 && opcode != RETURN && opcode != POP_INT && opcode != POP_STRING) {
                    intOperands[i] = in.readInt();
                } else {
                    intOperands[i] = in.readUnsignedByte();
                }
            }
        } catch (Exception e) {
            return def;
        }
        return def;
    }
    private int id;
    private int[] instructions;
    private int[] intOperands;
    private String[] stringOperands;
    private int intStackCount;
    private int stringStackCount;
    private int localIntCount;
    private int localStringCount;
    private Map<Integer, Integer>[] switches;
}
