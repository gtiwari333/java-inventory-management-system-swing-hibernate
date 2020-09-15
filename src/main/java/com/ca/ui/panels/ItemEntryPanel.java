package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.UnitsString;
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
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ItemEntryPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Purchase Order No.", "Name", "Category", "Specification",
            "Parts No.", "Serial No.", "Rack Number", "Purchase date", "Added date", "Vendor", "Original Quantity", "Qty in Stock", "Rate", "Unit",
            "Total"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JDateChooser txtPurDate;
    private SpecificationPanel currentSpecificationPanel;
    private JTextField txtName;
    private JButton btnReadAll;
    private JButton btnNew;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private int editingPrimaryId = 0;
    private JButton btnCancel;
    private DataComboBox cmbCategory;
    private JPanel specPanelHolder;
    private JTextField txtPartsnumber;
    private JTextField txtRacknumber;
    private DataComboBox cmbVendor;
    private NumberTextField txtQuantity;
    private NumberTextField txtRate;
    private JTextField txtTotal;
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
    private JTextField txtSerialnumber;
    private JTextField txtPurchaseordernumber;
    private JTextField txtEntrynumber;
    private DataComboBox cmbUnitcombo;

    public ItemEntryPanel() {
        /*
          all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.3);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        /*
          never forget to call after setting up UI
         */
        v = new Validator(mainApp, true);
        init();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                ItemEntryPanel panel = new ItemEntryPanel();
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
            initCmbCategory();

            initCmbVendor();

            initCmbUnits();
        } catch (Exception e) {
            handleDBError(e);
        }
        /* Item listener on cmbCategory - to change specification panel */
        cmbCategory.addItemListener(e -> {
            int id = cmbCategory.getSelectedId();
            specPanelHolder.removeAll();
            currentSpecificationPanel = null;
            if (id > 0) {
                currentSpecificationPanel = new SpecificationPanel(id);
                specPanelHolder.add(currentSpecificationPanel, FlowLayout.LEFT);
                if (status == Status.CREATE || status == Status.MODIFY)
                    currentSpecificationPanel.enableAll();
                else
                    currentSpecificationPanel.disableAll();
            }
            specPanelHolder.repaint();
            specPanelHolder.revalidate();

        });

    }

    private void initCmbCategory() throws Exception {
        /* Category Combo */
        cmbCategory.init();
        List<Category> cl = DBUtils.readAll(Category.class);
        for (Category c : cl) {
            cmbCategory.addRow(new Object[]{c.getId(), c.getCategoryName()});
        }
    }

    private void initCmbUnits() throws Exception {
        /* Category Combo */
        cmbUnitcombo.init();
        List<UnitsString> cl = DBUtils.readAll(UnitsString.class);
        for (UnitsString c : cl) {
            cmbUnitcombo.addRow(new Object[]{c.getId(), c.getValue() + ""});
        }
    }

    private void initCmbVendor() throws Exception {
        /* Vendor Combo */
        cmbVendor.init();
        List<Vendor> vl = DBUtils.readAll(Vendor.class);
        for (Vendor v : vl) {
            cmbVendor.addRow(new Object[]{v.getId(), v.getName(), v.getAddress()});
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
            buttonPanel.add(btnReadAll);

            btnNew = new JButton("New");
            btnNew.addActionListener(e -> changeStatus(Status.CREATE));
            buttonPanel.add(btnNew);

            JButton btnDeleteThis = new JButton("Delete This");
            btnDeleteThis.addActionListener(e -> {
                if (editingPrimaryId > 0) handleDeleteAction();
            });

            JButton btnModify = new JButton("Modify");
            btnModify.addActionListener(e -> {
                if (editingPrimaryId > 0) changeStatus(Status.MODIFY);
            });
            buttonPanel.add(btnModify);
            buttonPanel.add(btnDeleteThis);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> changeStatus(Status.READ));
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }

    private void handleDeleteAction() {
        if (status == Status.READ) {
            if (DataEntryUtils.confirmDBDelete()) deleteSelectedItem();
        }

    }

    private void deleteSelectedItem() {
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
                UIUtils.toggleAllChildren(currentSpecificationPanel, false);
                UIUtils.clearAllFields(formPanel);
                UIUtils.clearAllFields(currentSpecificationPanel);
                btnReadAll.setEnabled(true);
                btnNew.setEnabled(true);
                table.setEnabled(true);
                txtPurDate.getDateEditor().setEnabled(false);
                break;
            case CREATE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(currentSpecificationPanel, true);
                txtPurDate.setDate(new Date());
                table.setEnabled(false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                txtPurDate.getDateEditor().setEnabled(false);
                break;
            case MODIFY:
                UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(currentSpecificationPanel, true);
                cmbCategory.setEnabled(false);

                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                table.setEnabled(false);
                txtPurDate.getDateEditor().setEnabled(false);
                break;

            case READ:
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.toggleAllChildren(currentSpecificationPanel, false);
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

                save(false);
                break;
            case MODIFY:
                // modify
                save(true);
                break;

            default:
                break;
        }
    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }
        v = new Validator(mainApp, true);
        v.addTask(txtName, "Req", null, true);
        // TODO: confirm the fields to validate
        v.addTask(cmbCategory, "required", null, true);
        v.addTask(cmbVendor, "required", null, true);
        v.addTask(currentSpecificationPanel, "Spec req", null, true);
        v.addTask(txtPurDate, "", null, true, true);
        v.addTask(txtQuantity, "Req", null, true);
        v.addTask(txtEntrynumber, "Req", null, true);

    }

    /**
     * current date not added to object ( for the case of modified data)
     *
     * @return
     */
    private Item getModelFromForm() {
        Item bo = new Item();
        bo.setPurchaseOrderNo(txtPurchaseordernumber.getText().trim());
        bo.setName(txtName.getText().trim());
        bo.setRackNo(txtRacknumber.getText().trim().toUpperCase());
        bo.setRate(new BigDecimal(txtRate.getText().trim()));
        bo.setPartsNumber(txtPartsnumber.getText().trim());
        bo.setOriginalQuantity(Integer.parseInt(txtQuantity.getText().trim()));
        bo.setQuantity(Integer.parseInt(txtQuantity.getText().trim()));
        bo.setSerialNumber(txtSerialnumber.getText().trim());
        bo.setPurchaseDate(txtPurDate.getDate());
        bo.setAddedType(Item.ADD_TYPE_NEW_ENTRY);
        try {

            bo.setCategory((Category) DBUtils.getById(Category.class, cmbCategory.getSelectedId()));
            bo.setSpecification(currentSpecificationPanel.getSpecificationsObject());
            bo.setVendor((Vendor) DBUtils.getById(Vendor.class, cmbVendor.getSelectedId()));
            bo.setUnitsString((UnitsString) DBUtils.getById(UnitsString.class, cmbUnitcombo.getSelectedId()));

        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }
        return bo;
    }

    private void setModelIntoForm(Item bro) {
        txtPurchaseordernumber.setText(bro.getPurchaseOrderNo());
        txtName.setText(bro.getName());
        cmbCategory.selectItem(bro.getCategory().getId());
        txtPartsnumber.setText(bro.getPartsNumber());
        txtSerialnumber.setText(bro.getSerialNumber());
        txtRacknumber.setText(bro.getRackNo());
        txtPurDate.setDate(bro.getPurchaseDate());
        cmbVendor.selectItem(bro.getVendor().getId());
        txtQuantity.setText(bro.getQuantity() + "");
        txtRate.setText(bro.getRate().toString());
        cmbUnitcombo.selectItem(bro.getUnitsString().getId());
        BigDecimal total = bro.getRate().multiply(new BigDecimal(bro.getQuantity()));
        txtTotal.setText(total.toString());
    }

    private void save(boolean isModified) {
        initValidator();
        if (v.validate()) {

            if (!isModified) {
                if (!DataEntryUtils.confirmDBSave()) {
                    return;
                }
            } else {
                if (!DataEntryUtils.confirmDBUpdate()) {
                    return;
                }
            }
            try {

                Item newBo = getModelFromForm();
                if (isModified) {
                    Item bo = (Item) DBUtils.getById(Item.class, editingPrimaryId);
                    System.out.println("is MODIFIED..........");
                    bo.setName(newBo.getName());
                    bo.setPurchaseOrderNo(newBo.getPurchaseOrderNo());
                    bo.setRackNo(newBo.getRackNo());
                    bo.setRate(newBo.getRate());
                    bo.setPartsNumber(newBo.getPartsNumber());
                    bo.setOriginalQuantity(newBo.getOriginalQuantity());
                    bo.setQuantity(newBo.getQuantity());
                    bo.setSerialNumber(newBo.getSerialNumber());
                    bo.setPurchaseDate(newBo.getPurchaseDate());
                    bo.setCategory(newBo.getCategory());
                    bo.setVendor(newBo.getVendor());
                    bo.setAddedType(newBo.getAddedType());
                    bo.setLastModifiedDate(new Date());
                    bo.setCurrentFiscalYear(DateTimeUtils.getCurrentFiscalYear());
                    DBUtils.saveOrUpdate(bo);
                } else {
                    // save new
                    newBo.setdFlag(1);
                    newBo.setAddedDate(new Date());
                    newBo.setCurrentFiscalYear(DateTimeUtils.getCurrentFiscalYear());
                    DBUtils.saveOrUpdate(newBo);
                }
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                changeStatus(Status.READ);
                UIUtils.clearAllFields(upperPane);
                readAndShowAll(false);
            } catch (Exception e) {
                System.out.println("save--" + e.getMessage());
                handleDBError(e);
            }
        }
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "New Item Information Entry", TitledBorder.LEADING,
                    TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(90dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(137dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(49dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(56dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(137dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(29dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(26dlu;default):grow"),},
                    new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(11dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(15dlu;default)"),
                            FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(55dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblPurchaseOrderNumber = new JLabel("Purchase Order Number");
            formPanel.add(lblPurchaseOrderNumber, "4, 2");

            txtPurchaseordernumber = new JTextField();
            formPanel.add(txtPurchaseordernumber, "8, 2, fill, default");
            txtPurchaseordernumber.setColumns(10);

            JLabel lblEntryNumber = new JLabel("Entry Number");
            formPanel.add(lblEntryNumber, "4, 6");

            txtEntrynumber = new JTextField();
            formPanel.add(txtEntrynumber, "8, 6, fill, default");
            txtEntrynumber.setColumns(10);

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 8");

            txtName = new JTextField();
            formPanel.add(txtName, "8, 8, fill, default");
            txtName.setColumns(10);

            JLabel lblCategory = new JLabel("Category");
            formPanel.add(lblCategory, "4, 12, default, top");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 12, fill, default");

            specPanelHolder = new JPanel();
            specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            formPanel.add(specPanelHolder, "4, 14, 17, 1, fill, fill");

            JLabel lblPartsNumber = new JLabel("Parts Number");
            formPanel.add(lblPartsNumber, "4, 16");

            txtPartsnumber = new JTextField();
            formPanel.add(txtPartsnumber, "8, 16, fill, default");
            txtPartsnumber.setColumns(10);

            JLabel lblQuantity = new JLabel("Quantity");
            formPanel.add(lblQuantity, "12, 16");

            txtQuantity = new NumberTextField(6, true);

            txtQuantity.addKeyListener(priceCalcListener);
            formPanel.add(txtQuantity, "16, 16, fill, default");

            JLabel lblSerialNumber = new JLabel("Serial Number");
            formPanel.add(lblSerialNumber, "4, 18");

            txtSerialnumber = new JTextField();
            formPanel.add(txtSerialnumber, "8, 18, fill, default");
            txtSerialnumber.setColumns(10);

            JLabel lblUnit = new JLabel("Unit");
            formPanel.add(lblUnit, "12, 18");

            cmbUnitcombo = new DataComboBox();
            formPanel.add(cmbUnitcombo, "16, 18, fill, default");

            JLabel lblRacknumber = new JLabel("Rack Number");
            formPanel.add(lblRacknumber, "4, 20");

            txtRacknumber = new JTextField();
            formPanel.add(txtRacknumber, "8, 20, fill, default");
            txtRacknumber.setColumns(10);

            JLabel lblRate = new JLabel("Rate");
            formPanel.add(lblRate, "12, 20");

            txtRate = new NumberTextField(true);
            txtRate.setDecimalPlace(2);
            txtRate.addKeyListener(priceCalcListener);
            formPanel.add(txtRate, "16, 20, fill, default");
            txtRate.setDecimalPlace(2);

            JLabel lblPurchaseDate = new JLabel("Purchase Date");
            formPanel.add(lblPurchaseDate, "4, 22");

            txtPurDate = new JDateChooser();
            // txtPurDate.setDate(new Date());
            txtPurDate.setEnabled(false);
            formPanel.add(txtPurDate, "8, 22, fill, default");

            JLabel lblTotal = new JLabel("Total");
            formPanel.add(lblTotal, "12, 22");

            txtTotal = new JTextField();
            txtTotal.setEditable(false);
            txtTotal.setFocusable(false);
            // txtTotal.setEnabled(false);
            formPanel.add(txtTotal, "16, 22, fill, default");
            txtTotal.setColumns(10);

            JLabel lblPhoneNumber = new JLabel("Vendor");
            formPanel.add(lblPhoneNumber, "4, 24");

            cmbVendor = new DataComboBox();
            formPanel.add(cmbVendor, "8, 24, fill, default");

            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                SwingWorker worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        handleSaveAction();
                        return null;
                    }

                };
                worker.addPropertyChangeListener(evt -> {
                    if ("DONE".equals(evt.getNewValue().toString())) {
                        btnSave.setEnabled(true);
                    }
                });

                worker.execute();
            });
            formPanel.add(btnSave, "18, 24, fill, default");
        }

        return formPanel;
    }

    private void readAndShowAll(boolean showSize0Error) {
        List<Item> brsL = ItemServiceImpl.getAddedItems();
        editingPrimaryId = -1;
        if (brsL == null || brsL.size() == 0) {
            if (showSize0Error) {
                JOptionPane.showMessageDialog(null, "No Records Found");
            }
        }
        showListInGrid(brsL);

    }

    private String getPrice() {
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
        BigDecimal amt = rate.multiply(qty);
        return amt + "";
    }

    private void showListInGrid(List<Item> brsL) {
        dataModel.resetModel();
        int sn = 0;
        for (Item bo : brsL) {
            BigDecimal total = bo.getRate().multiply(new BigDecimal(bo.getQuantity()));

            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getPurchaseOrderNo(), bo.getName(),
                    bo.getCategory().getCategoryName(), bo.getSpeciifcationString(), bo.getPartsNumber(), bo.getSerialNumber(),
                    bo.getRackNo(), DateTimeUtils.getCvDateMMMddyyyy(bo.getPurchaseDate()), DateTimeUtils.getCvDateMMMddyyyy(bo.getAddedDate()),
                    bo.getVendor().getName(), bo.getOriginalQuantity(), bo.getQuantity(), bo.getRate(), bo.getUnitsString().getValue(), total});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

    @Override
    public final String getFunctionName() {
        return "New Item Entry";
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
                    /*
                      if second column doesnot have primary id info, then
                     */
                    int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
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
                currentSpecificationPanel.populateValues(bro.getSpecification());
            }
        } catch (Exception e) {
            System.out.println("populateSelectedRowInForm");
            handleDBError(e);
        }
    }

}
