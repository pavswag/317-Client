package com.client.instruction.impl;


import com.client.Client;
import com.client.StringUtils;
import com.client.graphics.interfaces.RSInterface;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

import java.util.Arrays;

public class SetWidgetContainerSize implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int itemContainerComponentId = args.getNextInt();
        int currentSizeComponentText = args.getNextInt();
        int maxSizeComponentText = args.getNextInt();
        RSInterface inter = RSInterface.get(itemContainerComponentId);
        RSInterface currentText = RSInterface.get(currentSizeComponentText);
        RSInterface maxText = RSInterface.get(maxSizeComponentText);
        if (inter == null || inter.inventoryItemId == null || currentText == null || maxText == null)
            return null;
        maxText.message = StringUtils.insertCommas(inter.inventoryItemId.length);
        currentText.message = StringUtils.insertCommas(Arrays.stream(inter.inventoryItemId).filter(it -> it > 0).count());
        return null;
    }
}
