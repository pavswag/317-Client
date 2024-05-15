package com.client.graphics.interfaces.impl;

import com.client.Sprite;
import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;
public class DropParty extends RSInterface {
    public static final int INTERFACE_ID = 31560;

    private static final Sprite BG = new Sprite("DropPartychest/BACKGROUND");

    public static void dropPartyInterface(TextDrawingArea[] tda) {
        RSInterface inter = addInterface(31560);
        inter.totalChildren(5);
        addSprite(31561, BG);
        inter.child(0, 31561, 15, 15);
        inter.child(1, 31562, 70, 50);
        addItemContainer(31565, 9, 1, 10, 4, false, new String[] { "Remove" });
        inter.child(2, 31565, 50, 250);
        addButton(31564, 1, "DropPartychest/BUTTON", "Confirm");
        inter.child(3, 31564, 410, 256);
        addHoverButton(33932, "Interfaces/HelpInterface/IMAGE", 2, 16, 16, "Close", -1, 33932, 3);
        inter.child(4, 33932, 473, 23);
        RSInterface inter2 = addInterface(31562);
        inter2.totalChildren(1);
        inter2.width = 400;
        inter2.height = 180;
        inter2.scrollMax = 1000;
        addItemContainer(31563, 9, 100, 10, 4, false, new String[0]);
        inter2.child(0, 31563, 0, 0);
    }
}

