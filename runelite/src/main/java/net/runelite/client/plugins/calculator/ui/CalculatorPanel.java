package net.runelite.client.plugins.calculator.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import net.runelite.client.plugins.calculator.CalculatorPlugin;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

public class CalculatorPanel extends JPanel {
    private static final ImageIcon PLUS_MINUS_ICON;

    private static final Insets INSETS_LEFT_BORDER = new Insets(1, 0, 1, 1);

    private static final Insets INSETS_RIGHT_BORDER = new Insets(1, 1, 1, 0);

    private static final Insets INSETS = new Insets(1, 1, 1, 1);

    private final CalculatorPluginPanel panel;

    private final DisplayField displayField;

    private final GridBagConstraints c;

    static {
        BufferedImage plusMinusIcon = ImageUtil.resizeImage(ImageUtil.getResourceStreamFromClass(CalculatorPlugin.class, "plus_minus_icon.png"), 25, 25);
        PLUS_MINUS_ICON = new ImageIcon(plusMinusIcon);
    }

    protected CalculatorPanel(CalculatorPluginPanel panel) {
        this.panel = panel;
        this.displayField = panel.getDisplayField();
        setLayout(new GridBagLayout());
        this.c = new GridBagConstraints();
        this.c.fill = 2;
        this.c.gridx = 0;
        this.c.gridy = 0;
        CalculatorButton plusMinus = new CalculatorButton(PLUS_MINUS_ICON);
        addButton("+");
        addButton("7");
        addButton("8");
        addButton("9");
        addButton("-");
        addButton("4");
        addButton("5");
        addButton("6");
        addButton("*");
        addButton("1");
        addButton("2");
        addButton("3");
        addButton("/");
        addButton("0");
        addComp(plusMinus);
        addButton("=");
        addButton("C");
        this.c.gridwidth = 3;
        addButton("Clear History");
        plusMinus.addActionListener(e -> {
            if (this.displayField.isFinished()) {
                this.displayField.reset();
                if (this.displayField.getPreviousResult() != null) {
                    this.displayField.setNum1(this.displayField.getPreviousResult());
                } else {
                    this.displayField.setNum1(Integer.valueOf(0));
                }
                this.displayField.setNum1(Integer.valueOf(this.displayField.getNum1().intValue() * -1));
                this.displayField.setFinished(false);
            } else if (this.displayField.getCalculatorAction() == null) {
                this.displayField.setNum1(Integer.valueOf(this.displayField.getNum1().intValue() * -1));
            } else {
                Integer num2 = this.displayField.getNum2();
                if (num2 == null)
                    num2 = Integer.valueOf(0);
                this.displayField.setNum2(Integer.valueOf(num2.intValue() * -1));
            }
            this.displayField.update();
        });
    }

    private void addButton(String key) {
        CalculatorButton btn = new CalculatorButton(key);
        btn.addActionListener(e -> {
            String text = btn.getText();
            if (text.equals("=")) {
                this.displayField.calculateResult();
                if (this.displayField.getResult() == null)
                    return;
                this.panel.getHistoryPanel().addHistoryItem(this.displayField.getText() + " =", this.displayField.getResult().toString());
            } else {
                if (text.equals("C")) {
                    this.displayField.clear();
                    return;
                }
                if (text.equals("Clear History")) {
                    this.panel.getHistoryPanel().clearHistory();
                    return;
                }
                if (StringUtils.isNumeric(text)) {
                    int num = Integer.parseInt(text);
                    if (this.displayField.isFinished()) {
                        this.displayField.reset();
                        this.displayField.setNum1(Integer.valueOf(num));
                        this.displayField.setFinished(false);
                    } else if (this.displayField.getCalculatorAction() == null) {
                        Integer num1 = this.displayField.getNum1();
                        if (num1.intValue() == 0) {
                            if (this.displayField.num1IsNegativeZero())
                                num *= -1;
                            this.displayField.setNum1(Integer.valueOf(num));
                        } else if (num1.intValue() < 0) {
                            this.displayField.setNum1(Integer.valueOf(num1.intValue() * 10 - num));
                        } else {
                            this.displayField.setNum1(Integer.valueOf(num1.intValue() * 10 + num));
                        }
                    } else {
                        Integer num2 = this.displayField.getNum2();
                        if (num2 == null || num2.intValue() == 0) {
                            if (this.displayField.num2IsNegativeZero())
                                num *= -1;
                            this.displayField.setNum2(Integer.valueOf(num));
                        } else if (num2.intValue() < 0) {
                            this.displayField.setNum2(Integer.valueOf(num2.intValue() * 10 - num));
                        } else {
                            this.displayField.setNum2(Integer.valueOf(num2.intValue() * 10 + num));
                        }
                    }
                } else {
                    if (this.displayField.isFinished() && this.displayField.getPreviousResult() != null) {
                        this.displayField.reset();
                        this.displayField.setNum1(this.displayField.getPreviousResult());
                        this.displayField.setFinished(false);
                        this.displayField.update();
                    }
                    if (this.displayField.getNum1() != null)
                        switch (text) {
                            case "+":
                                this.displayField.setCalculatorAction(DisplayField.Action.ADDITION);
                                break;
                            case "-":
                                this.displayField.setCalculatorAction(DisplayField.Action.SUBTRACTION);
                                break;
                            case "*":
                                this.displayField.setCalculatorAction(DisplayField.Action.MULTIPLICATION);
                                break;
                            case "/":
                                this.displayField.setCalculatorAction(DisplayField.Action.DIVISION);
                                break;
                        }
                    this.displayField.setNum2(null);
                }
            }
            this.displayField.update();
        });
        addComp(btn);
    }

    private void addComp(Component component) {
        switch (this.c.gridx) {
            case 0:
                this.c.insets = INSETS_LEFT_BORDER;
                break;
            case 3:
                this.c.insets = INSETS_RIGHT_BORDER;
                break;
            default:
                this.c.insets = INSETS;
                break;
        }
        if (this.c.gridwidth == 3)
            this.c.insets = INSETS_RIGHT_BORDER;
        add(component, this.c);
        this.c.gridx = ++this.c.gridx % 4;
        this.c.gridy = (this.c.gridx == 0) ? ++this.c.gridy : this.c.gridy;
    }
}
