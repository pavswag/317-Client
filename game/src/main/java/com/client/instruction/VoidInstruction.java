package com.client.instruction;

import com.client.graphics.interfaces.RSInterface;

/**
 * A client instruction that performs an operation with no value expected in return.
 * @author Heaven
 */
public interface VoidInstruction extends Instruction<Void> {

    Void invoke(InstructionArgs args);


}