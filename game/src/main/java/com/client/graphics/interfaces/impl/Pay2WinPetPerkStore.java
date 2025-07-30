package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

import java.util.Objects;

public class Pay2WinPetPerkStore extends RSInterface {
    public static void shopWidget(TextDrawingArea[] tda) {
        // Interface background
        RSInterface widget = addInterface(23382);
        addSprite(23383, 1, "Interfaces/Shop/IMAGE");
        addHoverButton(23384, "Interfaces/Shop/IMAGE", 2, 21, 21, "Close Window", 201, 23384, 5);
        addText(23385, "Pay 2 Win Perk Store", tda, 2, 0xFFA500, false, true);

        RSInterface scroll = addInterface(23386);
        setChildren(100, scroll);
        scroll.height = 252;
        scroll.width = 465;
        scroll.scrollMax = 1950;
        int widgetId = 23387;
        int scrollChildIndex = 0;
        int childRecY = 0;
        int perkNameY = 5;
        int perkDescriptionY = 37;
        int perkPriceY = 59;

        for(int i = 0; i < 25; i++) {
            RSInterface.addRectangle(widgetId, 466, 75, 0x221e1a, 0, true);
            Objects.requireNonNull(get(widgetId)).secondaryColor = 0x252220;
            Objects.requireNonNull(get(widgetId)).atActionType = 1;
            Objects.requireNonNull(get(widgetId)).tooltip = "<col=ff9933>Purchase Perk!";
            scroll.child(scrollChildIndex++, widgetId++, 0, childRecY);
            childRecY += 77;

            addText(widgetId, "", tda, 2, 0xff9933, true);
            scroll.child(scrollChildIndex++, widgetId++, 238, perkNameY);
            perkNameY += 77;

            addText(widgetId, "<", tda, 1, 0xff9933, true);
            scroll.child(scrollChildIndex++, widgetId++, 238, perkDescriptionY);
            perkDescriptionY += 77;

            addText(widgetId, "", tda, 2, 0xff9933, true);
            scroll.child(scrollChildIndex++, widgetId++, 420, perkPriceY);
            perkPriceY += 77;
        }

        widget.totalChildren(4);
        widget.child(0, 23383, 10, 10);
        widget.child(1, 23384, 485, 20);
        widget.child(2, 23385, 200, 20);
        widget.child(3, 23386, 20, 55);

    }
}
