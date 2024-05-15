package net.runelite.client.plugins.calculator.ui;

import java.awt.BorderLayout;
import net.runelite.client.ui.PluginPanel;

public class CalculatorPluginPanel extends PluginPanel {
    private final DisplayPanel displayPanel = new DisplayPanel();

    private final HistoryPanel historyPanel = new HistoryPanel();

    public HistoryPanel getHistoryPanel() {
        return this.historyPanel;
    }

    public CalculatorPluginPanel() {
        CalculatorPanel calculatorPanel = new CalculatorPanel(this);
        setLayout(new BorderLayout(5, 5));
        add(this.displayPanel, "North");
        add(calculatorPanel, "Center");
        add(this.historyPanel, "South");
    }

    protected DisplayField getDisplayField() {
        return this.displayPanel.getDisplayField();
    }
}
