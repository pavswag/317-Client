package com.client.instruction.impl;


import com.client.Client;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

public class SetVarp implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int idx = args.getNextInt();
        Client.instance.variousSettings[idx] = args.getNextInt();
        return null;
    }
}
