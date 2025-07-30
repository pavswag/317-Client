package com.client.graphics.interfaces.impl.pet_duel;

import com.client.Client;
import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.RSInterfaceConstants;
import com.client.graphics.interfaces.WidgetChildEvent;

import java.util.LinkedList;

public class PetDuelInterface extends RSInterface {

    private static String dir = "pet_duel/sprite";

    public static void petDualInterface(TextDrawingArea[] tda) {
//        int interfaceId = 500_000; // getInterfaceId() returns 500,000
//        int index = 1;
//        RSInterface mainInterface = addFullScreen(interfaceId);
//
//        addSprite(500_001, 0, dir);
//        interfaceCache.get(500_001).sprite1.advancedSprite = true;
//
//        addText(500_002, "Vote Genie", tda, 3, RSInterfaceConstants.WHITE, false);
//        interfaceCache.get(500_002).smallFont = true;
//
//        addSprite(500_003, 2, dir);
//        interfaceCache.get(500_003).onRender = (child) -> {
//            int percentage = 76;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addSprite(500_004, 1, dir);
//        interfaceCache.get(500_004).sprite1.advancedSprite = true;
//        addText(500_005, "76/99", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_005).smallFont = true;
//
//        addSprite(500_006, 4, dir);
//        interfaceCache.get(500_006).onRender = (child) -> {
//            int percentage = 33;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addSprite(500_007, 3, dir);
//        interfaceCache.get(500_007).sprite1.advancedSprite = true;
//        addText(500_008, "33/99", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_008).smallFont = true;
//
//        addSprite(500_009, 5, dir);
//        interfaceCache.get(500_009).onRender = (child) -> {
//            int percentage = 50;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addText(500_010, "500/1000", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_010).smallFont = true;
//
//        addSprite(500_011, 0, dir);
//        interfaceCache.get(500_011).sprite1.advancedSprite = true;
//        addText(500_012, "Vote Genie", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_012).smallFont = true;
//
//        addSprite(500_013, 2, dir);
//        interfaceCache.get(500_013).onRender = (child) -> {
//            int percentage = 76;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addSprite(500_014, 1, dir);
//        interfaceCache.get(500_014).sprite1.advancedSprite = true;
//        addText(500_015, "76/99", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_015).smallFont = true;
//
//        addSprite(500_016, 4, dir);
//        interfaceCache.get(500_016).onRender = (child) -> {
//            int percentage = 33;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addSprite(500_017, 3, dir);
//        interfaceCache.get(500_017).sprite1.advancedSprite = true;
//        addText(500_018, "33/99", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_018).smallFont = true;
//
//        addSprite(500_019, 5, dir);
//        interfaceCache.get(500_019).onRender = (child) -> {
//            int percentage = 50;
//            child.sprite1.subWidth = (int) ((percentage / 100.0) * 198);
//        };
//        addText(500_020, "500/1000", tda, 3, RSInterfaceConstants.WHITE, true);
//        interfaceCache.get(500_020).smallFont = true;
//
//        index = 1;
//
//        mainInterface.petDuelChild(500_001, 20, 30);
//        mainInterface.petDuelChild(500_002, 24, 31);
//        mainInterface.petDuelChild(500_003, 35, 57);
//        mainInterface.petDuelChild(500_004, 25, 54);
//        mainInterface.petDuelChild(500_005, 117, 51);
//        mainInterface.petDuelChild(500_006, 35, 73);
//        mainInterface.petDuelChild(500_007, 25, 70);
//        mainInterface.petDuelChild(500_008, 117, 67);
//        mainInterface.petDuelChild(500_009, 35, 92);
//        mainInterface.petDuelChild(500_010, 117, 83);
//
//
//        mainInterface.petDuelChild(500_011, 20, 30);
//        mainInterface.petDuelChild(500_012, 24, 31);
//        mainInterface.petDuelChild(500_013, 35, 57);
//        mainInterface.petDuelChild(500_014, 25, 54);
//        mainInterface.petDuelChild(500_015, 117, 51);
//        mainInterface.petDuelChild(500_016, 35, 73);
//        mainInterface.petDuelChild(500_017, 25, 70);
//        mainInterface.petDuelChild(500_018, 117, 67);
//        mainInterface.petDuelChild(500_019, 35, 92);
//        mainInterface.petDuelChild(500_020, 117, 83);
//        for(int i = 500_011; i <= 500_020; i++) {
//            get(i).onRender = (child) -> {
//                mainInterface.childX[child.widgetChildIndex] = Client.instance.getViewportWidth() - 30 - 228 + child.getOriginalX();
//            };
//        }
    }


    public static void onRender() {
        for(int i = 0; i <= 9; i++) {

        }
    }


    private static int getInterfaceId() {
        return 500_000;
    }
}
