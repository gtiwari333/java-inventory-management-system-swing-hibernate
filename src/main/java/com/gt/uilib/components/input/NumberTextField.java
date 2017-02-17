package com.gt.uilib.components.input;

import javax.swing.*;
import javax.swing.text.Document;
import java.math.BigDecimal;

public class NumberTextField extends JTextField {

    private static final long serialVersionUID = 2279403774195701454L;
    private boolean isPositiveOnly;

    public NumberTextField(int maxLength) {
        isPositiveOnly = false;
        setMaxLength(maxLength);
    }

    public NumberTextField(int maxLength, boolean isPositiveOnly) {
        this.isPositiveOnly = isPositiveOnly;
        setMaxLength(maxLength);
    }

    public NumberTextField() {
        isPositiveOnly = false;
    }

    public NumberTextField(boolean isPositiveOnly) {
        this.isPositiveOnly = isPositiveOnly;
    }

    public boolean isRestrictPositiveNumber() {
        return isPositiveOnly;
    }

    public void setRestrictPositiveNumber(boolean isPositiveNumber) {
        isPositiveOnly = isPositiveNumber;
    }

    public boolean isNonZeroEntered() {
        String val = getText();
        BigDecimal bd = new BigDecimal(val);
        if (bd.compareTo(BigDecimal.ZERO) == 1) {
            System.out.println("nonzero");
            return true;
        }
        return false;

    }

    protected Document createDefaultModel() {
        return new NumberFormatDocument(this);
    }

    public void setMaxLength(int maxLength) {
        ((NumberFormatDocument) getDocument()).setMaxLength(maxLength);
    }

    public void setDecimalPlace(int size) {
        ((NumberFormatDocument) getDocument()).setDecimalPlacesSize(size);
    }

    public String getText() {
        String text = super.getText();
        if (text == null) return text;
        else
            return text.replaceAll("[,]", "");
    }

    public void selectAll() {
        Document doc = getDocument();
        if (doc != null && super.getText() != null) {
            setCaretPosition(0);
            moveCaretPosition(super.getText().length());
        }
    }

    protected void initProperties() {
        setHorizontalAlignment(4);
    }
}
