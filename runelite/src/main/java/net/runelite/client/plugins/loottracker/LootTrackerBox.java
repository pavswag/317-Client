/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.loottracker;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.QuantityFormatter;
import net.runelite.client.util.Text;
import net.runelite.http.api.loottracker.LootRecordType;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

class LootTrackerBox extends JPanel {
    private static final int ITEMS_PER_ROW = 5;
    private static final int TITLE_PADDING = 5;

    private final JPanel itemContainer = new JPanel();
    private final JLabel priceLabel = new JLabel();
    private final JLabel subTitleLabel = new JLabel();
    private final JPanel logTitle = new JPanel();
    private final ItemManager itemManager;
    @Getter(AccessLevel.PACKAGE)
    private final String id;
    private final LootRecordType lootRecordType;
    private final LootTrackerPriceType priceType;
    private final boolean showPriceType;

    private int kills;
    @Getter
    private final List<LootTrackerItem> items = new ArrayList<>();

    private long totalPrice;
    private final boolean hideIgnoredItems;
    private final BiConsumer<String, Boolean> onItemToggle;

    LootTrackerBox(
            final ItemManager itemManager,
            final String id,
            final LootRecordType lootRecordType,
            @Nullable final String subtitle,
            final boolean hideIgnoredItems,
            final LootTrackerPriceType priceType,
            final boolean showPriceType,
            final BiConsumer<String, Boolean> onItemToggle,
            final BiConsumer<String, Boolean> onEventToggle,
            final boolean eventIgnored) {
        this.id = id;
        this.lootRecordType = lootRecordType;
        this.itemManager = itemManager;
        this.onItemToggle = onItemToggle;
        this.hideIgnoredItems = hideIgnoredItems;
        this.priceType = priceType;
        this.showPriceType = showPriceType;

        setLayout(new BorderLayout(0, 1));
        setBorder(new EmptyBorder(5, 0, 0, 0));

        logTitle.setLayout(new BoxLayout(logTitle, BoxLayout.X_AXIS));
        logTitle.setBorder(new EmptyBorder(7, 7, 7, 7));
        logTitle.setBackground(eventIgnored ? ColorScheme.DARKER_GRAY_HOVER_COLOR : ColorScheme.DARKER_GRAY_COLOR.darker());

        JLabel titleLabel = new JLabel();
        // Define an array of color codes and corresponding color values
        String[][] colorCodesAndValues = {
                {"@pur@", "#A10081"}, {"@red@", "#FF0000"}, {"@gre@", "#65280"}, {"@blu@", "#0000FF"},
                {"@bl2@", "#0F0085"}, {"@bl3@", "#00AFFF"}, {"@yel@", "#FFFF00"}, {"@cya@", "#00FFFF"},
                {"@mag@", "#FF00FF"}, {"@whi@", "#FFFFFF"}, {"@lre@", "#FF9040"}, {"@dre@", "#800000"},
                {"@bla@", "#000000"}, {"@or0@", "#A67711"}, {"@or1@", "#FFB000"}, {"@or2@", "#FF7000"},
                {"@or3@", "#FF3000"}, {"@or4@", "#FF9933"}, {"@gr0@", "#148200"}, {"@gr1@", "#C0FF00"},
                {"@gr2@", "#80FF00"}, {"@gr3@", "#40FF00"}, {"@OR0@", "#A67711"}, {"@PUR@", "#A10081"},
                {"@RED@", "#FF0000"}, {"@GRE@", "#65280"}, {"@BLU@", "#0000FF"}, {"@YEL@", "#FFFF00"},
                {"@CYA@", "#00FFFF"}, {"@MAG@", "#FF00FF"}, {"@WHI@", "#FFFFFF"}, {"@LRE@", "#FF9040"},
                {"@DRE@", "#800000"}, {"@BLA@", "#000000"}, {"@OR1@", "#FFB000"}, {"@OR2@", "#FF7000"},
                {"@OR3@", "#FF3000"}, {"@GR1@", "#C0FF00"}, {"@GR2@", "#80FF00"}, {"@GR3@", "#40FF00"}
        };

        boolean colorFound = false;
        String name = id;
        // Iterate over the color codes and values
        for (String[] colorCodeAndValue : colorCodesAndValues) {
            String colorCode = colorCodeAndValue[0];
            String colorValue = colorCodeAndValue[1];
            if (id.contains(colorCode)) {
                // Replace color code with span style
                titleLabel.setForeground(Color.decode(colorValue));
                name = id.replace(colorCode, ""); // Remove color code from id
                colorFound = true;
                break;
            }
        }

        // If no color code is found, set the default color to white
        if (!colorFound) {
            titleLabel.setForeground(Color.WHITE);
        }
        titleLabel.setText(Text.removeTags(name));
        titleLabel.setFont(FontManager.getRunescapeSmallFont());
        // Set a size to make BoxLayout truncate the name
        titleLabel.setMinimumSize(new Dimension(1, titleLabel.getPreferredSize().height));
        logTitle.add(titleLabel);

        subTitleLabel.setFont(FontManager.getRunescapeSmallFont());
        subTitleLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

        if (!Strings.isNullOrEmpty(subtitle)) {
            subTitleLabel.setText(subtitle);
        }

        logTitle.add(Box.createRigidArea(new Dimension(TITLE_PADDING, 0)));
        logTitle.add(subTitleLabel);
        logTitle.add(Box.createHorizontalGlue());
        logTitle.add(Box.createRigidArea(new Dimension(TITLE_PADDING, 0)));

        priceLabel.setFont(FontManager.getRunescapeSmallFont());
        priceLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        logTitle.add(priceLabel);

        add(logTitle, BorderLayout.NORTH);
        add(itemContainer, BorderLayout.CENTER);

        // Create popup menu for ignoring the loot event
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setComponentPopupMenu(popupMenu);

        final JMenuItem toggle = new JMenuItem(eventIgnored ? "Include loot" : "Hide loot");
        toggle.addActionListener(e -> onEventToggle.accept(id, !eventIgnored));
        popupMenu.add(toggle);
    }

