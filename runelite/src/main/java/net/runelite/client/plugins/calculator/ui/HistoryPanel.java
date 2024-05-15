package net.runelite.client.plugins.calculator.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class HistoryPanel extends JPanel {
    private final List<HistoryItemPanel> itemPanels = new ArrayList<>();

    private final GridBagConstraints c;

    public HistoryPanel() {
        setLayout(new GridBagLayout());
        this.c = new GridBagConstraints();
        this.c.fill = 2;
        this.c.gridx = 0;
        this.c.gridy = 0;
        this.c.weightx = 1.0D;
        this.c.weighty = 0.0D;
        this.c.insets = new Insets(0, 0, 7, 0);
    }

    protected void addHistoryItem(String line1, String line2) {
        removeAll();
        HistoryItemPanel itemPanel = new HistoryItemPanel(line1, line2);
        this.itemPanels.add(0, itemPanel);
        this.c.gridy = 0;
        this.itemPanels.forEach(panel -> {
            add(panel, this.c, 0);
            this.c.gridy++;
        });
        repaint();
        revalidate();
    }

    protected void clearHistory() {
        removeAll();
        this.itemPanels.clear();
        repaint();
        revalidate();
    }
}
