package net.runelite.client.plugins.calculator.ui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorButton extends JButton {
    private static final Logger log = LoggerFactory.getLogger(CalculatorButton.class);

    private static final Dimension PREFERRED_SIZE = new Dimension(55, 55);

    public CalculatorButton(String text) {
        super(text);
        setPreferredSize(PREFERRED_SIZE);
        setFont(new Font("Arial", 1, 20));
    }

    public CalculatorButton(Icon icon) {
        super(icon);
        setPreferredSize(PREFERRED_SIZE);
    }
}
