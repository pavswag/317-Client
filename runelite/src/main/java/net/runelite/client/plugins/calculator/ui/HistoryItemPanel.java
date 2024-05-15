package net.runelite.client.plugins.calculator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import net.runelite.client.plugins.calculator.CalculatorPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

public class HistoryItemPanel extends JPanel {
    private static final ImageIcon CLIPBOARD_ICON;

    static {
        BufferedImage clipboardIcon = ImageUtil.resizeImage(ImageUtil.getResourceStreamFromClass(CalculatorPlugin.class, "clipboard_icon.png"), 20, 20);
        CLIPBOARD_ICON = new ImageIcon(clipboardIcon);
    }

    public HistoryItemPanel(String line1, String line2) {
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new MatteBorder(0, 5, 0, 0, ColorScheme.BRAND_ORANGE));
        JPanel text = new JPanel(new BorderLayout());
        text.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        JLabel lineOneLabel = new JLabel(line1);
        lineOneLabel.setHorizontalAlignment(0);
        JLabel lineTwoLabel = new JLabel(line2);
        lineTwoLabel.setHorizontalAlignment(0);
        text.add(lineOneLabel, "North");
        text.add(lineTwoLabel, "South");
        add(text, "Center");
        JButton copy = new JButton(CLIPBOARD_ICON);
        copy.setPreferredSize(new Dimension(40, 40));
        copy.setToolTipText("Copy result to clipboard");
        copy.addActionListener(e -> {
            StringSelection selection = new StringSelection(line2);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        });
        add(copy, "East");
    }
}
