package com.client.graphics.interfaces.builder.impl;

import com.client.Configuration;
import com.client.Sprite;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.builder.InterfaceBuilder;

public class GroupIronmanBank extends InterfaceBuilder {

    public GroupIronmanBank() {
        super(48670);
    }

    @Override
    public void build() {
        addSprite(nextInterface(), new Sprite("banktab/07/bank 0"));
        child(12, 2);

        addText(nextInterface(), "Group Ironman Bank", 2, 0xE68A00, true, true);
        child(250, 13);

        addText(nextInterface(), "0", 0, 0xE68A00, true, true);
        child(472, 299);

        addText(nextInterface(), "350", 0, 0xE68A00, true, true);
        child(472, 313);

        // Inventory and container for inventory
        addInventoryContainer(nextInterface(), 9, 130,14, 2, true, "Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw All", "Withdraw X", "Withdraw All but one");
        RSInterface container = addInterface(nextInterface());
        container.width = 430;
        container.height = 200;
        container.scrollMax = 6000;
        setChildren(1, container);

        RSInterface itemContainer = RSInterface.get(getCurrentInterfaceId() - 2);
        itemContainer.contentType = 206;
        container.child(0, itemContainer.id, 12, 0);
        child(48, 84);

        child(5384, 476, 12); // Close
        child(5380, 476, 12);

//        child(18929, 70, 296); // Swap items
//        child(18930, 70, 296);

//        child(18933, 170, 296); // Noting
//        child(18934, 170, 296);

        child(58002, 20, 85); // Rearrange mode
//        child(58003, 20, 296);

        child(58010, 20, 119); // Noting
//        child(58011, 120, 296);

        child(58018, 20, 153); // Deposit backpack
//        child(58019, 423, 292);

        child(58026, 20, 187); // Deposit worn items

//        child(59018, 22, 276); // Rearrange mode:
//        child(59019, 140, 276); // Withdraw as:

//        child(59114, 45, 299); // Swap
//        child(59115, 95, 299); // Insert
//        child(59116, 20, 260); // Item
//        child(59117, 40, 14); // Note

        child(58065, 75, 290);
        int subtract_x = 210;
        int quantity_y = 305;
        child(58066, 242 - subtract_x, quantity_y);
        child(58067, 242 - subtract_x, quantity_y+3);
        child(58068, 267 - subtract_x+2, quantity_y);
        child(58069, 267 - subtract_x+2, quantity_y+3);
        child(58070, 292 - subtract_x+4, quantity_y);
        child(58071, 292 - subtract_x+4, quantity_y+3);
        child(58072, 317 - subtract_x+6, quantity_y);
        child(58073, 317 - subtract_x+6, quantity_y+3);
        child(58074, 342 - subtract_x+8, quantity_y);
        child(58075, 342 - subtract_x+8, quantity_y+3);
    }
}
