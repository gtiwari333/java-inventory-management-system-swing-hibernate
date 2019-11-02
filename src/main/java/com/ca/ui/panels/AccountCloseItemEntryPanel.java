package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.Vendor;
import com.ca.db.service.DBUtils;
import com.ca.db.service.ItemServiceImpl;
import com.gt.common.constants.Status;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.StringUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.input.NumberTextField;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountCloseItemEntryPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Purchase Order No.", "Name", "Pana Number", "Category", "Parts Number", "Serial Number",
            "Rack Number", "Purchase date", "Added date", "Vendor", "Original Quantity", "Qty in Stock", "Rate", "Unit", "Total", "Saved Date"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private final Validator v;
    private JDateChooser txtPurDate;
    private JTextField txtName;
    private JButton btnReadAll;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private int editingPrimaryId = 0;
    private JButton btnModify;
    private JButton btnCancel;
    private DataComboBox cmbCategory;
    private JLabel lblPartsNumber;
    private JTextField txtPartsnumber;
    private JLabel lblPurchaseDate;
    private JLabel lblRacknumber;
    private JTextField txtRacknumber;
    private DataComboBox cmbVendor;
    private JLabel lblQuantity;
    private NumberTextField txtQuantity;
    private JLabel lblRate;
    private JLabel lblTotal;
    private JLabel lblSerialNumber;
    private NumberTextField txtRate;
    private JTextField txtTotal;
    private JTextField txtSerialnumber;
    private final KeyListener priceCalcListener = new KeyListener() {

        public void keyPressed(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

        public void keyTyped(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

        public void keyReleased(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

    };
    private JLabel lblPanaNumber;
    private JTextField txtPananumber;
    private JLabel lblPurchaseOrderNumber;
    private JTextField txtPurchaseordernumber;
    private JButton btnNewVendor;
    private JComboBox cmbReturnableOrNonRet;
    private JLabel lblKhataNumber;
    private JTextField txtKhatanumber;

    private JLabel lblDakhilaNumber;
    private JTextField txtDakhilanumber;

    public AccountCloseItemEntryPanel() {
        /**
         * all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.3);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        // splitPane.setDividerSize(0);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        /**
         * never forget to call after setting up UI
         */
        v = new Validator(mainApp, true);
        init();
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
                AccountCloseItemEntryPanel panel = new AccountCloseItemEntryPanel();
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
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
        intCombo();
        txtTotal.setText("0");
        txtQuantity.setText("0");
        txtRate.setText("0");
    }

    private void intCombo() {
        try {
            /* Category Combo */
            cmbCategory.init();
            List<Category> cl = DBUtils.readAll(Category.class);
            for (Category c : cl) {
                cmbCategory.addRow(new Object[]{c.getId(), c.getCategoryName()});
            }

            /* Vendor Combo */
            cmbVendor.init();
            List<Vendor> vl = DBUtils.readAll(Vendor.class);
            for (Vendor v : vl) {
                cmbVendor.addRow(new Object[]{v.getId(), v.getName(), v.getAddress()});
            }

        } catch (Exception e) {
            handleDBError(e);
        }

    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            btnReadAll = new JButton("Read All");
            btnReadAll.addActionListener(e -> {
                readAndShowAll(true);
                changeStatus(Status.READ);
            });

            cmbReturnableOrNonRet = new JComboBox();
            cmbReturnableOrNonRet.addItem("");
            cmbReturnableOrNonRet.addItem("Returnable");
            cmbReturnableOrNonRet.addItem("Non-Returnable");
            buttonPanel.add(cmbReturnableOrNonRet);
            buttonPanel.add(btnReadAll);

            btnModify = new JButton("Change Pana Number");
            btnModify.addActionListener(e -> {
                if (editingPrimaryId > 0) changeStatus(Status.MODIFY);
            });
            buttonPanel.add(btnModify);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> changeStatus(Status.READ));
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }

    private void handleDeleteAction() {
        if (status == Status.READ) {
            if (DataEntryUtils.confirmDBDelete()) deleteSelectedBranchOffice();
        }

    }

    private void deleteSelectedBranchOffice() {
        try {
            DBUtils.deleteById(Item.class, editingPrimaryId);
            changeStatus(Status.READ);
            JOptionPane.showMessageDialog(null, "Deleted");
            readAndShowAll(false);
        } catch (Exception e) {
            System.out.println("deleteSelectedBranchOffice");
            handleDBError(e);
        }
    }

    @Override
    public final void enableDisableComponents() {
        v.resetErrors();
        switch (status) {
            case NONE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.clearAllFields(formPanel);
                btnReadAll.setEnabled(true);
                table.setEnabled(true);
                txtPurDate.getDateEditor().setEnabled(false);
                cmbReturnableOrNonRet.setEnabled(true);
                break;
            // case CREATE:
            // UIUtils.toggleAllChildren(buttonPanel, false);
            // // UIUtils.toggleAllChildren(formPanel, true);
            // table.setEnabled(false);
            // btnCancel.setEnabled(true);
            // btnSave.setEnabled(true);
            // txtPurDate.getDateEditor().setEnabled(false);
            // break;
            case MODIFY:
                // UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(buttonPanel, false);
                btnCancel.setEnabled(true);
                txtPananumber.setEnabled(true);
                btnSave.setEnabled(true);
                table.setEnabled(false);
                txtPurDate.getDateEditor().setEnabled(false);
                break;

            case READ:
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.clearAllFields(formPanel);
                table.clearSelection();
                table.setEnabled(true);
                editingPrimaryId = -1;

                intCombo();
                break;

            default:
                break;
        }
    }

    @Override
    public final void handleSaveAction() {
        switch (status) {
            case CREATE:
                // create new
                save();
                break;
            case MODIFY:
                // modify
                if (DataEntryUtils.confirmDBUpdate()) save();
                break;

            default:
                break;
        }
    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }

        v.addTask(txtPananumber, "Req", null, true);


    }

    private void setModelIntoForm(Item bro) {
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
        txtKhatanumber.setText(bro.getKhataNumber());
        txtDakhilanumber.setText(bro.getDakhilaNumber());
        BigDecimal total = bro.getRate().multiply(new BigDecimal(bro.getQuantity()));
        txtTotal.setText(total.toString());
    }

    private void save() {
        initValidator();
        if (v.validate()) {
            try {

                Item bo = (Item) DBUtils.getById(Item.class, editingPrimaryId);
                bo.setLastModifiedDate(new Date());
                // the previous item is transferred to new
                bo.setCurrentFiscalYear(DateTimeUtils.getCurrentFiscalYear());
                bo.setAccountTransferStatus(Item.ACCOUNT_TRANSFERRED_TO_NEW);

                Item newBo = new Item();
                newBo.setPurchaseOrderNo(bo.getPurchaseOrderNo());
                newBo.setPurchaseDate(bo.getPurchaseDate());
                newBo.setName(bo.getName());
                newBo.setKhataNumber(bo.getKhataNumber());
                newBo.setKhataNumber(bo.getKhataNumber());
                newBo.setDakhilaNumber(bo.getDakhilaNumber());
                newBo.setPanaNumber(txtPananumber.getText().trim());
                newBo.setRackNo(bo.getRackNo());
                newBo.setRate(bo.getRate());
                newBo.setPartsNumber(bo.getPartsNumber());
                newBo.setOriginalQuantity(bo.getOriginalQuantity());
                newBo.setQuantity(bo.getQuantity());
                newBo.setSerialNumber(bo.getSerialNumber());
                newBo.setPurchaseDate(bo.getPurchaseDate());
                newBo.setAddedType(Item.ACCOUNT_TRANSFERRED_TO_NEW);
                newBo.setCategory(bo.getCategory());
                newBo.setSpecification(bo.getSpecification());
                newBo.setVendor(bo.getVendor());
                newBo.setUnitsString(bo.getUnitsString());


                newBo.setdFlag(1);
                newBo.setParentItemId(bo.getId());
                newBo.setLastModifiedDate(new Date());
                // FIXME: new approach? nepali calendar??
                newBo.setCurrentFiscalYear(DateTimeUtils.getCurrentFiscalYear() + 1);
                System.out.println(newBo.getSpecification().getId() + "  > sp id");

                DBUtils.saveOrUpdate(newBo);
                DBUtils.saveOrUpdate(bo);
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                changeStatus(Status.READ);
                UIUtils.clearAllFields(upperPane);
                readAndShowAll(false);
            } catch (Exception e) {
                System.out.println("save--");
                handleDBError(e);
            }
        }
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Items to Transfer to new Account",
                    TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(90dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(137dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(49dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(36dlu;default)"),},
                    new RowSpec[]{
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            RowSpec.decode("max(16dlu;default)"),
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            RowSpec.decode("top:max(14dlu;default)"),
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
                            FormFactory.DEFAULT_ROWSPEC,}));

            lblPurchaseOrderNumber = new JLabel("Purchase Order Number");
            formPanel.add(lblPurchaseOrderNumber, "4, 2");

            txtPurchaseordernumber = new JTextField();
            txtPurchaseordernumber.setEnabled(false);
            txtPurchaseordernumber.setEditable(false);
            formPanel.add(txtPurchaseordernumber, "8, 2, fill, default");
            txtPurchaseordernumber.setColumns(10);

            lblKhataNumber = new JLabel("Khata Number");
            formPanel.add(lblKhataNumber, "4, 4");

            txtKhatanumber = new JTextField();
            formPanel.add(txtKhatanumber, "8, 4, fill, default");
            txtKhatanumber.setColumns(10);

            lblDakhilaNumber = new JLabel("Dakhila Number");
            formPanel.add(lblDakhilaNumber, "4, 6");

            txtDakhilanumber = new JTextField();
            formPanel.add(txtDakhilanumber, "8, 6, fill, default");
            txtDakhilanumber.setColumns(10);

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 8");

            txtName = new JTextField();
            txtName.setEnabled(false);
            txtName.setEditable(false);
            formPanel.add(txtName, "8, 8, fill, default");
            txtName.setColumns(10);

            lblPanaNumber = new JLabel("Pana Number");
            formPanel.add(lblPanaNumber, "4, 10");

            txtPananumber = new JTextField();
            formPanel.add(txtPananumber, "8, 10, fill, default");
            txtPananumber.setColumns(10);

            JLabel lblCategory = new JLabel("Category");
            formPanel.add(lblCategory, "4, 12, default, top");

            cmbCategory = new DataComboBox();
            cmbCategory.setEnabled(false);
            formPanel.add(cmbCategory, "8, 12, fill, default");

            lblPartsNumber = new JLabel("Parts Number");
            formPanel.add(lblPartsNumber, "4, 14");

            txtPartsnumber = new JTextField();
            txtPartsnumber.setEnabled(false);
            txtPartsnumber.setEditable(false);
            formPanel.add(txtPartsnumber, "8, 14, fill, default");
            txtPartsnumber.setColumns(10);

            lblSerialNumber = new JLabel("Serial Number");
            formPanel.add(lblSerialNumber, "4, 16");

            txtSerialnumber = new JTextField();
            txtSerialnumber.setEnabled(false);
            txtSerialnumber.setEditable(false);
            formPanel.add(txtSerialnumber, "8, 16, fill, default");
            txtSerialnumber.setColumns(10);

            lblRacknumber = new JLabel("Rack Number");
            formPanel.add(lblRacknumber, "4, 18");

            txtRacknumber = new JTextField();
            formPanel.add(txtRacknumber, "8, 18, fill, default");
            txtRacknumber.setColumns(10);

            lblPurchaseDate = new JLabel("Purchase Date");
            formPanel.add(lblPurchaseDate, "4, 20");

            txtPurDate = new JDateChooser();
            txtPurDate.setEnabled(false);
            formPanel.add(txtPurDate, "8, 20, fill, default");

            JLabel lblPhoneNumber = new JLabel("Vendor");
            formPanel.add(lblPhoneNumber, "4, 22");

            cmbVendor = new DataComboBox();
            formPanel.add(cmbVendor, "8, 22, fill, default");

            lblQuantity = new JLabel("Quantity");
            formPanel.add(lblQuantity, "4, 24");

            txtQuantity = new NumberTextField(5);
            txtQuantity.setEnabled(false);
            txtQuantity.setEditable(false);

            txtQuantity.addKeyListener(priceCalcListener);
            formPanel.add(txtQuantity, "8, 24, fill, default");
            txtQuantity.setColumns(10);

            lblRate = new JLabel("Rate");
            formPanel.add(lblRate, "4, 26");

            txtRate = new NumberTextField(true);
            txtRate.setEnabled(false);
            txtRate.setEditable(false);
            txtRate.setDecimalPlace(2);
            txtRate.addKeyListener(priceCalcListener);
            formPanel.add(txtRate, "8, 26, fill, default");
            txtRate.setDecimalPlace(2);

            lblTotal = new JLabel("Total");
            formPanel.add(lblTotal, "4, 28");

            txtTotal = new JTextField();
            txtTotal.setEditable(false);
            txtTotal.setFocusable(false);
            // txtTotal.setEnabled(false);
            formPanel.add(txtTotal, "8, 28, fill, default");
            txtTotal.setColumns(10);

            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                handleSaveAction();
                btnSave.setEnabled(true);
            });
            formPanel.add(btnSave, "10, 28, fill, default");
        }

        // set Verifiers
        // txtRate.setInputVerifier(new DataTypeVerifier(null, txtRate,
        // "must be price", RegexUtils.NON_NEGATIVE_MONEY_FIELD, false, false));
        // txtQuantity.setInputVerifier(new DataTypeVerifier(null, txtQuantity,
        // "must be int", RegexUtils.NON_NEGATIVE_INTEGER_FIELD, false, false));

        return formPanel;
    }

    /**
     * Quantity >0, and close_status not transferred
     *
     * @param showSize0Error
     */
    private void readAndShowAll(boolean showSize0Error) {
        try {
            ItemServiceImpl is = new ItemServiceImpl();
            int type = cmbReturnableOrNonRet.getSelectedIndex();
            List<Item> brsL = ItemServiceImpl.getAllItemsToCloseCurrFiscalYear(type);
            editingPrimaryId = -1;
            if (brsL == null || brsL.size() == 0) {
                if (showSize0Error) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
                dataModel.resetModel();

                dataModel.fireTableDataChanged();
                editingPrimaryId = -1;
            } else {
                showListInGrid(brsL);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private String getPrice() {
        BigDecimal amt = new BigDecimal("0");
        BigDecimal rate = new BigDecimal("0");
        BigDecimal qty = new BigDecimal("0");
        if (!StringUtils.isEmpty(txtRate.getText())) {
            rate = new BigDecimal(txtRate.getText().trim());
        }
        if (!StringUtils.isEmpty(txtQuantity.getText())) {
            qty = new BigDecimal(txtQuantity.getText().trim());
        }
        System.out.println("Rate " + txtRate.getText() + " Qty " + txtQuantity.getText());
        System.out.println("Rate " + rate + " Qty " + qty);
        amt = rate.multiply(qty);
        return amt + "";
    }

    private void showListInGrid(List<Item> brsL) {
        dataModel.resetModel();
        int sn = 0;
        for (Item bo : brsL) {
            BigDecimal total = bo.getRate().multiply(new BigDecimal(bo.getQuantity()));
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getPurchaseOrderNo(), bo.getName(), bo.getPanaNumber(),
                    bo.getCategory().getCategoryName(), bo.getPartsNumber(), bo.getSerialNumber(), bo.getRackNo(),
                    DateTimeUtils.getCvDateMMMddyyyy(bo.getPurchaseDate()), DateTimeUtils.getCvDateMMMddyyyy(bo.getAddedDate()), bo.getVendor().getName(),
                    bo.getOriginalQuantity(), bo.getQuantity(), bo.getRate(), bo.getUnitsString().getValue(), total, DateTimeUtils.getCvDateMMMddyyyy(bo.getAddedDate())});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

    @Override
    public final String getFunctionName() {
        return "Account Closing - Ledger Pana Number Update";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.CENTER);
            upperPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return upperPane;
    }

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setLayout(new BorderLayout());
            dataModel = new EasyTableModel(header);

            table = new BetterJTable(dataModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            lowerPane.add(sp, BorderLayout.CENTER);
            table.getSelectionModel().addListSelectionListener(e -> {
                int selRow = table.getSelectedRow();
                if (selRow != -1) {
                    /**
                     * if second column doesnot have primary id info, then
                     */
                    int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
                    // changeStatus(Status.NONE);
                    populateSelectedRowInForm(selectedId);
                }
            });
        }
        return lowerPane;
    }

    private void populateSelectedRowInForm(int selectedId) {
        try {
            Item bro = (Item) DBUtils.getById(Item.class, selectedId);

            if (bro != null) {
                setModelIntoForm(bro);
                editingPrimaryId = bro.getId();
                cmbCategory.selectItem(bro.getCategory().getId());
                cmbVendor.selectItem(bro.getVendor().getId());
                txtPurDate.setDate(bro.getPurchaseDate());
            }
        } catch (Exception e) {
            System.out.println("populateSelectedRowInForm " + e.getMessage());
            handleDBError(e);
        }
    }

}
