package com.client.instruction;

import com.client.Script;
import com.client.ScriptEvent;

import static com.client.Client.runScriptLogic;

/**
 * A class that serves to process any valid instruction that is passed along with the specified arguments.
 * @author Heaven
 */
public class InstructionProcessor {

    /**
     * Attempts to invoke the instruction from the id value and arguments passed through.
     * @param instructionId the int-literal value of the identifier. This value must match one of the identifier values
     *                      within the {@link InstructionId} list.
     * @param args The arguments to be passed along to the respective instruction, if any.
     */
    public static void invoke(int instructionId, InstructionArgs args) {
        try {
            InstructionId identifier = InstructionId.fromId(instructionId);
            if (identifier == InstructionId.NOTHING) {
                System.err.println("Could not invoke missing instruction with id "+ instructionId +".");
                return;
            }

            Instruction<Object> instruction = identifier.getInstruction();
            if (instruction == null) {
                System.err.println("No valid instruction mapped to id "+ identifier +".");

                return;
            }

            System.out.println("Invoking script " + instruction.getClass().getName() +" with instructions [" + args + "]");
            instruction.invoke(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static void runScript(ScriptEvent var0, int var1, int var2) {
        Object[] var3 = var0.args;
        Script var4;
        int var5 = (Integer)var3[0];
        var4 = Script.scripts[var5];

        if (var4 != null) {
            runScriptLogic(var0, var4, var1, var2);
        }

    }

    public static void runScriptEvent(ScriptEvent var0) {
        runScript(var0, 500000, 475000);
    }
}
