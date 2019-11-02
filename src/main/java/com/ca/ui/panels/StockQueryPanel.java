package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.Vendor;
import com.ca.db.service.DBUtils;
import com.ca.db.service.ItemServiceImpl;
import com.gt.common.constants.Status;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.ExcelUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.DataComboBox;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StockQueryPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Category", "Specification", "Khata No.", "Dakhila No.", "Parts Number", "Serial Number", "Pana Number", "Rack Number", "Purchase date", "Vendor", "Added Type",
            "Remaining Quantity", "Unit", "Rate",};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JDateChooser txtFromDate;
    private JDateChooser txtToDate;
    private SpecificationPanel currentSpecificationPanel;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private DataComboBox cmbCategory;
    private DataComboBox cmbVendor;
    private JPanel specPanelHolder;
    private JLabel lblVendor;
    private JLabel lblPanaNumber;
    private JTextField txtPanaNumber;
    private JLabel lblFrom;
    private JLabel lblTo;
    private JButton btnSaveToExcel;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel lblItemName;
    private JTextField txtItemname;
    private JLabel lblRackNumber;
    private JTextField txtRacknumber;
    private JButton btnReset;
    private JLabel lblKhataNumber;
    private JTextField txtKhataNumber;
    private JLabel lblDakhilaNumber;
    private JTextField txtDakhilanumber;

    public StockQueryPanel() {
        /**
         * all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.1);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        /**
         * never forget to call after setting up UI
         */
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
                StockQueryPanel panel = new StockQueryPanel();
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
        /* Item listener on cmbCategory - to change specification panel */
        cmbCategory.addItemListener(e -> {
            int id = cmbCategory.getSelectedId();
            specPanelHolder.removeAll();
            currentSpecificationPanel = null;
            if (id > 0) {
                currentSpecificationPanel = new SpecificationPanel(id);
                specPanelHolder.add(currentSpecificationPanel, FlowLayout.LEFT);
                // if (status == Status.CREATE || status == Status.MODIFY)
                // currentSpecificationPanel.enableAll();
                // else
                // currentSpecificationPanel.disableAll();
            }
            specPanelHolder.repaint();
            specPanelHolder.revalidate();

        });

    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();

            btnSaveToExcel = new JButton("Save to Excel");
            btnSaveToExcel.addActionListener(e -> {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.showDialog(StockQueryPanel.this, "Select Save location");
                String fileName = jf.getSelectedFile().getAbsolutePath();
                try {
                    ExcelUtils.writeExcelFromJTable(table, fileName + ".xls");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Could not save" + e1.getMessage());
                }
            });

            btnPrev = new JButton("<");
            buttonPanel.add(btnPrev);

            btnNext = new JButton(">");
            buttonPanel.add(btnNext);
            buttonPanel.add(btnSaveToExcel);
        }
        return buttonPanel;
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                // UIUtils.toggleAllChildren(buttonPanel, false);
                // UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.clearAllFields(formPanel);
                table.setEnabled(true);
                btnSave.setEnabled(true);
                break;

            case READ:
                // UIUtils.toggleAllChildren(formPanel, false);
                // UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.clearAllFields(formPanel);
                table.clearSelection();
                table.setEnabled(true);
                break;

            default:
                break;
        }
    }

    @Override
    public void handleSaveAction() {

    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }

        v = new Validator(mainApp, true);

    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Inventory Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(115dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(118dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("default:grow"),},
                    new RowSpec[]{
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            RowSpec.decode("max(51dlu;default)"),
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,}));

            lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);

            JLabel lblN = new JLabel("Category");
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            lblVendor = new JLabel("Vendor");
            formPanel.add(lblVendor, "12, 4");

            cmbVendor = new DataComboBox();
            formPanel.add(cmbVendor, "16, 4, fill, default");

            specPanelHolder = new JPanel();
            formPanel.add(specPanelHolder, "4, 6, 21, 1, fill, fill");
            specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            lblKhataNumber = new JLabel("Khata Number");
            formPanel.add(lblKhataNumber, "4, 8");

            txtKhataNumber = new JTextField();
            formPanel.add(txtKhataNumber, "8, 8, fill, default");
            txtKhataNumber.setColumns(10);

            lblDakhilaNumber = new JLabel("Dakhila Number");
            formPanel.add(lblDakhilaNumber, "12, 8");

            txtDakhilanumber = new JTextField();
            formPanel.add(txtDakhilanumber, "16, 8, fill, default");
            txtDakhilanumber.setColumns(10);

            lblPanaNumber = new JLabel("Pana Number");
            formPanel.add(lblPanaNumber, "4, 10");

            txtPanaNumber = new JTextField();
            formPanel.add(txtPanaNumber, "8, 10, left, default");
            txtPanaNumber.setColumns(10);

            lblRackNumber = new JLabel("Rack Number");
            formPanel.add(lblRackNumber, "12, 10");

            txtRacknumber = new JTextField();
            formPanel.add(txtRacknumber, "16, 10, fill, default");
            txtRacknumber.setColumns(10);

            lblFrom = new JLabel("From");
            formPanel.add(lblFrom, "4, 12");

            txtFromDate = new JDateChooser();
            formPanel.add(txtFromDate, "8, 12, fill, default");

            lblTo = new JLabel("To");
            formPanel.add(lblTo, "12, 12");

            txtToDate = new JDateChooser();
            txtToDate.setDate(new Date());
            formPanel.add(txtToDate, "16, 12, fill, default");

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> handleSearchQuery());

            formPanel.add(btnSave, "18, 12");

            btnReset = new JButton("Reset");
            btnReset.addActionListener(e -> {
                UIUtils.clearAllFields(formPanel);
                if (currentSpecificationPanel != null)
                    currentSpecificationPanel.resetAll();
                cmbCategory.selectDefaultItem();
                cmbVendor.selectDefaultItem();
//					itemReceiverPanel.clearAll();
            });
            formPanel.add(btnReset, "20, 12");
        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            ItemServiceImpl is = new ItemServiceImpl();
            List<Item> brsL;
            List<String> specs = null;
            if (currentSpecificationPanel == null) {
                specs = new LinkedList<>();
            } else {
                specs = currentSpecificationPanel.getSpecificationsStringList();
            }
            brsL = ItemServiceImpl.itemStockQuery(txtItemname.getText(), cmbCategory.getSelectedId(), cmbVendor.getSelectedId(), txtPanaNumber.getText().trim(), txtRacknumber.getText().trim(), txtKhataNumber.getText().trim(), txtDakhilanumber.getText().trim(),
                    txtFromDate.getDate(), txtToDate.getDate(), specs);

            if (brsL == null || brsL.size() == 0) {
                if (true) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
                dataModel.resetModel();
                dataModel.fireTableDataChanged();
                table.adjustColumns();
                return;
            }
            showListInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void showListInGrid(List<Item> brsL) {
        dataModel.resetModel();
        int sn = 0;
        for (Item bo : brsL) {
            // BigDecimal total = bo.getRate().multiply(new
            // BigDecimal(bo.getQuantity()));
            String addedType = "";
            if (bo.getAddedType() == Item.ADD_TYPE_NEW_ENTRY) {
                addedType = "Purchase Stock";
            } else {
                addedType = "Returned Stock";
            }
            //"Specification","Khata No.","Dakhila No.",
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getCategory().getCategoryName(), bo.getSpeciifcationString(), bo.getKhataNumber(), bo.getDakhilaNumber(), bo.getPartsNumber(),
                    bo.getSerialNumber(), bo.getPanaNumber(), bo.getRackNo(), DateTimeUtils.getCvDateMMMddyyyy(bo.getPurchaseDate()), bo.getVendor().getName(), addedType, bo.getQuantity(), bo.getUnitsString().getValue(),
                    bo.getRate()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
    }

    @Override
    public final String getFunctionName() {
        return "Stock Query";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.WEST);
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
        }
        return lowerPane;
    }

}
