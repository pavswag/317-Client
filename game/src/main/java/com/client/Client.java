import com.client.draw.HoverManager;
                                        boolean customHover = HoverManager.shouldDraw(itemDef.id);
                                        if (customHover) {
                                            hintMenu = true;
                                            hintName = itemDef.name;
                                            hintId = itemDef.id;
                                        }
                                                        if (customHover || itemDef.interfaceOptions[l3].contains("Wield") || itemDef.interfaceOptions[l3].contains("Wear")) {
                                                        } else if (!customHover) {
                                                if (!customHover) {
                                                    hintMenu = false;
                                                }
                                                        if (customHover || itemDef.interfaceOptions[i4].contains("Wield") || itemDef.interfaceOptions[i4].contains("Wear")) {
                                                        } else if (!customHover) {
        HoverManager.reset();

        boolean customHover = HoverManager.shouldDraw(itemId);

            if (customHover && !controlIsDown) {
                HoverManager.hintId = itemId;
                HoverManager.hintName = itemName;
                HoverManager.showMenu = true;
                HoverManager.drawHintMenu();
            }
        if (customHover) {
            HoverManager.hintId = itemId;
            HoverManager.hintName = itemName;
            HoverManager.showMenu = true;
            HoverManager.drawHintMenu();
            return;
        }

        MenuEntry menuEntry = menuManager.getMenuEntry(menuActionRow - 1);
        if (itemId < 0 || itemId >= ItemStats.itemstats.length || ItemStats.itemstats[itemId] == null) {
            System.out.println("ItemStats: missing stats for id " + itemId);
            drawMissingStatsBox(itemName, mouseX, mouseY, color);
    private void drawMissingStatsBox(String itemName, int mouseX, int mouseY, int color) {
        Rasterizer2D.drawBoxOutline(mouseX, mouseY + 5, 150, 36, 0x696969);
        Rasterizer2D.drawTransparentBox(mouseX + 1, mouseY + 6, 150, 37, 0x000000, 90);

        Client.instance.newSmallFont.drawBasicString(itemName,
                mouseX + 150 / (12 + Client.instance.newSmallFont.getTextWidth(itemName)) + 30,
                mouseY + 17, color, 1);
        Client.instance.newSmallFont.drawBasicString("Stats not found", mouseX + 4, mouseY + 35,
                color, 1);
    }

            HoverManager.init();
        // Reset hover hint each frame so menus don't persist when not hovering an item
        hintMenu = false;
            } else {
                HoverManager.reset();
                hintMenu = false;
