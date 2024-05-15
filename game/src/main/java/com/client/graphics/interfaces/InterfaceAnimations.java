package com.client.graphics.interfaces;

import com.client.graphics.interfaces.impl.BlackJack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 22/03/2024
 */
public class InterfaceAnimations {
    public static HashMap<Integer, InterfaceAnimation> interfaceAnimations = new HashMap<>();

    public static void moveInterface(int parentId, int childId, int startX, int startY, int endX, int endY, int timeInMainGameTicks) {
        moveInterface(parentId, childId, startX, startY, endX, endY, timeInMainGameTicks, false);
    }
    public static void moveInterfaceIfShould(int parentId, int childId, int startX, int startY, int endX, int endY, int timeInMainGameTicks, boolean hideOncedone) {
        RSInterface parentInterface = RSInterface.interfaceCache[parentId];
        if (parentInterface == null)
            return;
        int childInterfaceIndex = -1;
        for (int a = 0; a < parentInterface.children.length; a++) {
            if (parentInterface.children[a] == childId) {
                childInterfaceIndex = a;
                break;
            }
        }
        if (childInterfaceIndex == -1)
            return;

        if (startX != -1 && startY != -1) {
            if(parentInterface.childX[childInterfaceIndex] != startX || parentInterface.childY[childInterfaceIndex] != startY)
                return;
            parentInterface.childX[childInterfaceIndex] = startX;
            parentInterface.childY[childInterfaceIndex] = startY;
        } else {
            startX = parentInterface.childX[childInterfaceIndex];
            startY = parentInterface.childY[childInterfaceIndex];
        }
        if(RSInterface.interfaceCache[childId].isHidden())
            RSInterface.interfaceCache[childId].setHidden(false);
        if (timeInMainGameTicks == 0) {
            parentInterface.childX[childInterfaceIndex] = endX;
            parentInterface.childY[childInterfaceIndex] = endY;
            return;
        }
        if (startX == endX && startY == endY)
            return;
        interfaceAnimations.put(Integer.valueOf(childId), new InterfaceAnimation(parentId, childId, timeInMainGameTicks, startX, startY, endX, endY, hideOncedone));
    }
    public static void moveInterface(int parentId, int childId, int startX, int startY, int endX, int endY, int timeInMainGameTicks, boolean hideOncedone) {
        RSInterface parentInterface = RSInterface.interfaceCache[parentId];
        if (parentInterface == null)
            return;
        int childInterfaceIndex = -1;
        for (int a = 0; a < parentInterface.children.length; a++) {
            if (parentInterface.children[a] == childId) {
                childInterfaceIndex = a;
                break;
            }
        }
        if (childInterfaceIndex == -1)
            return;
        if(RSInterface.interfaceCache[childId].interfaceHidden)
            RSInterface.interfaceCache[childId].interfaceHidden = false;
        if (timeInMainGameTicks == 0) {
            parentInterface.childX[childInterfaceIndex] = endX;
            parentInterface.childY[childInterfaceIndex] = endY;
            return;
        }
        if (startX != -1 && startY != -1) {
            parentInterface.childX[childInterfaceIndex] = startX;
            parentInterface.childY[childInterfaceIndex] = startY;
        } else {
            startX = parentInterface.childX[childInterfaceIndex];
            startY = parentInterface.childY[childInterfaceIndex];
        }
        if (startX == endX && startY == endY) {
            if(hideOncedone)
                RSInterface.interfaceCache[childId].interfaceHidden = true;
            return;
        }
        interfaceAnimations.put(Integer.valueOf(childId), new InterfaceAnimation(parentId, childId, timeInMainGameTicks, startX, startY, endX, endY, hideOncedone));
    }

