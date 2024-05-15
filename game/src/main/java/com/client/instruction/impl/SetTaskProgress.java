package com.client.instruction.impl;


import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.impl.tasks.TaskInterfaceActions;
import com.client.graphics.interfaces.builder.impl.tasks.model.TaskEntryType;
import com.client.graphics.interfaces.impl.Interfaces;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;
import com.client.utilities.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SetTaskProgress implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        String typeString = args.getNextString();
        String title = args.getNextString();
        String progress = args.getNextString();
        int claimed = args.getNextInt();


        TaskInterfaceActions actions = Interfaces.taskInterface.actions;
        TaskEntryType type = TaskEntryType.valueOf(typeString.toUpperCase());
        List<String> progressList;
        try {
            progressList = JsonUtil.fromJsonString(progress, new TypeToken<List<String>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(progressList != null)
            actions.setProgress(type, title, progressList, claimed == 1);

        return null;
    }
}