    /**
     * Returns total amount of kills
     *
     * @return total amount of kills
     */
    private int getTotalKills() {
        return kills;
    }

    /**
     * Checks if this box matches specified record
     *
     * @param record loot record
     * @return true if match is made
     */
    boolean matches(final LootTrackerRecord record) {
        return record.getTitle().equals(id) && record.getType() == lootRecordType;
    }

    /**
     * Checks if this box matches specified id and type
     *
     * @param id   other record id
     * @param type other record type
     * @return true if match is made
     */
    boolean matches(final String id, final LootRecordType type) {
        if (id == null) {
            return true;
        }

        return this.id.equals(id) && lootRecordType == type;
    }

    /**
     * Adds an record's data into a loot box.
     */
    void addKill(final LootTrackerRecord record) {
        if (!matches(record)) {
            throw new IllegalArgumentException(record.toString());
        }

        kills += record.getKills();

        outer:
        for (LootTrackerItem item : record.getItems()) {
            final int mappedItemId = LootTrackerMapping.map(item.getId(), item.getName());
            // Combine it into an existing item if one already exists
            for (int idx = 0; idx < items.size(); ++idx) {
                LootTrackerItem i = items.get(idx);
                if (mappedItemId == i.getId()) {
                    items.set(idx, new LootTrackerItem(i.getId(), i.getName(), i.getQuantity() + item.getQuantity(), i.getGePrice(), i.getHaPrice(), i.isIgnored()));
                    continue outer;
                }
            }

            final LootTrackerItem mappedItem = mappedItemId == item.getId()
                    ? item // reuse existing item
                    : new LootTrackerItem(mappedItemId, item.getName(), item.getQuantity(), item.getGePrice(), item.getHaPrice(), item.isIgnored());
            items.add(mappedItem);
        }
    }

    void rebuild() {
        buildItems();

        String priceTypeString = " ";
        if (showPriceType) {
            priceTypeString = priceType == LootTrackerPriceType.HIGH_ALCHEMY ? "HA: " : "GE: ";
        }

        if (priceType == LootTrackerPriceType.HIGH_ALCHEMY) {
            priceLabel.setText(priceTypeString + QuantityFormatter.quantityToStackSize(totalPrice) + " gp");
            priceLabel.setToolTipText(QuantityFormatter.formatNumber(totalPrice) + " gp");
        } else {
            priceLabel.setText(priceTypeString + QuantityFormatter.quantityToStackSize(totalPrice) + " nomad");
            priceLabel.setToolTipText(QuantityFormatter.formatNumber(totalPrice) + " nomad");
        }

        final long kills = getTotalKills();
        if (kills > 1) {
            subTitleLabel.setText("x " + kills);
            subTitleLabel.setToolTipText(QuantityFormatter.formatNumber(totalPrice / kills) + " gp (average)");
        }

        validate();
        repaint();
    }

