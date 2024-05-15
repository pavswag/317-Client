package com.client.graphics.interfaces.impl;

import com.client.Sprite;
import com.client.TextDrawingArea;
import com.client.graphics.interfaces.InterfaceAnimations;
import com.client.graphics.interfaces.InterfaceImage;
import com.client.graphics.interfaces.RSInterface;

import java.util.HashMap;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 22/03/2024
 */
public class BlackJack extends RSInterface {

    private static final BlackJack INSTANCE = new BlackJack();
    public static final int INTERFACE_ID = 60950;//60968

    public static BlackJack get() {return INSTANCE;}

    public static String dir = "Interfaces/blackjack/sprite";
    public static String card_dir = "Interfaces/blackjack/cards/sprite";


    public static void mainInterface(TextDrawingArea[] tda) {
        RSInterface main = addInterface(INTERFACE_ID);
        int index = 1;
        int startX = 5, startY = 5;

        addSprite(INTERFACE_ID+index++, 0, dir);
        interfaceCache[60951].sprite1.advancedSprite = true;
        configHoverButton(INTERFACE_ID+index, "Close Interfaces", dir, 3,4,3,3, false, INTERFACE_ID+index++);

        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);

        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);
        addSprite(INTERFACE_ID+index++, 15, dir);

        System.out.println("W=" + (INTERFACE_ID+index));
        addText(INTERFACE_ID+index++, "@gre@0", tda, 2, 16777215);//Statistics
        System.out.println("L=" + (INTERFACE_ID+index));
        addText(INTERFACE_ID+index++, "@red@0", tda, 2, 16777215);//Statistics
        System.out.println("P=" + (INTERFACE_ID+index));
        addText(INTERFACE_ID+index++, "@gre@P: 0", tda, 2, 16777215);//Statistics
        addClickableText(INTERFACE_ID+index++, "<img=16> Rules", "Select", tda, 2, 16777215, false, true, 60, 16);//Rules
        configHoverButton(INTERFACE_ID+index, "Select", dir, 11, 12, 11, 11, false, INTERFACE_ID+index++);//HIT
        configHoverButton(INTERFACE_ID+index, "Select", dir, 5, 6, 5, 5, false, INTERFACE_ID+index++);//STAND
        configHoverButton(INTERFACE_ID+index, "Select", dir, 7, 8, 7, 7, false, INTERFACE_ID+index++);//DOUBLE
        configHoverButton(INTERFACE_ID+index, "Select", dir, 1, 2, 1, 1, false, INTERFACE_ID+index++);//BET
        System.out.println("Bet input=" + (INTERFACE_ID+index));
        addInputField(INTERFACE_ID+index++, 30, 16777215, "0", 148, 25, false, true);//Bet Amount


        addText(INTERFACE_ID+index++, "Hit", tda, 2, 16777215);
        addText(INTERFACE_ID+index++, "Stand", tda, 2, 16777215);
        addText(INTERFACE_ID+index++, "Double", tda, 2, 16777215);
        addText(INTERFACE_ID+index++, "Bet", tda, 2, 16777215);
        System.out.println("BALANCE = " + (INTERFACE_ID + index));
        addText(INTERFACE_ID+index++, "1,501,250,000 GP", tda, 0, 16777215);
        System.out.println("DEALER = " + (INTERFACE_ID + index));
        addText(INTERFACE_ID+index++, "0", tda, 0, 16777215, true);
        System.out.println("PLAYER = " + (INTERFACE_ID + index));
        addText(INTERFACE_ID+index++, "0", tda, 0, 16777215, true);

        index = 1;
        int child = 0;
        setChildren(34, main);
        setBounds(INTERFACE_ID+index++, startX, startY, child++, main);
        setBounds(INTERFACE_ID+index++, startX+474, startY+7, child++, main);

        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);

        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);
        setBounds(INTERFACE_ID+index++, 409, startY+56, child++, main);

        setBounds(INTERFACE_ID+index++, startX+434, startY + 231, child++, main);
        setBounds(INTERFACE_ID+index++, startX+434, startY + 257, child++, main);
        setBounds(INTERFACE_ID+index++, startX+420, startY + 285, child++, main);

        setBounds(INTERFACE_ID+index++, 60, 45, child++, main);

        setBounds(INTERFACE_ID+index++, 20, 171, child++, main);
        setBounds(INTERFACE_ID+index++, 20, 205, child++, main);
        setBounds(INTERFACE_ID+index++, 20, 240, child++, main);
        setBounds(INTERFACE_ID+index++, 20, 275, child++, main);
        setBounds(INTERFACE_ID+index++, 20, 132, child++, main);

        setBounds(INTERFACE_ID+index++, 81, 179, child++, main);
        setBounds(INTERFACE_ID+index++, 81, 213, child++, main);
        setBounds(INTERFACE_ID+index++, 81, 246, child++, main);
        setBounds(INTERFACE_ID+index++, 81, 283, child++, main);
        setBounds(INTERFACE_ID+index++, 74, 80, child++, main);
        setBounds(INTERFACE_ID+index++, 289, 150, child++, main);
        setBounds(INTERFACE_ID+index++, 289, 290, child++, main);
        clearInterfaceForNextGame();
        for(int i = 60953; i <= 60968; i++)
            RSInterface.interfaceCache[i].setHidden(true);
    }
    public static void open() {

        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60953, 5+412, 5+56, 5+412 - 230, 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60954, 5+412, 5+56, 5+412 - 230 + (23 * 1), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60955, 5+412, 5+56, 5+412 - 230 + (23 * 2), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60956, 5+412, 5+56, 5+412 - 230 + (23 * 3), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60957, 5+412, 5+56, 5+412 - 230 + (23 * 4), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60958, 5+412, 5+56, 5+412 - 230 + (23 * 5), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60959, 5+412, 5+56, 5+412 - 230 + (23 * 6), 5+56, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60960, 5+412, 5+56, 5+412 - 230 + (23 * 7), 5+56, 16, false);
        int endX = 187 + (23 * 1);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60961, 5+412, 5+56, 5+412 - 230, 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60962, 5+412, 5+56, 5+412 - 230 + (23 * 1), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60963, 5+412, 5+56, 5+412 - 230 + (23 * 2), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60964, 5+412, 5+56, 5+412 - 230 + (23 * 3), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60965, 5+412, 5+56, 5+412 - 230 + (23 * 4), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60966, 5+412, 5+56, 5+412 - 230 + (23 * 5), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60967, 5+412, 5+56, 5+412 - 230 + (23 * 6), 5+56 + 140, 16, false);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60968, 5+412, 5+56, 5+412 - 230 + (23 * 7), 5+56 + 140, 16, false);
    }
    
    public static void clearInterfaceForNextGame() {

        for(int i = 60953; i <= 60968; i++) {
            interfaceCache[i].sprite1 = imageLoader(15, BlackJack.dir);
            interfaceCache[i].sprite2 = imageLoader(15, BlackJack.dir);
            InterfaceAnimations.moveInterface(INTERFACE_ID, i, -1, -1, 409, 5 + 56, 16, true);
        }

        /*
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60953, 5+412 - 230, 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60954, 5+412 - 230 + (23 * 1), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60955, 5+412 - 230 + (23 * 2), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60956, 5+412 - 230 + (23 * 3), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60957, 5+412 - 230 + (23 * 4), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60958, 5+412 - 230 + (23 * 5), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60959, 5+412 - 230 + (23 * 6), 5+56,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60960, 5+412 - 230 + (23 * 7), 5+56,409, 5+56, 16, true);

        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60961, 5+412 - 230, 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60962, 5+412 - 230 + (23 * 1), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60963, 5+412 - 230 + (23 * 2), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60964, 5+412 - 230 + (23 * 3), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60965, 5+412 - 230 + (23 * 4), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60966, 5+412 - 230 + (23 * 5), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60967, 5+412 - 230 + (23 * 6), 5+56 + 140,409, 5+56, 16, true);
        InterfaceAnimations.moveInterfaceIfShould(INTERFACE_ID, 60968, 5+412 - 230 + (23 * 7), 5+56 + 140,409, 5+56, 16, true);
        */
    }
}
