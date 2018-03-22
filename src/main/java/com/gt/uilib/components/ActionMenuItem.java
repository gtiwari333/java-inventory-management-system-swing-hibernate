package com.gt.uilib.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.gt.common.ResourceManager;

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
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AppFrame.getInstance().setWindow(panelQualifiedClassName);
            }
        };
        return al;
    }
}
