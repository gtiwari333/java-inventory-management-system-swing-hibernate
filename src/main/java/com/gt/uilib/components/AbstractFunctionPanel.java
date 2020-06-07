package com.gt.uilib.components;

import com.ca.db.model.Item;
import com.ca.ui.panels.FormPanelStrategy;
import com.gt.common.constants.CommonConsts;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.input.NumberTextField;
import com.gt.uilib.inputverifier.Verifier;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * com.gt.uilib.components-AbstractFunctionPanel.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public abstract class AbstractFunctionPanel extends JPanel implements Verifier {
    private static final long serialVersionUID = -5535283266424039078L;
    public boolean isReadyToClose = false;
    protected AppFrame mainApp;
    protected Status status;
    protected boolean debug;
    FormPanelStrategy formpanelstrategy;
    
    public void content(JPanel formPanel){
    	formpanelstrategy.content(formPanel);
    }
    
    public void setFormPanelStrategy(FormPanelStrategy formpanelstrategy)
    {
    	this.formpanelstrategy = formpanelstrategy;
    }

    public AbstractFunctionPanel() {
        debug = AppFrame.debug;
        setBounds(100, 100, 450, 300);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CommonConsts.COLOR_TOOLBAR_BORDER));
    }

    /**
     * function name will be displayed in title bar
     */
    abstract public String getFunctionName();

    // utility method to clear all editable text fields and combo boxes

    /**
     * initialize fields, set initial status, <br/>
     * it sh
     */
    public void init() {
        // UIUtils.updateFont(new Font("Arial", Font.PLAIN, 12), this);
        UIUtils.decorateBorders(this);
    }

    /**
     * we can override this function to display different message
     */
    public static final String getUnsavedExitMessage() {
        return "Are you sure to exit?";

    }

    public final AppFrame getMainFrame() {
        return mainApp;
    }

    public final void validateFailed() {
        getMainFrame().getStatusLbl().setText("Please enter data properly before saving");

    }

    public void validatePassed() {

    }

    protected static final void handleDBError(Exception e) {
        System.out.println("db error " + e.toString());
        e.printStackTrace();
        String expln = e.toString();

        String[] exp = expln.split("Exception:");
        System.err.println("Reason - " + exp[exp.length - 1]);
        JOptionPane.showMessageDialog(null, "DB Error" + e.getMessage(), "Error ! ", JOptionPane.ERROR_MESSAGE);
    }

    public final void changeStatus(Status status) {
        this.status = status;
        enableDisableComponents();
    }

    abstract public void handleSaveAction();

    abstract public void enableDisableComponents();
    
    protected void setModelIntoForm(Item bro, JTextField txtPurchaseordernumber, JTextField txtName, JTextField txtPananumber, DataComboBox cmbCategory,
    		JTextField txtPartsnumber, JTextField txtSerialnumber, JTextField txtRacknumber, JDateChooser txtPurDate, DataComboBox cmbVendor,
    		NumberTextField txtQuantity, NumberTextField txtRate, JTextField txtTotal, JTextField txtKhatanumber,
    		JTextField txtDakhilanumber) {
        txtPurchaseordernumber.setText(bro.getPurchaseOrderNo());
        txtName.setText(bro.getName());
        txtPananumber.setText(bro.getPanaNumber());
        cmbCategory.selectItem(bro.getCategory().getId());
        txtPartsnumber.setText(bro.getPartsNumber());
        txtSerialnumber.setText(bro.getSerialNumber());
        txtRacknumber.setText(bro.getRackNo());
        txtPurDate.setDate(bro.getPurchaseDate());
        cmbVendor.selectItem(bro.getVendor().getId());
        txtQuantity.setText(bro.getQuantity() + "");
        txtRate.setText(bro.getRate().toString());
        BigDecimal total = bro.getRate().multiply(new BigDecimal(bro.getQuantity()));
        txtTotal.setText(total.toString());
        txtKhatanumber.setText(bro.getKhataNumber());
        txtDakhilanumber.setText(bro.getDakhilaNumber());
    }
}