    void collapse() {
        if (!isCollapsed()) {
            itemContainer.setVisible(false);
            applyDimmer(false, logTitle);
        }
    }

    void expand() {
        if (isCollapsed()) {
            itemContainer.setVisible(true);
            applyDimmer(true, logTitle);
        }
    }

    boolean isCollapsed() {
        return !itemContainer.isVisible();
    }

    private void applyDimmer(boolean brighten, JPanel panel) {
        for (Component component : panel.getComponents()) {
            Color color = component.getForeground();

            component.setForeground(brighten ? color.brighter() : color.darker());
        }
    }

    /**
     * This method creates stacked items from the item list, calculates total price and then
     * displays all the items in the UI.
     */
    private void buildItems() {
        totalPrice = 0;

        List<LootTrackerItem> items = this.items;
        if (hideIgnoredItems) {
            items = items.stream().filter(item -> !item.isIgnored()).collect(Collectors.toList());
        }

        boolean isHidden = items.isEmpty();
        setVisible(!isHidden);

        if (isHidden) {
            return;
        }

        ToLongFunction<LootTrackerItem> getPrice = priceType == LootTrackerPriceType.HIGH_ALCHEMY
                ? LootTrackerItem::getTotalHaPrice
                : LootTrackerItem::getTotalGePrice;

        totalPrice = items.stream()
                .mapToLong(getPrice)
                .sum();

        items.sort(Comparator.comparingLong(getPrice).reversed());

        // Calculates how many rows need to be display to fit all items
        final int rowSize = ((items.size() % ITEMS_PER_ROW == 0) ? 0 : 1) + items.size() / ITEMS_PER_ROW;

        itemContainer.removeAll();
        itemContainer.setLayout(new GridLayout(rowSize, ITEMS_PER_ROW, 1, 1));

        for (int i = 0; i < rowSize * ITEMS_PER_ROW; i++) {
            final JPanel slotContainer = new JPanel();
            slotContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

            if (i < items.size()) {
                final LootTrackerItem item = items.get(i);
                final JLabel imageLabel = new JLabel();
                imageLabel.setToolTipText(buildToolTip(item));
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                AsyncBufferedImage itemImage = itemManager.getImage(item.getId(), item.getQuantity(), item.getQuantity() > 1);

                if (item.isIgnored()) {
                    Runnable addTransparency = () ->
                    {
                        BufferedImage transparentImage = ImageUtil.alphaOffset(itemImage, .3f);
                        imageLabel.setIcon(new ImageIcon(transparentImage));
                    };
                    itemImage.onLoaded(addTransparency);
                    addTransparency.run();
                } else {
                    itemImage.addTo(imageLabel);
                }

                slotContainer.add(imageLabel);

                // Create popup menu
                final JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
                slotContainer.setComponentPopupMenu(popupMenu);

                final JMenuItem toggle = new JMenuItem("Toggle item");
                toggle.addActionListener(e ->
                {
                    item.setIgnored(!item.isIgnored());
                    onItemToggle.accept(item.getName(), item.isIgnored());
                });

                popupMenu.add(toggle);
            }

            itemContainer.add(slotContainer);
        }

        itemContainer.repaint();
    }

    private static String buildToolTip(LootTrackerItem item) {
        final String name = item.getName();
        final int quantity = item.getQuantity();
        final long gePrice = item.getTotalGePrice();
        final long haPrice = item.getTotalHaPrice();
        final String ignoredLabel = item.isIgnored() ? " - Ignored" : "";
        final StringBuilder sb = new StringBuilder("<html>");
        sb.append(name).append(" x ").append(QuantityFormatter.formatNumber(quantity)).append(ignoredLabel);
        if (item.getId() == ItemID.COINS_995) {
            sb.append("</html>");
            return sb.toString();
        }

        sb.append("<br>nomad: ").append(QuantityFormatter.quantityToStackSize(gePrice));
        if (quantity > 1) {
            sb.append(" (").append(QuantityFormatter.quantityToStackSize(item.getGePrice())).append(" ea)");
        }

        if (item.getId() == ItemID.PLATINUM_TOKEN) {
            sb.append("</html>");
            return sb.toString();
        }

        sb.append("<br>HA: ").append(QuantityFormatter.quantityToStackSize(haPrice));
        if (quantity > 1) {
            sb.append(" (").append(QuantityFormatter.quantityToStackSize(item.getHaPrice())).append(" ea)");
        }
        sb.append("</html>");
        return sb.toString();
    }

    private static String replace(String text, String replace, String replaceWith) {
        if (text.contains(replace)) {
            return text.replaceAll(replace, replaceWith);
        }
        return text;
    }

