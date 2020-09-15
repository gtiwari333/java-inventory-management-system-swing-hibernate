package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.Vendor;
import com.ca.db.service.DBUtils;
import com.ca.db.service.ItemServiceImpl;
import com.ca.db.service.TransferServiceImpl;
import com.gt.common.constants.Status;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.BetterJTableNoSorting;
import com.gt.uilib.components.table.EasyTableModel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class ItemTransferPanel extends AbstractFunctionPanel {
    private final int qtyCol = 6;
    private final String[] header = new String[]{"", "ID", "Name", "Category", "Specification", "Parts Number", "Serial Number", "Rack Number",
            "Purchase date", "Added date", "Vendor", "Quantity", "Unit", "Rate"};
    private final String[] cartHeader = new String[]{"", "ID", "Name", "Category", "Specification", "Rack Number", "Quantity", "Unit"};
    private final List<TableCellEditor> cellEditors = new ArrayList<>();
    private JPanel formPanel = null;
    private SpecificationPanel currentSpecificationPanel;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable itemDetailTable;
    private CartTable cartTable;
    private EasyTableModel dataModel;
    private EasyTableModel cartDataModel;
    private DataComboBox cmbCategory;
    private DataComboBox cmbVendor;
    private JPanel specPanelHolder;
    private JTextField txtItemname;
    private JSplitPane lowerPanel;
    private JPanel cartPanel;
    private ItemReceiverPanel itemReceiverPanel;
    private JDateChooser transferDateChooser;
    private JButton btnSend;
    private JPanel addToCartPanel;
    private JTextField txtRequestnumber;
    private JDateChooser txtFromDate;
    private JDateChooser txtToDate;

    public ItemTransferPanel() {
        /*
          all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(false);
        splitPane.setResizeWeight(0.1);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerPanel());

        /*
          never forget to call after setting up UI
         */
        init();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                ItemTransferPanel panel = new ItemTransferPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private JSplitPane getLowerPanel() {
        if (lowerPanel == null) {
            lowerPanel = new JSplitPane();
            lowerPanel.setContinuousLayout(true);
            lowerPanel.setResizeWeight(0.5);
            lowerPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);

            lowerPanel.setLeftComponent(getLowerSplitPane());

            JPanel panel_1 = new JPanel();
            lowerPanel.setRightComponent(panel_1);
            panel_1.setLayout(new BorderLayout(0, 0));

            cartPanel = new JPanel();
            cartPanel.setBorder(new TitledBorder(null, "Transfer Entry - choose item from Inventory, click 'Add Item' and enter quantity by double clicking the cell", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel_1.add(cartPanel, BorderLayout.CENTER);
            cartPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(45dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(27dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(15dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(24dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(9dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(124dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(59dlu;default)"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    RowSpec.decode("top:max(31dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(23dlu;default)"),
                    FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JPanel panel_3 = new JPanel();
            cartPanel.add(panel_3, "2, 2, fill, fill");
            panel_3.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
                    FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JButton btnAddItem = new JButton("Add Item");
            panel_3.add(btnAddItem, "2, 2");

            JButton btnDelete = new JButton("Remove");
            btnDelete.addActionListener(e -> {
                if (cartTable.getRowCount() > 0) {
                    int selRow = cartTable.getSelectedRow();
                    if (selRow != -1) {
                        /*
                          if second column doesnot have primary id info,
                          then
                         */

                        int selectedId = cartDataModel.getKeyAtRow(selRow);
                        System.out.println("Selected ID : " + selectedId + "_  >>  row " + selRow);
                        if (cartDataModel.containsKey(selectedId)) {
                            removeSelectedRowInCartTable(selectedId, selRow);
                        }

                    }
                }
            });
            panel_3.add(btnDelete, "2, 4");
            btnAddItem.addActionListener(e -> {
                if (itemDetailTable.getRowCount() > 0) {
                    int selRow = itemDetailTable.getSelectedRow();
                    if (selRow != -1) {
                        /*
                          if second column doesnot have primary id info,
                          then
                         */

                        int selectedId = dataModel.getKeyAtRow(selRow);

                        if (!cartDataModel.containsKey(selectedId)) {
                            addSelectedRowInCartTable(selectedId);
                        } else {
                            JOptionPane.showMessageDialog(null, "This Item Already Selected", "Duplicate Selection", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            cartPanel.add(getAddToCartPane(), "4, 2, 13, 1, fill, top");

            JLabel lblReceiver = new JLabel("Receiver Office:");
            cartPanel.add(lblReceiver, "4, 4");

            itemReceiverPanel = new ItemReceiverPanel();
            cartPanel.add(itemReceiverPanel, "6, 4, fill, top");

            JLabel lblSentDate = new JLabel("Date");
            cartPanel.add(lblSentDate, "10, 4, default, bottom");

            transferDateChooser = new JDateChooser();
            transferDateChooser.setDate(new Date());
            cartPanel.add(transferDateChooser, "14, 4, fill, bottom");

            btnSend = new JButton("Transfer");
            btnSend.addActionListener(e -> {

                if (!isValidCart()) {
                    JOptionPane.showMessageDialog(null, "Please fill the required data");
                    return;
                }
                btnSend.setEnabled(false);
                SwingWorker worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() {
                        if (DataEntryUtils.confirmDBSave()) saveTransfer();
                        return null;
                    }

                };
                worker.addPropertyChangeListener(evt -> {
                    System.out.println("Event " + evt + " name" + evt.getPropertyName() + " value " + evt.getNewValue());
                    if ("DONE".equals(evt.getNewValue().toString())) {
                        btnSend.setEnabled(true);
                    }
                });

                worker.execute();

            });
            cartPanel.add(btnSend, "16, 4, default, bottom");

            JLabel lblRequestNumber = new JLabel("Request Number");
            cartPanel.add(lblRequestNumber, "4, 8, left, default");

            txtRequestnumber = new JTextField();
            cartPanel.add(txtRequestnumber, "6, 8, fill, default");
            txtRequestnumber.setColumns(10);
        }

        return lowerPanel;
    }

    private void saveTransfer() {
        try {
            TransferServiceImpl.saveTransfer(cartTable.getIdAndQuantityMap(), transferDateChooser.getDate(), itemReceiverPanel.getCurrentType(),
                    itemReceiverPanel.getSelectedId(), txtRequestnumber.getText().trim());

            handleTransferSuccess();
        } catch (Exception er) {
            handleDBError(er);
        }
    }

    private void removeSelectedRowInCartTable(int selectedId, int selRow) {
        cartDataModel.removeRowWithKey(selectedId);
        cartDataModel.fireTableDataChanged();
        // TODO:
        cellEditors.remove(selRow);
    }

    private void handleTransferSuccess() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Saved Successfully");
            cartDataModel.resetModel();
            cartDataModel.fireTableDataChanged();
            UIUtils.clearAllFields(cartPanel);
            itemReceiverPanel.clearAll();
            dataModel.resetModel();
            dataModel.fireTableDataChanged();
            cellEditors.clear();

        });

    }

    private void addSelectedRowInCartTable(int selectedId) {
        try {
            Item bo = (Item) DBUtils.getById(Item.class, selectedId);
            System.out.println("Adding to cart id = " + selectedId + ">>" + bo.getQuantity() + " >> org " + bo.getOriginalQuantity());
            int sn = cartDataModel.getRowCount();

            cartDataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getCategory().getCategoryName(), bo.getSpeciifcationString(),
                    bo.getRackNo(), 0, bo.getUnitsString().getValue()});
            cartTable.setModel(cartDataModel);
            cartDataModel.fireTableDataChanged();
            cellEditors.add(new CartTableQuantityCellEditor(bo.getQuantity()));

        } catch (Exception e) {
            System.out.println("populateSelectedRowInForm" + e.getMessage());
            handleDBError(e);
        }
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
            }
            specPanelHolder.repaint();
            specPanelHolder.revalidate();

        });

    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.clearAllFields(formPanel);
                itemDetailTable.setEnabled(true);
                btnSave.setEnabled(true);
                break;

            case READ:
                UIUtils.clearAllFields(formPanel);
                itemDetailTable.clearSelection();
                itemDetailTable.setEnabled(true);
                break;

            default:
                break;
        }
    }

    @Override
    public void handleSaveAction() {

    }

    private boolean isValidCart() {
        if (cartTable.isValidCartQty() && cartTable.getRowCount() > 0 && itemReceiverPanel.isSelected() && transferDateChooser.getDate() != null) {
            return true;
        }
        return false;
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Inventory to select product and quantity to transfer", TitledBorder.LEADING,
                    TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(115dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(125dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    RowSpec.decode("max(51dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);

            JLabel lblN = new JLabel("Category");
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            JLabel lblVendor = new JLabel("Vendor");
            formPanel.add(lblVendor, "12, 4");

            cmbVendor = new DataComboBox();
            formPanel.add(cmbVendor, "16, 4, fill, default");

            specPanelHolder = new JPanel();
            formPanel.add(specPanelHolder, "4, 6, 19, 1, fill, fill");
            specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> handleSearchQuery());

            formPanel.add(btnSave, "18, 8, left, default");

            JButton btnReset = new JButton("Reset");
            btnReset.addActionListener(e -> {
                UIUtils.clearAllFields(formPanel);
                if (currentSpecificationPanel != null) currentSpecificationPanel.resetAll();
                cmbCategory.selectDefaultItem();
                cmbVendor.selectDefaultItem();
            });
            formPanel.add(btnReset, "20, 8");

            JLabel lblFrom = new JLabel("From");
            formPanel.add(lblFrom, "4, 10");

            txtFromDate = new JDateChooser();
            formPanel.add(txtFromDate, "8, 10, fill, default");

            JLabel lblTo = new JLabel("To");
            formPanel.add(lblTo, "12, 10");

            txtToDate = new JDateChooser();
            formPanel.add(txtToDate, "16, 10, fill, default");

        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            List<Item> brsL;
            List<String> specs;
            if (currentSpecificationPanel == null) {
                specs = new LinkedList<>();
            } else {
                specs = currentSpecificationPanel.getSpecificationsStringList();
            }
            brsL = ItemServiceImpl.itemStockQuery(txtItemname.getText().trim(), cmbCategory.getSelectedId(), cmbVendor.getSelectedId(), null, txtFromDate.getDate(), txtToDate.getDate(),
                    specs);

            if (brsL == null || brsL.size() == 0) {
                JOptionPane.showMessageDialog(null, "No Records Found");
                dataModel.resetModel();
                dataModel.fireTableDataChanged();
                itemDetailTable.adjustColumns();
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

            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getCategory().getCategoryName(),
                    bo.getSpeciifcationString(), bo.getPartsNumber(), bo.getSerialNumber(), bo.getRackNo(),
                    DateTimeUtils.getCvDateMMMddyyyy(bo.getPurchaseDate()), DateTimeUtils.getCvDateMMMddyyyy(bo.getAddedDate()),
                    bo.getVendor().getName(), bo.getQuantity(), bo.getUnitsString().getValue(), bo.getRate()});
        }
        SwingUtilities.invokeLater(() -> {
            itemDetailTable.setModel(dataModel);
            dataModel.fireTableDataChanged();
            itemDetailTable.adjustColumns();
        });
    }

    @Override
    public final String getFunctionName() {
        return "Item Transfer Entry";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            GridBagLayout gbl_upperPane = new GridBagLayout();
            gbl_upperPane.columnWidths = new int[]{728, 0};
            gbl_upperPane.rowHeights = new int[]{194, 0};
            gbl_upperPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gbl_upperPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
            upperPane.setLayout(gbl_upperPane);
            GridBagConstraints gbc_formPanel = new GridBagConstraints();
            gbc_formPanel.anchor = GridBagConstraints.NORTH;
            gbc_formPanel.fill = GridBagConstraints.HORIZONTAL;
            gbc_formPanel.gridx = 0;
            gbc_formPanel.gridy = 0;
            upperPane.add(getUpperFormPanel(), gbc_formPanel);
        }
        return upperPane;
    }

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setLayout(new BorderLayout());
            dataModel = new EasyTableModel(header);

            itemDetailTable = new BetterJTable(dataModel);
            itemDetailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(itemDetailTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            lowerPane.add(sp, BorderLayout.CENTER);
        }
        return lowerPane;
    }

    private JPanel getAddToCartPane() {
        if (addToCartPanel == null) {
            addToCartPanel = new JPanel();
            addToCartPanel.setLayout(new BorderLayout());
            cartDataModel = new EasyTableModel(cartHeader);

            cartTable = new CartTable(cartDataModel);
            cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cartTable.setRowSorter(null);

            JScrollPane sp = new JScrollPane(cartTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            // TODO: number of rows into scrl pane
            addToCartPanel.add(sp, BorderLayout.CENTER);
        }
        return addToCartPanel;
    }

    static class CartTableQuantityCellEditor extends AbstractCellEditor implements TableCellEditor {
        final JComponent component = new JTextField();
        int maxQuantity;

        CartTableQuantityCellEditor(int maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

        public final Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
            // Configure the component with the specified value
            ((JTextField) component).setText(value.toString());

            // Return the configured component
            return component;
        }

        /**
         * This method is called when editing is completed.<br>
         * It must return the new value to be stored in the cell.
         */
        public final Object getCellEditorValue() {
            int retQty;
            try {
                retQty = Integer.parseInt(((JTextField) component).getText());
                // if max
                if (retQty > maxQuantity) {
                    JOptionPane.showMessageDialog(null, "The maximum qty in stock is " + maxQuantity, "Max Qty Exceed",
                            JOptionPane.INFORMATION_MESSAGE);
                    retQty = 0;
                }

            } catch (Exception e) {
                retQty = 0;
            }
            return retQty <= 0 ? "0" : retQty;
        }
    }

    class CartTable extends BetterJTableNoSorting {

        CartTable(TableModel dm) {
            super(dm);
        }

        public final boolean isCellEditable(int row, int column) {
            return column == qtyCol;
        }

        /**
         * if at lease 1 item has greater than 0 qty, others will be ignored
         * during saving
         *
         * @return
         */
        final boolean isValidCartQty() {
            Map<Integer, Integer> cartMap = cartTable.getIdAndQuantityMap();
            for (Entry<Integer, Integer> entry : cartMap.entrySet()) {
                int qty = entry.getValue();

                if (qty > 0) {
                    return true;
                }
            }
            return false;

        }

        final Map<Integer, Integer> getIdAndQuantityMap() {
            Map<Integer, Integer> cartIdQtyMap = new HashMap<>();
            int rows = getRowCount();
            for (int i = 0; i < rows; i++) {
                int idCol = 1;
                Integer id = Integer.parseInt(getValueAt(i, idCol).toString());
                int qty = Integer.parseInt(getValueAt(i, qtyCol).toString());
                /*
                  Put the items that have qty >0 only
                 */
                if (qty > 0) {
                    cartIdQtyMap.put(id, qty);
                }
            }
            return cartIdQtyMap;
        }

        // Determine editor to be used by row
        public final TableCellEditor getCellEditor(int row, int column) {
            if (column == qtyCol) {
                return cellEditors.get(row);
            } else
                return super.getCellEditor(row, column);
        }

    }
}
