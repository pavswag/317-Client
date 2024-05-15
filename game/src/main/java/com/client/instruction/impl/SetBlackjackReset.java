package com.client.instruction.impl;


import com.client.graphics.interfaces.InterfaceAnimations;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.BlackJack;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

public class SetBlackjackReset implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        BlackJack.clearInterfaceForNextGame();
        return null;
    }
}