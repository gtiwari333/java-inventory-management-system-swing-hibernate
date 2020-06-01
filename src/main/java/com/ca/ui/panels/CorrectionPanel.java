package com.ca.ui.panels;

import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.inputverifier.Validator;

import javax.swing.*;

public abstract class CorrectionPanel extends AbstractFunctionPanel {
	protected JLabel txtItemnmaa;
    protected JLabel txtCategoryr;
    protected JLabel txtKhatapananumbbber;
    Validator v;
    
    abstract protected void getEditPanel();
    abstract protected void handleDeleteAction();
    abstract protected boolean isValidData();
    abstract protected void handleSuccess();
    abstract protected void handleDeleteSuccess();
}
