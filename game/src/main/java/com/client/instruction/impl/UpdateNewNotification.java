package com.client.instruction.impl;


import com.client.Client;
import com.client.graphics.interfaces.impl.notification.Notification;
import com.client.graphics.interfaces.impl.notification.NotificationInterface;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

public class UpdateNewNotification implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int item = args.getNextInt();
        String title = args.getNextString();
        String topDescription = args.getNextString();
        String bottomDescription = args.getNextString();
        NotificationInterface.notificationQueue.add(new Notification(title, topDescription, bottomDescription, item));
        return null;
    }
}
