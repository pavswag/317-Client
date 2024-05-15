package com.client.instruction.impl;


import com.client.Client;
import com.client.graphics.interfaces.InterfaceAnimations;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.health_hud.HealthHud;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;

public class SetWidgetMove implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        boolean hideOnceDone = args.getNextBool();
        int speed = args.getNextInt();
        int endY = args.getNextInt();
        int endX = args.getNextInt();
        int startY = args.getNextInt();
        int startX = args.getNextInt();
        RSInterface child = args.getNextInterface();
        RSInterface parent = args.getNextInterface();
        System.out.println("Invoke setWidgetMoveScript");
        if(parent == null || child == null) {
            System.out.println("Null widget");
            return null;
        }
        if(parent.children == null) {
            System.out.println("Null widget children on parent [" + parent.getId());
            return null;
        }
        int childIndex = -1;
        for(int id = 0; id < parent.children.length; id++)
            if(parent.children[id] == child.getId())
                childIndex = id;
        if(startX == -10) {
            startX = parent.childX[childIndex];
        }
        if(startY == -10) {
            startY = parent.childY[childIndex];
        }
        System.out.println("Moving " + parent.getId() + "/" + child.getId() + " from " + startX + "/" + startY + " too " + endX + "/" + endY);
        InterfaceAnimations.moveInterface(parent.getId(), child.getId(), startX, startY, endX, endY, speed, hideOnceDone);
        return null;
    }
}