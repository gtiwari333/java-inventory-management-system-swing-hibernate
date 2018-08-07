package com.gt.uilib.components;

import com.gt.common.ResourceManager;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionMenuItem extends JMenuItem {
    private static final long serialVersionUID = -6416935244618242268L;
    private final String panelQualifiedClassName;

    protected ActionMenuItem(String text, String fileFullName, String panelQualifiedClassName) {

        super(text, ResourceManager.getImageIcon(fileFullName));
        this.panelQualifiedClassName = panelQualifiedClassName;
        initListner();
    }

    public static ActionMenuItem create(String text, String fileName, String panelQualifiedClassName) {
        String fullFileName = fileName + "-menu.png";
        return new ActionMenuItem(text, fullFileName, panelQualifiedClassName);
    }

    protected final void initListner() {
        addActionListener(getCommonListener());
    }

    private ActionListener getCommonListener() {
        ActionListener al = e -> AppFrame.getInstance().setWindow(panelQualifiedClassName);
        return al;
    }
}