    public static String handleOldSyntax(String text) {
        if (text.contains("@")) {
            text = text.replace("@pur@", "<span style='color: #A10081;'>");
            text = text.replace("@red@", "<span style='color: #FF0000;'>");
            text = text.replace("@gre@", "<span style='color: #65280;'>");
            text = text.replace("@blu@", "<span style='color: #0000FF;'>");
            text = text.replace("@bl2@", "<span style='color: #0F0085;'>");
            text = text.replace("@bl3@", "<span style='color: #00AFFF;'>");
            text = text.replace("@yel@", "<span style='color: #FFFF00;'>");
            text = text.replace("@cya@", "<span style='color: #00FFFF;'>");
            text = text.replace("@mag@", "<span style='color: #FF00FF;'>");
            text = text.replace("@whi@", "<span style='color: #FFFFFF;'>");
            text = text.replace("@lre@", "<span style='color: #FF9040;'>");
            text = text.replace("@dre@", "<span style='color: #800000;'>");
            text = text.replace("@bla@", "<span style='color: #000000;'>");
            text = text.replace("@or0@", "<span style='color: #A67711;'>");
            text = text.replace("@or1@", "<span style='color: #FFB000;'>");
            text = text.replace("@or2@", "<span style='color: #FF7000;'>");
            text = text.replace("@or3@", "<span style='color: #FF3000;'>");
            text = text.replace("@or4@", "<span style='color: #FF9933;'>");
            text = text.replace("@gr0@", "<span style='color: #148200;'>");
            text = text.replace("@gr1@", "<span style='color: #C0FF00;'>");
            text = text.replace("@gr2@", "<span style='color: #80FF00;'>");
            text = text.replace("@gr3@", "<span style='color: #40FF00;'>");
            text = text.replace("@OR0@", "<span style='color: #A67711;'>");
            text = text.replace("@PUR@", "<span style='color: #A10081;'>");
            text = text.replace("@RED@", "<span style='color: #FF0000;'>");
            text = text.replace("@GRE@", "<span style='color: #65280;'>");
            text = text.replace("@BLU@", "<span style='color: #0000FF;'>");
            text = text.replace("@YEL@", "<span style='color: #FFFF00;'>");
            text = text.replace("@CYA@", "<span style='color: #00FFFF;'>");
            text = text.replace("@MAG@", "<span style='color: #FF00FF;'>");
            text = text.replace("@WHI@", "<span style='color: #FFFFFF;'>");
            text = text.replace("@LRE@", "<span style='color: #FF9040;'>");
            text = text.replace("@DRE@", "<span style='color: #800000;'>");
            text = text.replace("@BLA@", "<span style='color: #000000;'>");
            text = text.replace("@OR1@", "<span style='color: #FFB000;'>");
            text = text.replace("@OR2@", "<span style='color: #FF7000;'>");
            text = text.replace("@OR3@", "<span style='color: #FF3000;'>");
            text = text.replace("@GR1@", "<span style='color: #C0FF00;'>");
            text = text.replace("@GR2@", "<span style='color: #80FF00;'>");
            text = text.replace("@GR3@", "<span style='color: #40FF00;'>");
            /*if (text.contains("@cr")) {
                text = replace(text, "@cr0@", "<img=39>");//mod
                text = replace(text, "@cr1@", "<img=37>");//admin
                text = replace(text, "@cr2@", "<img=36>");//owner
                text = replace(text, "@cr3@", "<img=37>");//
                text = replace(text, "@cr4@", "<img=48>");//$20
                text = replace(text, "@cr5@", "<img=5>");//
                text = replace(text, "@cr6@", "<img=47>");//$50
                text = replace(text, "@cr7@", "<img=42>");//$1000
                text = replace(text, "@cr8@", "<img=46>");//$1000
                text = replace(text, "@cr9@", "<img=9>");//HC Iron
                text = replace(text, "@cr10@", "<img=38>");//Support
                text = replace(text, "@cr11@", "<img=11>");//hitbox
                text = replace(text, "@cr12@", "<img=12>");//im
                text = replace(text, "@cr13@", "<img=13>");//uim
                text = replace(text, "@cr14@", "<img=14>");//yt
                text = replace(text, "@cr15@", "<img=15>");//
                text = replace(text, "@cr16@", "<img=45>");//$250
                text = replace(text, "@cr17@", "<img=44>");//$500
                text = replace(text, "@cr18@", "<img=18>");//
                text = replace(text, "@cr19@", "<img=19>");
                text = replace(text, "@cr20@", "<img=20>");//Notification
                text = replace(text, "@cr21@", "<img=21>");
                text = replace(text, "@cr22@", "<img=22>");//OSRS
                text = replace(text, "@cr23@", "<img=23>");
                text = replace(text, "@cr24@", "<img=24>");//Rogue
                text = replace(text, "@cr25@", "<img=25>");//Rogue ironman
                text = replace(text, "@cr26@", "<img=26>");//Rogue HC Ironman
                text = replace(text, "@cr27@", "<img=27>");//GIM
                text = replace(text, "@cr28@", "<img=28>");//event
                text = replace(text, "@cr29@", "<img=28>");
                text = replace(text, "@cr30@", "<img=30>");//osrs iron
                text = replace(text, "@cr31@", "<img=43>");
                text = replace(text, "@cr32@", "<img=41>");//$1500
                text = replace(text, "@cr33@", "<img=40>");//$2000
                text = replace(text, "@cr34@", "<img=34>");
                text = replace(text, "@cr35@", "<img=35>");
                text = replace(text, "@cr36@", "<img=36>");
                text = replace(text, "@cr37@", "<img=37>");
                text = replace(text, "@cr38@", "<img=38>");
                text = replace(text, "@cr39@", "<img=39>");
                text = replace(text, "@cr40@", "<img=40>");
                text = replace(text, "@cr41@", "<img=41>");
                text = replace(text, "@cr42@", "<img=42>");
                text = replace(text, "@cr43@", "<img=43>");
                text = replace(text, "@cr44@", "<img=44>");
                text = replace(text, "@cr45@", "<img=45>");
                text = replace(text, "@cr46@", "<img=46>");
                text = replace(text, "@cr47@", "<img=47>");
                text = replace(text, "@cr48@", "<img=92>");
                text = replace(text, "@cr49@", "<img=49>");
                text = replace(text, "@cr50@", "<img=92>");
                text = replace(text, "@cr51@", "<img=92>");
                text = replace(text, "@cr52@", "<img=92>");
                text = replace(text, "@cr53@", "<img=92>");
                text = replace(text, "@cr54@", "<img=92>");
                text = replace(text, "@cr55@", "<img=92>");
                text = replace(text, "@cr56@", "<img=92>");
                text = replace(text, "@cr57@", "<img=92>");
                text = replace(text, "@cr58@", "<img=92>");
                text = replace(text, "@cr59@", "<img=92>");
                text = replace(text, "@cr60@", "<img=92>");
                text = replace(text, "@cr61@", "<img=92>");
                text = replace(text, "@cr62@", "<img=92>");
                text = replace(text, "@cr63@", "<img=92>");
                text = replace(text, "@cr64@", "<img=92>");
                text = replace(text, "@cr65@", "<img=92>");
                text = replace(text, "@cr66@", "<img=92>");
                text = replace(text, "@cr67@", "<img=92>");
                text = replace(text, "@cr68@", "<img=92>");
                text = replace(text, "@cr69@", "<img=92>");
                text = replace(text, "@cr70@", "<img=92>");
                text = replace(text, "@cr71@", "<img=92>");
                text = replace(text, "@cr72@", "<img=92>");
                text = replace(text, "@cr73@", "<img=92>");
                text = replace(text, "@cr74@", "<img=92>");
                text = replace(text, "@cr75@", "<img=92>");
                text = replace(text, "@cr76@", "<img=92>");
                text = replace(text, "@cr77@", "<img=92>");
                text = replace(text, "@cr78@", "<img=92>");
                text = replace(text, "@cr79@", "<img=92>");
                text = replace(text, "@cr80@", "<img=92>");
                text = replace(text, "@cr81@", "<img=92>");
                text = replace(text, "@cr82@", "<img=92>");
                text = replace(text, "@cr83@", "<img=92>");
                text = replace(text, "@cr84@", "<img=92>");
                text = replace(text, "@cr85@", "<img=92>");
                text = replace(text, "@cr86@", "<img=92>");
                text = replace(text, "@cr87@", "<img=92>");
                text = replace(text, "@cr88@", "<img=92>");
                text = replace(text, "@cr89@", "<img=92>");
                text = replace(text, "@cr90@", "<img=92>");
                text = replace(text, "@cr91@", "<img=92>");
                text = replace(text, "@cr92@", "<img=92>");
                text = replace(text, "@cr93@", "<img=93>");
            }*/
        }
        return text;
    }
}
