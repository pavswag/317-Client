package net.runelite.client.plugins.calculator.ui;

import javax.swing.JTextField;

public class DisplayField extends JTextField {
    private Integer num1;

    private Integer num2;

    private Action calculatorAction;

    private Integer result;

    private Integer previousResult;

    private String text;

    private boolean finished;

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DisplayField))
            return false;
        DisplayField other = (DisplayField)o;
        if (!other.canEqual(this))
            return false;
        if (!super.equals(o))
            return false;
        if (isFinished() != other.isFinished())
            return false;
        Object this$num1 = getNum1(), other$num1 = other.getNum1();
        if ((this$num1 == null) ? (other$num1 != null) : !this$num1.equals(other$num1))
            return false;
        Object this$num2 = getNum2(), other$num2 = other.getNum2();
        if ((this$num2 == null) ? (other$num2 != null) : !this$num2.equals(other$num2))
            return false;
        Object this$result = getResult(), other$result = other.getResult();
        if ((this$result == null) ? (other$result != null) : !this$result.equals(other$result))
            return false;
        Object this$previousResult = getPreviousResult(), other$previousResult = other.getPreviousResult();
        if ((this$previousResult == null) ? (other$previousResult != null) : !this$previousResult.equals(other$previousResult))
            return false;
        Object this$calculatorAction = getCalculatorAction(), other$calculatorAction = other.getCalculatorAction();
        if ((this$calculatorAction == null) ? (other$calculatorAction != null) : !this$calculatorAction.equals(other$calculatorAction))
            return false;
        Object this$text = getText(), other$text = other.getText();
        return !((this$text == null) ? (other$text != null) : !this$text.equals(other$text));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DisplayField;
    }

    public int hashCode() {
        int PRIME = 59;
        result = super.hashCode();
        result = result * 59 + (isFinished() ? 79 : 97);
        Object $num1 = getNum1();
        result = result * 59 + (($num1 == null) ? 43 : $num1.hashCode());
        Object $num2 = getNum2();
        result = result * 59 + (($num2 == null) ? 43 : $num2.hashCode());
        Object $result = getResult();
        result = result * 59 + (($result == null) ? 43 : $result.hashCode());
        Object $previousResult = getPreviousResult();
        result = result * 59 + (($previousResult == null) ? 43 : $previousResult.hashCode());
        Object $calculatorAction = getCalculatorAction();
        result = result * 59 + (($calculatorAction == null) ? 43 : $calculatorAction.hashCode());
        Object $text = getText();
        return result * 59 + (($text == null) ? 43 : $text.hashCode());
    }

    public DisplayField() {
        this.finished = true;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public void setCalculatorAction(Action calculatorAction) {
        this.calculatorAction = calculatorAction;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public void setPreviousResult(Integer previousResult) {
        this.previousResult = previousResult;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String toString() {
        return "DisplayField(num1=" + getNum1() + ", num2=" + getNum2() + ", calculatorAction=" + getCalculatorAction() + ", result=" + getResult() + ", previousResult=" + getPreviousResult() + ", text=" + getText() + ", finished=" + isFinished() + ")";
    }

    protected enum Action {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        DIVISION("/");

        private final String character;

        Action(String character) {
            this.character = character;
        }

        public String getCharacter() {
            return this.character;
        }
    }

    public Integer getNum1() {
        return this.num1;
    }

    public Integer getNum2() {
        return this.num2;
    }

    public Action getCalculatorAction() {
        return this.calculatorAction;
    }

    public Integer getResult() {
        return this.result;
    }

    public Integer getPreviousResult() {
        return this.previousResult;
    }

    public String getText() {
        return this.text;
    }

    public boolean isFinished() {
        return this.finished;
    }

    protected void calculateResult() {
        if (this.num1 == null || this.num2 == null || this.calculatorAction == null)
            return;
        switch (this.calculatorAction) {
            case ADDITION:
                this.result = Integer.valueOf(this.num1.intValue() + this.num2.intValue());
                break;
            case SUBTRACTION:
                this.result = Integer.valueOf(this.num1.intValue() - this.num2.intValue());
                break;
            case MULTIPLICATION:
                this.result = Integer.valueOf(this.num1.intValue() * this.num2.intValue());
                break;
            case DIVISION:
                if (this.num2.intValue() == 0) {
                    reset();
                    super.setText("Error: Cannot divide by 0!");
                    this.finished = true;
                    break;
                }
                this.result = Integer.valueOf(this.num1.intValue() / this.num2.intValue());
                break;
        }
        this.previousResult = this.result;
        this.finished = true;
    }

    protected void reset() {
        this.num1 = null;
        this.num2 = null;
        this.calculatorAction = null;
        this.result = null;
        this.text = null;
        update();
    }

    protected void clear() {
        reset();
        this.finished = true;
        this.previousResult = null;
    }

    protected void update() {
        if (this.num1 == null) {
            this.text = "";
        } else if (this.calculatorAction == null) {
            this.text = this.num1.toString();
        } else if (this.num2 == null) {
            this.text = String.join(" ", new CharSequence[] { this.num1.toString(), this.calculatorAction.getCharacter() });
        } else if (this.result == null) {
            this.text = String.join(" ", new CharSequence[] { this.num1.toString(), this.calculatorAction.getCharacter(), this.num2.toString() });
        } else {
            this.text = String.join(" ", new CharSequence[] { this.num1
                    .toString(), this.calculatorAction.getCharacter(), this.num2.toString(), "=", this.result.toString() });
        }
        super.setText(this.text);
        repaint();
    }

    protected boolean num1IsNegativeZero() {
        return (this.num1.intValue() == 0 && this.num1.toString().length() == 2);
    }

    protected boolean num2IsNegativeZero() {
        if (this.num2 == null)
            return false;
        return (this.num2.intValue() == 0 && this.num2.toString().length() == 2);
    }
}
