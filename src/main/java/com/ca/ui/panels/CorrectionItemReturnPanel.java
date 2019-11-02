package com.ca.ui.panels;

import com.ca.db.model.ItemReturn;
import com.ca.db.model.Transfer;
import com.ca.db.service.DBUtils;
import com.ca.db.service.TransferServiceImpl;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.NumberTextField;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class CorrectionItemReturnPanel extends AbstractFunctionPanel {

    private JLabel txtItemnmaa;
    private JLabel txtCategoryr;
    private JLabel txtKhatapananumbbber;
    Validator v;
    private final String[] damageStatusStr = new String[]{"", "Good", "Unrepairable", "Needs Repair", "Exemption"};
    private JComboBox cmbReturnStatus;
    private JLabel txtTransferPnaNum1;
    private JLabel lblItemrequestnumber;
    private JLabel lblTransferquanityt;
    private JLabel lblRemainingquantity;
    private final int currentReturnId;
    private JDateChooser txtTransferDate;
    private NumberTextField txtReturnQuanitty;
    private JDateChooser returnDateChooser;
    private JTextField txtReturnnumber;

    public CorrectionItemReturnPanel(int id) {

        this.currentReturnId = id;

        getEditPanel();
        init();
        setSize(477, 449);

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                CorrectionItemReturnPanel panel = new CorrectionItemReturnPanel(2);
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public final void init() {
        /* never forget to call super.init() */
        super.init();
        try {
            ItemReturn ret = (ItemReturn) DBUtils.getById(ItemReturn.class, currentReturnId);

            //transfer
            Transfer nik = ret.getTransfer();
            txtItemnmaa.setText(nik.getItem().getName());
            txtCategoryr.setText(nik.getItem().getCategory().getCategoryName());
            txtKhatapananumbbber.setText(nik.getItem().getKhataNumber() + " / " + nik.getItem().getPanaNumber());
            txtTransferPnaNum1.setText(nik.getTransferPanaNumber());
            lblItemrequestnumber.setText(nik.getTransferRequestNumber());
            txtTransferDate.setDate(nik.getTransferDate());
            lblTransferquanityt.setText(nik.getQuantity() + "");
            lblRemainingquantity.setText(nik.getRemainingQtyToReturn() + "");

            //item return
            txtReturnnumber.setText(ret.getReturnNumber());
            returnDateChooser.setDate(ret.getAddedDate());
            txtReturnQuanitty.setText(ret.getQuantity() + "");
            cmbReturnStatus.setSelectedIndex(ret.getReturnItemCondition());
//			lbl
            // disable components :
            txtTransferDate.setEnabled(false);
            returnDateChooser.getDateEditor().setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }

    }

    private void getEditPanel() {
        setLayout(new FormLayout(new ColumnSpec[]{
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(129dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,},
                new RowSpec[]{
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(5dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblItemName = new JLabel("Item Name");
        add(lblItemName, "4, 2");

        txtItemnmaa = new JLabel("-");
        add(txtItemnmaa, "10, 2");

        JLabel lblCategory = new JLabel("Category");
        add(lblCategory, "4, 4");

        txtCategoryr = new JLabel("-");
        add(txtCategoryr, "10, 4");

        JLabel lblKhatapanaNumber = new JLabel("Khata/Pana Number");
        add(lblKhatapanaNumber, "4, 6");

        txtKhatapananumbbber = new JLabel("KhataPanaNumbbber");
        add(txtKhatapananumbbber, "10, 6");

        JLabel lblTransferNumber = new JLabel("Transfer Pana Number");
        add(lblTransferNumber, "4, 8");

        txtTransferPnaNum1 = new JLabel("xx");
        add(txtTransferPnaNum1, "10, 8");

        JLabel lblItemRequestNumber = new JLabel("Item Request Number");
        add(lblItemRequestNumber, "4, 10");

        lblItemrequestnumber = new JLabel("itemRequestNumber");
        add(lblItemrequestnumber, "10, 10");

        JLabel lblQuantity = new JLabel("Transfer Quantity");
        add(lblQuantity, "4, 12");

        lblTransferquanityt = new JLabel("TransferQuanityt");
        add(lblTransferquanityt, "10, 12");

        JLabel lblRemainingQuantity = new JLabel("Remaining Quantity");
        add(lblRemainingQuantity, "4, 14");

        lblRemainingquantity = new JLabel("remainingQuantity");
        add(lblRemainingquantity, "10, 14");

        JLabel lblDate = new JLabel("Transfer Date");
        add(lblDate, "4, 16");

        txtTransferDate = new JDateChooser();
        // txtDate.setText("Date");
        add(txtTransferDate, "10, 16, fill, default");

        JSeparator separator = new JSeparator();
        add(separator, "4, 18, 7, 1");

        JLabel lblReturnNumber = new JLabel("Return Number");
        add(lblReturnNumber, "4, 20");

        txtReturnnumber = new JTextField();
        add(txtReturnnumber, "10, 20, fill, default");
        txtReturnnumber.setColumns(10);

        JLabel lblReturnDate = new JLabel("Return Date");
        add(lblReturnDate, "4, 22");

        returnDateChooser = new JDateChooser();
        add(returnDateChooser, "10, 22, fill, default");

        JLabel lblReturnQuantity = new JLabel("Return Quantity");
        add(lblReturnQuantity, "4, 24");

        txtReturnQuanitty = new NumberTextField();
        txtReturnQuanitty.setText("Return Quanitty");
        add(txtReturnQuanitty, "10, 24, fill, default");
        txtReturnQuanitty.setColumns(10);

        cmbReturnStatus = new JComboBox(damageStatusStr);
        add(cmbReturnStatus, "10, 26, fill, default");

        JPanel panel = new JPanel();
        add(panel, "4, 28, 7, 1, fill, fill");
        panel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JButton btnReset = new JButton("Reset");
        panel.add(btnReset, "2, 2");
        btnReset.addActionListener(e -> init());

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> handleDeleteAction());

        JButton btnSave = new JButton("Save");
        panel.add(btnSave, "4, 2");
        btnSave.addActionListener(e -> handleSaveAction());
        panel.add(btnDelete, "18, 2");

    }

    private void handleDeleteAction() {
        if (!DataEntryUtils.confirmDBDelete()) {
            return;
        }
        try {
            TransferServiceImpl ns = new TransferServiceImpl();
            TransferServiceImpl.deleteTransfer(currentReturnId);

            handleDeleteSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }
    }

    @Override
    public final String getFunctionName() {
        return "Transfer Correction and Edit, Hastantaran Register";
    }

    private boolean isValidData() {
        if (returnDateChooser.getDate() != null) {
            JOptionPane.showMessageDialog(null, "Please enter proper return date!");
            return false;
        }

        int qty = Integer.parseInt("0" + txtReturnQuanitty.getText());
        int qtyToRet = Integer.parseInt("0" + lblRemainingquantity.getText());
        if (qty <= 0 || qty > qtyToRet) {
            JOptionPane.showMessageDialog(null, "Please enter return quantity >0 & < " + qtyToRet);
            return false;
        }

        int damageStatus = getDamageStatusIndex(cmbReturnStatus.getSelectedItem().toString());
        if (damageStatus < 0) {
            JOptionPane.showMessageDialog(null, "Please enter damage status!");
            return false;
        }


        if (damageStatus > 0 && qty > 0 && returnDateChooser.getDate() != null) {
            return true;
        }
        return false;

    }

    private int getDamageStatusIndex(String str) {
        for (int i = 0; i < damageStatusStr.length; i++) {
            if (str.trim().equals(damageStatusStr[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public final void handleSaveAction() {
        if (!isValidData()) {
            return;
        }
        if (!DataEntryUtils.confirmDBUpdate()) {
            return;
        }
        try {
            int damageStatus = getDamageStatusIndex(cmbReturnStatus.getSelectedItem().toString());
            handleSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }
    }

    private void handleSuccess() {
        JOptionPane.showMessageDialog(null, "Saved Successfully");
        Window w = SwingUtilities.getWindowAncestor(CorrectionItemReturnPanel.this);
        w.setVisible(false);
    }

    private void handleDeleteSuccess() {
        JOptionPane.showMessageDialog(null, "Deleted Successfully");
        Window w = SwingUtilities.getWindowAncestor(CorrectionItemReturnPanel.this);
        w.setVisible(false);
    }

    @Override
    public void enableDisableComponents() {

    }

}
