package com.gt.uilib.components;

import javax.swing.*;
import java.awt.*;

public class GDialog extends JDialog {
    AbstractFunctionPanel funcPane;

    public GDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public GDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public final void setAbstractFunctionPanel(AbstractFunctionPanel abstractFunctionPanel, Dimension dm) {
        this.funcPane = abstractFunctionPanel;

        add(funcPane);
        setSize(dm);
        setLocation(100, 50);
        setVisible(true);
    }

}
