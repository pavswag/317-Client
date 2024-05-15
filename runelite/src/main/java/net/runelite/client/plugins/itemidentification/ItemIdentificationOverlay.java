/*
 * Copyright (c) 2019, Hydrox6 <ikada@protonmail.ch>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.itemidentification;

import com.google.inject.Inject;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import static net.runelite.api.widgets.WidgetID.GUIDE_PRICE_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.KEPT_ON_DEATH_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.LOOTING_BAG_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.SEED_BOX_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.KINGDOM_GROUP_ID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;

class ItemIdentificationOverlay extends WidgetItemOverlay
{
    private final ItemIdentificationConfig config;
    private final ItemManager itemManager;

    @Inject
    ItemIdentificationOverlay(ItemIdentificationConfig config, ItemManager itemManager)
    {
        this.config = config;
        this.itemManager = itemManager;
        showOnInventory();
        showOnBank();
        showOnInterfaces(KEPT_ON_DEATH_GROUP_ID, GUIDE_PRICE_GROUP_ID, LOOTING_BAG_GROUP_ID, SEED_BOX_GROUP_ID, KINGDOM_GROUP_ID);
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        ItemIdentification iden = findItemIdentification(itemId);
        if (iden == null || !iden.type.enabled.test(config))
        {
            return;
        }

        if ((!config.showBars() && iden.type == ItemIdentification.Type.BAR) ||
                (!config.showAllotmentSeeds() && iden.type == ItemIdentification.Type.SEED_ALLOTMENT)||
                (!config.showHerbSeeds() && iden.type == ItemIdentification.Type.SEED_HERB)||
                (!config.showBerrySeeds() && iden.type == ItemIdentification.Type.SEED_BERRY)||
                (!config.showSpecialSeeds() && iden.type == ItemIdentification.Type.SEED_SPECIAL)||
                (!config.showTreeSeeds() && iden.type == ItemIdentification.Type.SEED_TREE)||
                (!config.showFruitTreeSeeds() && iden.type == ItemIdentification.Type.SEED_FRUIT_TREE)||
                (!config.showFlowerSeeds() && iden.type == ItemIdentification.Type.SEED_FLOWER)||
                (!config.showHopsSeeds() && iden.type == ItemIdentification.Type.HOPS_SEED)||
                (!config.showSacks() && iden.type == ItemIdentification.Type.SACK)||
                (!config.showHerbs() && iden.type == ItemIdentification.Type.HERB)||
                (!config.showLogs() && iden.type == ItemIdentification.Type.LOGS)||
                (!config.showPyreLogs() && iden.type == ItemIdentification.Type.LOGS_PYRE)||
                (!config.showPlanks() && iden.type == ItemIdentification.Type.PLANK)||
                (!config.showSaplings() && iden.type == ItemIdentification.Type.SAPLING)||
                (!config.showComposts() && iden.type == ItemIdentification.Type.COMPOST)||
                (!config.showOres() && iden.type == ItemIdentification.Type.ORE)||
                (!config.showGems() && iden.type == ItemIdentification.Type.GEM)||
                (!config.showPotions() && iden.type == ItemIdentification.Type.POTION)||
                (!config.showImplingJars() && iden.type == ItemIdentification.Type.IMPLING_JAR)||
                (!config.showTablets() && iden.type == ItemIdentification.Type.TABLET)||
                (!config.showTeleportScrolls() && iden.type == ItemIdentification.Type.SCROLL)||
                (!config.showJewellery() && iden.type == ItemIdentification.Type.JEWELLERY)||
                (!config.showEnchantedJewellery() && iden.type == ItemIdentification.Type.JEWELLERY_ENCHANTED)) {
            return;
        }


        graphics.setFont(FontManager.getRunescapeSmallFont());
        renderText(graphics, widgetItem.getCanvasBounds(), iden);
    }

    private void renderText(Graphics2D graphics, Rectangle bounds, ItemIdentification iden)
    {
        final TextComponent textComponent = new TextComponent();
        textComponent.setPosition(new Point(bounds.x - 1, bounds.y + bounds.height - 1));
        textComponent.setColor(config.textColor());
        switch (config.identificationType())
        {
            case SHORT:
                textComponent.setText(iden.shortName);
                break;
            case MEDIUM:
                textComponent.setText(iden.medName);
                break;
        }
        textComponent.render(graphics);
    }

    private ItemIdentification findItemIdentification(final int itemID)
    {
//        final int realItemId = itemManager.canonicalize(itemID);
        return ItemIdentification.get(itemID);
    }
}