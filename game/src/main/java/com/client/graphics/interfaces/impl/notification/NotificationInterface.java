package com.client.graphics.interfaces.impl.notification;

import com.client.Client;
import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NotificationInterface extends RSInterface {
    public static double currentY = -73.0D;//-73.0D;

    public static int dropDownTicks = 0;

    public static int dropDownStage = 0;
    private static Notification currentNotification = null;
    public static final List<Notification> notificationQueue = new ArrayList<>();
    public static void processNotifications() {
        if (currentNotification != null) {
            //if (dropDownTicks == 0)
             //   Client.instance.walkableInterfaceFourId = 46500;
            switch (dropDownStage) {
                case 0:
                    if (currentY >= 6.0D) {
                        dropDownStage = 1;
                        dropDownTicks = 0;
                    }
                    currentY += 2.0D;
                    break;
                case 1:
                    if (dropDownTicks >= 240) {
                        dropDownStage = 2;
                        dropDownTicks = 0;
                    }
                    break;
                case 2:
                    if (currentY <= -73.0D)
                        dropDownStage = 10;
                    currentY -= 3.0D;
                    break;
                case 3:
                    if (currentY <= -73.0D)
                        dropDownStage = 10;
                    currentY -= 5.0D;
                    break;
                case 10:
                    currentNotification = null;
                    break;
            }
            dropDownTicks++;
        } else if (notificationQueue.size() > 0) {
            currentNotification = notificationQueue.get(0);
            (RSInterface.interfaceCache[46502]).message = currentNotification.getTitle();
            (RSInterface.interfaceCache[46503]).message = currentNotification.getTopDescription();
            (RSInterface.interfaceCache[46504]).message = currentNotification.getBottomDescription();
            interfaceCache[46508].inventoryItemId[0] = currentNotification.getItem() + 1;
            interfaceCache[46509].inventoryItemId[0] = currentNotification.getItem() + 1;
            if (currentNotification.getBottomDescription().length() > 1) {
                RSInterface.changeInterfaceImage(46501, 0);
                (RSInterface.interfaceCache[46507]).height = 73;
            } else {
                RSInterface.changeInterfaceImage(46501, 1);
                (RSInterface.interfaceCache[46504]).message = "";
                (RSInterface.interfaceCache[46507]).height = 59;
            }
            notificationQueue.remove(0);
            currentY = -73.0D;
            //Client.instance.walkableInterfaceFourId = 46500;
            dropDownStage = 0;
            dropDownTicks = 0;
        }
    }
    public static void notification(TextDrawingArea[] tda) {
        RSInterface tab = addInterface(46500);
        addSprite(46501, 0, "notifications/dropdown");
        setExtraImageForInterface(46501, 1, "notifications/dropdown");
        addText(46502, "<img=90> Notification Title!", 16750623, true, true, 0, tda, 2);
        addText(46503, "<img=82>@ora@Testing line @or2@1@ora@ description", 16750623, true, true, 0, tda, 1);
        addText(46504, "<img=62> @ora@Testing line @or2@2@ora@ description", 16750623, true, true, 0, tda, 1);
        addClickableText(46507, "", "click notification", tda, 0, 16750623, false, false, 315);
        (RSInterface.interfaceCache[46507]).width = 315;
        (RSInterface.interfaceCache[46507]).height = 73;
        addItem(46508, new String[0]);
        addItem(46509, new String[0]);
        interfaceCache[46508].parentID = 46500;
        interfaceCache[46509].parentID = 46500;
        interfaceCache[46508].inventoryItemId[0] = 4152;
        interfaceCache[46509].inventoryItemId[0] = 4152;
        interfaceCache[46508].inventoryAmounts[0] = 1;
        interfaceCache[46509].inventoryAmounts[0] = 1;
        interfaceCache[46508].hideInvStackSizes = true;
        interfaceCache[46509].hideInvStackSizes = true;
        int interfaceWidth = 315;
        tab.totalChildren(7);
        int id = 0;
        tab.child(id++, 46501, 0, 0);
        tab.child(id++, 46502, interfaceWidth / 2, 12);
        tab.child(id++, 46503, interfaceWidth / 2, 30);
        tab.child(id++, 46504, interfaceWidth / 2, 48);
        tab.child(id++, 46507, 0, 0);
        tab.child(id++, 46508, 4, 18);
        tab.child(id++, 46509, 281, 18);
        Client.instance.parallelWidgetList.add(tab);
    }
}
