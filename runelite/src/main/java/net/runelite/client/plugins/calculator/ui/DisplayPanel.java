package net.runelite.client.plugins.calculator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DisplayPanel extends JPanel {
    private final DisplayField displayField = new DisplayField();

    public DisplayField getDisplayField() {
        return this.displayField;
    }

    public DisplayPanel() {
        setLayout(new BorderLayout(4, 4));
        setBorder(new EmptyBorder(0, 1, 0, 1));
        this.displayField.setPreferredSize(new Dimension(228, 50));
        this.displayField.setMinimumSize(new Dimension(170, 50));
        this.displayField.setEditable(false);
        add(this.displayField, "Center");
    }
}
