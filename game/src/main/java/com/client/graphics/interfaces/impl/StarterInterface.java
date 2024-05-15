package com.client.graphics.interfaces.impl;

import com.client.Configuration;
import com.client.Sprite;
import com.client.connection.Connection;
import com.client.graphics.interfaces.Configs;
import com.client.graphics.interfaces.RSInterface;

public class StarterInterface extends RSInterface {

    private static final StarterInterface INSTANCE = new StarterInterface();
    private static final int INTERFACE_ID = 24303;

    public static StarterInterface get() {
        return INSTANCE;
    }

    private Sprite bg = new Sprite("starter_interface/BACKGROUND");
    private Sprite line = new Sprite("starter_interface/LINE");
    private Sprite thumb = new Sprite("starter_interface/THUMB");
    private Sprite button = new Sprite("starter_interface/button");
    private Sprite buttonSelected = new Sprite("starter_interface/button_selected");

    private static final String MODE_HEADER_COLOUR = "<col=" + 0xFFFFFFFF + ">";
    private static final String MODE_DESC_COLOUR = " <col=" + RSInterface.DEFAULT_TEXT_COLOR + ">";
    private String[] GAME_MODES = {
            "Standard Mode\\nThe standard " + Configuration.CLIENT_TITLE + " experience.",
            "Ironman Mode\\nAn Iron Man cannot trade, stake, receive PK loot, \\nor scavenge dropped items.",
            "Hardcore Ironman\\nIn addition to the standard Iron Man rules, Hardcore\\nhas 1 life. A dangerous death will result in being\\ndowngraded to a standard Iron Man.",
            "Ultimate Ironman\\nIn addition to the standard Iron Man rules, an\\nUltimate Iron Man cannot use banks.",
            "Group Ironman\\nYou'll be taken to a closed area where you can\\nSelect a group to play with in Ironman mode.\\nYou'll be able to trade and share resources.",
            "Wildyman Mode\\n10% extra DropRate, Locked to Wilderness.\\nconstant white skull.\\n(Changeable stats when maxed in specific skill).",
            "Hardcore Wildyman Mode\\n25% extra DropRate, Locked to Wilderness.\\nconstant red skull.\\n(Changeable stats when maxed in specific skill)."
    };

    private String[] W2GAME_MODES = {
            "Standard Mode\\nThe standard " + Configuration.CLIENT_TITLE + " experience.",
            "Ironman Mode\\nAn Iron Man cannot trade, stake, receive PK loot, \\nor scavenge dropped items.",
            "Hardcore Ironman\\nIn addition to the standard Iron Man rules, Hardcore\\nhas 1 life. A dangerous death will result in being\\ndowngraded to a standard Iron Man.",
            "Ultimate Ironman\\nIn addition to the standard Iron Man rules, an\\nUltimate Iron Man cannot use banks.",
            "Group Ironman\\nYou'll be taken to a closed area where you can\\nSelect a group to play with in Ironman mode.\\nYou'll be able to trade and share resources.",
            "Wildyman Mode\\n10% extra DropRate, Locked to Wilderness.\\nconstant white skull.\\n(Changeable stats when maxed in specific skill).",
            "Hardcore Wildyman Mode\\n25% extra DropRate, Locked to Wilderness.\\nconstant red skull.\\n(Changeable stats when maxed in specific skill)."
    };

    private StarterInterface() {}

    public void load() {
        int interfaceId = INTERFACE_ID;
        int childId = 0;

        RSInterface inter = addInterface(interfaceId++);
        inter.totalChildren(5);

        addSprite(interfaceId, bg);
        inter.child(childId++, interfaceId++, 20, 24);

        addText(interfaceId, 2, RSInterface.DEFAULT_TEXT_COLOR, true,"Game Mode Setup");
        inter.child(childId++, interfaceId++, 248, 34);

        int buttonX = 194;
        int buttonY = 278;
        addClickableText(interfaceId, "Confirm","Choose", RSInterface.defaultTextDrawingAreas, 1, 0xff981f,
                false,false,60, 16);
        inter.child(childId++, interfaceId++, buttonX, buttonY);

        addSprite(interfaceId, thumb);
        inter.child(childId++, interfaceId++, buttonX - 22, buttonY - 4);


        inter.child(childId++, interfaceId, 46, 84);
        interfaceId = loadContainer(interfaceId);
    }

    private int loadContainer(int interfaceId) {
        String[] mode = GAME_MODES;
        if (Configuration.CONNECTION.equals(Connection.WORLD_1)) {
            mode = W2GAME_MODES;
        }

        RSInterface inter = addInterface(interfaceId++);
        inter.width = 303;
        inter.height = 189;
        inter.scrollMax = inter.height + 8 + (mode.length > 4 ? (mode.length - 4) * 46 : 0);
        inter.totalChildren(mode.length * 3);
        int childId = 0;

        for (int index = 0; index < mode.length; index++) {
            int y = index * 47;

            // Button
            addConfigButton(interfaceId, button, buttonSelected, "Select mode", index, Configs.GAME_MODE_SETUP_SELECTION, 4);
            get(interfaceId).ignoreConfigClicking = true;
            inter.child(childId++, interfaceId++, 12, y + 12);

            // Divider
            addSprite(interfaceId, line);
            inter.child(childId++, interfaceId++, 4, y + 46);

            addText(interfaceId, 0, RSInterface.DEFAULT_TEXT_COLOR, false, format(mode[index]));
            inter.child(childId++, interfaceId++, 36, y + 4);
        }

        return interfaceId;
    }

    private String format(String gameModeString) {
        String[] split = gameModeString.split("\\n");
        StringBuilder formattedString = new StringBuilder();
        for (int index = 0; index < split.length; index++) {
            if (index == 0) {
                formattedString.append(MODE_HEADER_COLOUR).append(split[0]).append("\\n");
            } else {
                formattedString.append(MODE_DESC_COLOUR).append(split[index]).append("\\n");
            }
        }
        return formattedString.toString();
    }
}
