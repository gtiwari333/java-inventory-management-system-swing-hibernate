package com.gt.uilib.components.input;

import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * border, scrollbar, length(RxC) limit etc
 *
 * @author GT
 */
public class GTextArea extends JScrollPane {
    private JTextArea addressFLD;

    public GTextArea(int rows, int cols) {
        addressFLD = new JTextArea(rows, cols);
        addressFLD.setWrapStyleWord(true);
        addressFLD.setLineWrap(true);
        addressFLD.setDocument(new JTextFieldLimit(rows * cols));
        setBorder(BorderFactory.createEmptyBorder());

        getViewport().add(addressFLD);
        setVisible(true);
        repaint();
        revalidate();

    }

    public static void makeUI() {

        JFrame frame = new JFrame("Adventure in Nepal - Combo Test");
        frame.getContentPane().add(new GTextArea(2, 10));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }

        makeUI();
    }

    public final String getText() {
        return addressFLD.getText().trim();
    }

    public final void setText(String str) {
        addressFLD.setText(str);

    }
}

class JTextFieldLimit extends PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }

    public final void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
