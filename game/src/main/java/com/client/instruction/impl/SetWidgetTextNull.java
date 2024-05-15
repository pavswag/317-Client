package com.client.instruction.impl;


import com.client.Client;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.health_hud.HealthHud;
import com.client.instruction.InstructionArgs;
import com.client.instruction.ScriptRuntimeException;
import com.client.instruction.VoidInstruction;
import lombok.SneakyThrows;

import java.util.Objects;

public class SetWidgetTextNull implements VoidInstruction {

    @SneakyThrows
    @Override
    public Void invoke(InstructionArgs args) {
        int startInterfaceId = args.getNextInt();
        int lengthExclusive = args.getNextInt();

        for (int i = 0; i < lengthExclusive; i++) {
            if(RSInterface.get(startInterfaceId + i) == null) {
                try {
                    Objects.requireNonNull(RSInterface.get(startInterfaceId + i)).message = "";
                } catch (Exception e) {
                    throw new ScriptRuntimeException("Widget [" + (startInterfaceId + i) + " is null");
                }
            } else {
                Objects.requireNonNull(RSInterface.get(startInterfaceId + i)).message = "";
            }
        }
        return null;
    }
}