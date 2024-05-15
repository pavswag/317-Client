package com.client.graphics.interfaces.impl;

import com.client.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;
import com.client.model.Items;

public class LootViewer extends RSInterface {

    private static final int ID = 44_942;
    private static final int BUTTONS = 45_010;
    private static final int TABLE_ONE = 45_140;
    private static final int TABLE_TWO = 45_180;

    private static final boolean PRINT_IDS = false;

    private enum Button {
        MINI_MYSTERY_BOX("Mini Mystery Box", 6679),
        MINI_SUPER_MYSTERY_BOX("Mini Super Box", 6677),
        MINI_ULTRA_MYSTERY_BOX("Mini Ultra Box", 6678),
        MINI_COX_BOX("Mini Cox Box", 12585),
        MINI_TOB_BOX("Mini Tob Box", 19895),
        MINI_ARBO_BOX("Mini Arbo Box", 6680),
        MINI_DONO_BOX("Mini Dono box", 19887),
        MYSTERY_BOX("Mystery Box", 6199),
        SUPER_MYSTERY_BOX("Super M Box", 6828),
        ULTRA_MYSTERY_BOX("Ultra M Box", 13346),
        COX_BOX("Chambers M Box", 12582),
        TOB_BOX("TOB Box", 19891),
        ARBO_BOX("Arbo Box", 12579),
        FOE_MYSTERY_CHEST("Nomad Chest", 8167),
        DIVISION_F2P("Division F2P Box", 6831),
        DIVISION_P2P("Division P2P Box", 6829),
        DONO_BOX("Donator Box", 12588),
        VOTE_MYSTERY_BOX("Vote M Box", 11739),
        COSMETIC_BOX("Cosmetic Box", 19897),
        PVM_CASKET("PvM Casket", 405),
        SLAYER_MYSTERY_CHEST("Slayer Chest", 13438),
        XERIC_CHESTS("Xeric Chest", 20851),
        TOB_CHEST("TOB Chest", 8151),
        ARBOGRAVE("Arbo Swamp", 2400),
        VOTE_KEY("Vote Key", 22093),
        CRYSTAL_CHEST("Crystal Chest", 989),
        BRIMSTONE_KEY("Brimstone Key", Items.BRIMSTONE_KEY),
        HESPORI_KEY("Hespori Key", 22374),
        PORAZDIRS_KEY("Unbearable key", 4185),
        LARRANS_CHEST("Larran's Chest", Items.LARRANS_KEY),
        SERENS_KEY("Seren's Key", 6792),
        WILDERNESS_CHEST("Wildyman Chest", 13302),
        HUNNLEFS_KEY("Hunnlef's Key", 23776)
        ;

        private final String name;

        Button(String name, int itemId) {
            this.name = name;
            this.itemId = itemId;
        }

        private final int itemId;

    }

    public void load(TextDrawingArea[] tda) {
        RSInterface inter = addInterface(ID);
        int childInterfaceId = ID + 1;

        // Background and title
        addSprite(childInterfaceId++, 0, "Interfaces/Generic/IMAGE");
        addText(childInterfaceId++, "Loot Table", tda, 2, 0xff9040, true, true);

        // Begin assembling children
        int childIndex = 0;
        int children = childInterfaceId - (ID + 1);
        children += 2; // close button
        children += 1; // buttons div
        children += 2; // item containers
        setChildren(children, inter);

        // Add these after children count is made

        // Add buttons
        getButtons(tda, BUTTONS);

        // Add tables
        getTable(tda, TABLE_ONE, "Common - Uncommon");
        getTable(tda, TABLE_TWO, "Rare - Very Rare");

        // Reset child interface id for children assembly
        childInterfaceId = ID + 1;

        int x = 0;
        int y = 4;

        // Background
        inter.child(childIndex++, childInterfaceId++, 16 + x, 20 + y);

        // Title
        inter.child(childIndex++, childInterfaceId++, 264 + x, 30 + y);

        // Close buttons
        inter.child(childIndex++, 47902, 473 + x, 27 + y);
        inter.child(childIndex++, 47903, 473 + x, 27 + y);

        // Left buttons
        inter.child(childIndex++, BUTTONS, 22 + x, 54 + y);

        // Containers
        inter.child(childIndex++, TABLE_ONE, 22 + x, 20+132 + y);
        inter.child(childIndex++, TABLE_TWO, 261 + x, 20+132 + y);
    }

    public int getTable(TextDrawingArea[] tda, int id, String header) {
        boolean bottom = !header.equals("Rare");

        // Header
        RSInterface inter = addInterface(id++);
        setChildren(bottom ? 4 : 3, inter);
        int childIndex = 0;

        // Header text
        inter.child(childIndex++, id, 108, 2);
        addText(id++, header, tda, 2, Integer.MAX_VALUE, true, true);

        // Border removed increase index's to keep values same.
        childIndex++;
        id++;

        // Item container
        inter.child(childIndex++, id, 0, 20);

        // Item container define
        int scrollInterface = id;
        RSInterface scrollable = addInterface(id++);
        setChildren(1, scrollable);
        scrollable.width = 219;
        scrollable.height = 147;
        scrollable.scrollMax = scrollable.height + 1;

        scrollable.child(0, id, 4, 4);
        addItemContainerAutoScrollable(id++, 5, 36, 12, 4, true, scrollInterface);

        return id;
    }

    public int getButtons(TextDrawingArea[] tda, int startingId) {
        int interfaceId = startingId;
        final int PARENT_ID = interfaceId;

        RSInterface interfaceContainer = addInterface(interfaceId++);
        interfaceContainer.height = 82;
        interfaceContainer.width = 473;
        interfaceContainer.scrollMax = (int) (39.55 * Button.values().length);
        interfaceContainer.horizontalScroll = true;

        int buttonCount = Button.values().length;

        setChildren(buttonCount * 3, interfaceContainer);
        int childIndex = 0;

        if (PRINT_IDS) {
            System.out.println("View table button start interface id " + interfaceId);
        }

        int buttonIndex = 0;
        int xCoordinate = 0;
        for (int index = 0; index < buttonCount; index++) {
            Button button = Button.values()[index];
            int yCoordinate = buttonIndex * 28;

            // Button
            interfaceContainer.child(childIndex++, interfaceId, xCoordinate, yCoordinate);
            addConfigButton(interfaceId, PARENT_ID, 2, 1, "Interfaces/horizontalscroll/SPRITE", 117, 26, button.name, index, 4, 1354);
            RSInterface.interfaceCache[interfaceId].ignoreConfigClicking = true;
            interfaceId++;

            // Text
            interfaceContainer.child(childIndex++, interfaceId, xCoordinate + 50, yCoordinate + 7);
            addText(interfaceId++, button.name, tda, 0, 0xff9040, true, true);

            // Item sprite
            interfaceContainer.child(childIndex++, interfaceId, xCoordinate + 90, yCoordinate + 4);
            addItemView(interfaceId++, button.itemId + 1, true, true);

            buttonIndex++;
            if ((index + 1) % 3 == 0) {
                xCoordinate += 119;
                buttonIndex = 0;
            }
        }

        return interfaceId;
    }
}
