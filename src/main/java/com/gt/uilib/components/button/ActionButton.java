package com.gt.uilib.components.button;

import com.gt.common.ResourceManager;
import com.gt.uilib.components.AppFrame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ActionButton extends JLabel {

    private static final long serialVersionUID = 20110814L;
    private final String panelQualifiedClassName;
    private final ImageIcon on;
    private final ImageIcon off;

    protected ActionButton(String Text, ImageIcon on, ImageIcon off, String panelQualifiedClassName) {
        super(Text, off, CENTER);
        this.off = off;
        this.on = on;
        this.panelQualifiedClassName = panelQualifiedClassName;
        Dimension d = new Dimension(80, 80);
        setPreferredSize(d);
        setMinimumSize(d);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        initListner();
    }

    public static ActionButton create(String text, String fileName, String panelQualifiedClassName) {
        String offFile = fileName + "-on.png";
        String onFile = fileName + "-off.png";
        return new ActionButton(text, ResourceManager.getImageIcon(onFile), ResourceManager.getImageIcon(offFile), panelQualifiedClassName);
    }

    public final void highlight() {
        setIcon(on);
    }

    public final void unhighlight() {
        setIcon(off);
    }

    protected void initListner() {
        addMouseListener(getCommonListener());
    }

    private MouseListener getCommonListener() {
        MouseListener ml = new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                AppFrame.getInstance().setWindow(panelQualifiedClassName);
                highlight();
            }

            public void mouseEntered(MouseEvent e) {
                setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                highlight();

            }

            public void mouseExited(MouseEvent e) {
                setBorder(null);
                unhighlight();
            }
        };
        return ml;
    }

}
