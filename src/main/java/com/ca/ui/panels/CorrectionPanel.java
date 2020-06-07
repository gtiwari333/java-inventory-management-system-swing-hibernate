package com.ca.ui.panels;

import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.inputverifier.Validator;
import com.ca.ui.panels.CorrectionTransferPanel;
import com.ca.ui.panels.CorrectionItemReturnPanel;

import java.awt.Window;

import javax.swing.*;

public abstract class CorrectionPanel extends AbstractFunctionPanel {
	protected JLabel txtItemnmaa;
    protected JLabel txtCategoryr;
    protected JLabel txtKhatapananumbbber;
    Validator v;
    
    abstract protected void getEditPanel();
    abstract protected void handleDeleteAction();
    abstract protected boolean isValidData();
    
    
    protected void handleSuccess(CorrectionPanel panel) {
        JOptionPane.showMessageDialog(null, "Saved Successfully");
        Window w = SwingUtilities.getWindowAncestor(panel);
        w.setVisible(false);
    }
    
    protected void handleDeleteSuccess(CorrectionPanel panel) {
        JOptionPane.showMessageDialog(null, "Deleted Successfully");
        Window w = SwingUtilities.getWindowAncestor(panel);
        w.setVisible(false);
    }
}
