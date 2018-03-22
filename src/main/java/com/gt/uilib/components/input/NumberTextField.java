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

    public final boolean isRestrictPositiveNumber() {
        return isPositiveOnly;
    }

    public final void setRestrictPositiveNumber(boolean isPositiveNumber) {
        isPositiveOnly = isPositiveNumber;
    }

    public final boolean isNonZeroEntered() {
        String val = getText();
        BigDecimal bd = new BigDecimal(val);
        if (bd.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("nonzero");
            return true;
        }
        return false;

    }

    protected final Document createDefaultModel() {
        return new NumberFormatDocument(this);
    }

    public final void setMaxLength(int maxLength) {
        ((NumberFormatDocument) getDocument()).setMaxLength(maxLength);
    }

    public final void setDecimalPlace(int size) {
        ((NumberFormatDocument) getDocument()).setDecimalPlacesSize(size);
    }

    public final String getText() {
        String text = super.getText();
        if (text == null) return text;
        else
            return text.replaceAll("[,]", "");
    }

    public final void selectAll() {
        Document doc = getDocument();
        if (doc != null && super.getText() != null) {
            setCaretPosition(0);
            moveCaretPosition(super.getText().length());
        }
    }

    protected final void initProperties() {
        setHorizontalAlignment(4);
    }
}
