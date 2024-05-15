package com.client.instruction.impl;


import com.client.Client;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.health_hud.HealthHud;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

public class SetHealthHud implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int type = args.getNextInt();
        int currentHealth = args.getNextInt();
        int maxHealth = args.getNextInt();
        if(type >= 0 || type < HealthHud.HudType.values().length)
            HealthHud.setHudType(HealthHud.HudType.values()[type]);
        RSInterface header = RSInterface.interfaceCache[HealthHud.HEADER_ID];
        header.message = args.getNextString();
        if(maxHealth == 0) {
            RSInterface.interfaceCache[HealthHud.PROGRESS_WIDGET_ID].message = "";
        } else {
            RSInterface.interfaceCache[HealthHud.PROGRESS_WIDGET_ID].message = currentHealth + "/" + maxHealth;
        }
        HealthHud.healthHudTimeoutTick = Client.instance.getGameCycle() + (50 * 5);
        return null;
    }
}