package com.client.instruction.impl;


import com.client.graphics.interfaces.InterfaceAnimations;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.BlackJack;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

import static com.client.graphics.interfaces.RSInterface.imageLoader;

public class SetBlackjackCard implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int spriteId = args.getNextInt();
        System.out.println("Setting card sprite too sprite " + spriteId);
        RSInterface widget = args.getNextInterface();
        widget.sprite1 = imageLoader(spriteId, BlackJack.card_dir);
        widget.sprite2 = imageLoader(spriteId, BlackJack.card_dir);
        return null;
    }
}