    public static void processAnimations() {
        if (!interfaceAnimations.isEmpty())
            for (Iterator<Map.Entry<Integer, InterfaceAnimation>> it = interfaceAnimations.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Integer, InterfaceAnimation> entry = it.next();
                InterfaceAnimation interfaceAnimation = entry.getValue();
                RSInterface parentInterface = RSInterface.interfaceCache[interfaceAnimation.parentId];
                if (parentInterface == null) {
                    it.remove();
                    return;
                }
                int childInterfaceIndex = -1;
                for (int a = 0; a < parentInterface.children.length; a++) {
                    if (parentInterface.children[a] == interfaceAnimation.childId) {
                        childInterfaceIndex = a;
                        break;
                    }
                }
                if (childInterfaceIndex == -1) {
                    return;
                }
                int currentX = parentInterface.childX[childInterfaceIndex];
                int currentY = parentInterface.childY[childInterfaceIndex];
                double totalMovementToMakeX = interfaceAnimation.xMovementPerTick + interfaceAnimation.xMovementLeft;
                interfaceAnimation.xMovementLeft = totalMovementToMakeX % 1.0D;
                totalMovementToMakeX = (int)totalMovementToMakeX;
                double totalMovementToMakeY = interfaceAnimation.yMovementPerTick + interfaceAnimation.yMovementLeft;
                interfaceAnimation.yMovementLeft = totalMovementToMakeY % 1.0D;
                totalMovementToMakeY = (int)totalMovementToMakeY;
                parentInterface.childX[childInterfaceIndex] = currentX + (int)totalMovementToMakeX;
                parentInterface.childY[childInterfaceIndex] = currentY + (int)totalMovementToMakeY;
                boolean isXGoingUp = (interfaceAnimation.xMovementPerTick > 0.0D);
                boolean isYGoingUp = (interfaceAnimation.yMovementPerTick > 0.0D);
                if (isXGoingUp && parentInterface.childX[childInterfaceIndex] > interfaceAnimation.endX) {
                    parentInterface.childX[childInterfaceIndex] = interfaceAnimation.endX;
                } else if (!isXGoingUp && parentInterface.childX[childInterfaceIndex] < interfaceAnimation.endX) {
                    parentInterface.childX[childInterfaceIndex] = interfaceAnimation.endX;
                }
                if (isYGoingUp && parentInterface.childY[childInterfaceIndex] > interfaceAnimation.endY) {
                    parentInterface.childY[childInterfaceIndex] = interfaceAnimation.endY;
                } else if (!isYGoingUp && parentInterface.childY[childInterfaceIndex] < interfaceAnimation.endY) {
                    parentInterface.childY[childInterfaceIndex] = interfaceAnimation.endY;
                }
                if (parentInterface.childX[childInterfaceIndex] == interfaceAnimation.endX && parentInterface.childY[childInterfaceIndex] == interfaceAnimation.endY) {
                    if(interfaceAnimation.hideOnceDone) {
                        RSInterface.interfaceCache[parentInterface.children[childInterfaceIndex]].setHidden(true);
                    }
                    it.remove();
                }
            }
    }

    static class InterfaceAnimation {
        int parentId;

        int childId;

        int ticksLeft;

        double xMovementPerTick;

        double yMovementPerTick;

        double xMovementLeft;

        double yMovementLeft;

        int endX;

        int endY;

        boolean hideOnceDone;

        public InterfaceAnimation(int parentId, int childId, int ticksLeft, int startX, int startY, int endX, int endY) {
            this.parentId = parentId;
            this.childId = childId;
            this.ticksLeft = ticksLeft;
            this.xMovementPerTick = (endX - startX) / ticksLeft;
            this.yMovementPerTick = (endY - startY) / ticksLeft;
            this.endX = endX;
            this.endY = endY;
            this.hideOnceDone = false;
        }
        public InterfaceAnimation(int parentId, int childId, int ticksLeft, int startX, int startY, int endX, int endY, boolean hideOnceDone) {
            this.parentId = parentId;
            this.childId = childId;
            this.ticksLeft = ticksLeft;
            this.xMovementPerTick = (endX - startX) / ticksLeft;
            this.yMovementPerTick = (endY - startY) / ticksLeft;
            this.endX = endX;
            this.endY = endY;
            this.hideOnceDone = hideOnceDone;
        }
    }
}