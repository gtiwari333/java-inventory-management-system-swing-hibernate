package com.gt.uilib.components.input;

import javax.swing.FocusManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFormatDocument extends PlainDocument {

    private static final long serialVersionUID = 147012982380037443L;
    private static final String REGEXP = "^[0-9,.]+$";
    private static final String REGEXP_NEGATIVE = "^[0-9,-.]+$";
    private NumberTextField target;
    private NumberFormat format;
    private int maxLength;
    private int decimalPlacesSize;
    private Pattern pattern;
    private Pattern patternNegative;

    public NumberFormatDocument(NumberTextField f) {

        format = NumberFormat.getNumberInstance();
        maxLength = 16;
        decimalPlacesSize = 0;
        pattern = null;
        patternNegative = null;
        target = f;
        format.setGroupingUsed(true);
        pattern = Pattern.compile(REGEXP);
        patternNegative = Pattern.compile(REGEXP_NEGATIVE);
    }

    private static int calcComma(String val) {
        int n = 0;
        for (int i = 0; i < val.length(); i++)
            if (val.charAt(i) == ',') n++;

        return n;
    }

    public final int getMaxLength() {
        return maxLength;
    }

    public final void setMaxLength(int maxLength) {
        if (maxLength > 16 || maxLength < 1) {
            throw new IllegalArgumentException("the max length value is limited from 1 to 16.");
        } else {
            this.maxLength = maxLength;
        }
    }

    public final void setDecimalPlacesSize(int decimalPlacesSize) {
        this.decimalPlacesSize = decimalPlacesSize;
    }

    public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        Matcher matcher;
        if (target.isRestrictPositiveNumber()) matcher = pattern.matcher(str);
        else
            matcher = patternNegative.matcher(str);
        if (!matcher.matches()) throw new BadLocationException(str, offs);
        int dot = target.getCaret().getDot();
        int len = getLength();
        StringBuilder buf = new StringBuilder();
        if (str.equals("-")) {
            if (len == 0) {
                super.insertString(offs, str, a);
                target.setForeground(Color.RED);
                return;
            }
            buf.append(getText(0, len));
            if ('-' == buf.charAt(0)) {
                buf.delete(0, 1);
                if (dot > 0) dot--;
                super.remove(0, len);
                super.insertString(0, buf.toString(), a);
                target.getCaret().setDot(dot);
                target.setForeground(Color.BLACK);
            } else {
                super.insertString(0, str, a);
                target.setForeground(Color.RED);
            }
            return;
        }
        if (str.equals(",")) return;
        buf.append(getText(0, offs));
        buf.append(str);
        buf.append(getText(offs, len - offs));
        int comma1 = calcComma(getText(0, dot));
        String value = buf.toString();
        value = value.replace(",", "");
        String temp = value.replace("-", "");
        int periodIndex = temp.indexOf('.');
        boolean focusNext = false;
        String temp2 = temp.replace(".", "");
        if (temp2.length() == maxLength) focusNext = true;
        if (periodIndex > 0) {
            if (decimalPlacesSize == 0) throw new BadLocationException(str, offs);
            int decimal = temp.length() - periodIndex - 1;
            if (decimal > decimalPlacesSize) throw new BadLocationException(str, offs);
            temp = value.substring(0, periodIndex);
        }
        int checkLength = maxLength - decimalPlacesSize;
        if (temp.length() > checkLength) throw new BadLocationException(str, offs);
        String commaValue = format(value, offs);
        super.remove(0, getLength());
        super.insertString(0, commaValue, a);
        dot += str.length();
        int comma2 = calcComma(getText(0, dot));
        dot += comma2 - comma1;
        target.getCaret().setDot(dot);
        if (getLength() > 0 && getText(0, 1).equals("-")) target.setForeground(Color.RED);
        else
            target.setForeground(Color.BLACK);
        if (focusNext) {
            FocusManager fm = FocusManager.getCurrentManager();
            if (fm != null) {
                Component owner = fm.getFocusOwner();
                if ((owner instanceof JTextComponent)) {
                    JTextComponent tf = (JTextComponent) owner;
                    if (tf.getDocument() == this) owner.transferFocus();
                }
            }
        }
    }

    public final void remove(int offs, int len) throws BadLocationException {
        int dot = target.getCaret().getDot();
        int comma1 = calcComma(getText(0, dot));
        boolean isDelete = false;
        if (offs == dot) isDelete = true;
        if (len == 1 && offs > 0) {
            String c = getText(offs, len);
            if (c.equals(",")) if (dot > offs) offs--;
            else
                offs++;
        }
        super.remove(offs, len);
        String value = getText(0, getLength());
        if (value == null || value.length() == 0) {
            target.setForeground(Color.BLACK);
            target.getCaret().setDot(0);
            return;
        }
        value = value.replaceAll("[,]", "");
        String commaValue = format(value, offs);
        super.remove(0, getLength());
        super.insertString(0, commaValue, null);
        if (!isDelete) dot -= len;
        int comma2 = calcComma(getText(0, dot));
        dot += comma2 - comma1;
        target.getCaret().setDot(dot);
        if (getLength() > 0 && getText(0, 1).equals("-")) target.setForeground(Color.RED);
        else
            target.setForeground(Color.BLACK);
    }

    private String format(String value, int offs) throws BadLocationException {
        if (value.length() < 2) return value;
        boolean isDecimal = false;
        String intValue = value;
        String decimalValue = "";
        if (value.contains(".")) {
            isDecimal = true;
            String[] values = value.split("\\.");
            if (values.length > 2) throw new BadLocationException(value, offs);
            intValue = values[0];
            if (values.length == 2) {
                decimalValue = values[1];
                if (decimalValue.contains(".")) throw new BadLocationException(value, offs);
            }
        }
        double doubleValue;
        try {
            doubleValue = Double.parseDouble(intValue);
        } catch (NumberFormatException nex) {
            throw new BadLocationException(value, offs);
        }
        StringBuilder commaValue = new StringBuilder(format.format(doubleValue));
        if (isDecimal) {
            commaValue.append('.');
            commaValue.append(decimalValue);
        }
        return commaValue.toString();
    }
}
