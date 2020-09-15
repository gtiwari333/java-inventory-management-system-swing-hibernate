package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Transfer;
import com.ca.db.service.DBUtils;
import com.ca.db.service.ItemReturnServiceImpl;
import com.ca.db.service.TransferServiceImpl;
import com.ca.db.service.dto.ReturnedItemDTO;
import com.gt.common.constants.Status;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.ExcelUtils;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

/**
 * entry of returned items
 *
 * @author GT
 */
public class ItemReturnPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Category", "Specification", "Transfer Date", "Sent To",
            "Request Number", "Remaining Quantity", "Unit"};
    private final String[] returnTblHeader = new String[]{"", "ID", "Name", "Category", "Goods Status", "Return Quantity", "Unit"};
    private final String[] damageStatusStr = new String[]{"", "Good", "Unrepairable", "Needs Repair", "Exemption"};
    private final List<TableCellEditor> cellQtyEditors = new ArrayList<>();
    private final int qtyCol = 5;
    private final int damageStatusCol = 4;
    private ReturnTable returnTable;
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private JDateChooser txtFromDate;
    private JDateChooser txtToDate;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private EasyTableModel cartDataModel;
    private DataComboBox cmbCategory;
    private JLabel lblFrom;
    private JTextField txtItemname;

    /*
     * Some Inner classes
     */
    private ItemReceiverPanel itemReceiverPanel;
    private JPanel addToCartPanel;
    private JSplitPane lowerPanel;
    private JPanel cartPanel;
    private JDateChooser transferDateChooser;
    private JButton btnSend;
    private JTextField txtReturnNUmber;

    public ItemReturnPanel() {
        /*
          all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
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
                ItemReturnPanel panel = new ItemReturnPanel();
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
            lowerPanel.setResizeWeight(0.6);
            lowerPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);

            lowerPanel.setLeftComponent(getLowerSplitPane());

            JPanel panel_1 = new JPanel();
            lowerPanel.setRightComponent(panel_1);
            panel_1.setLayout(new BorderLayout(0, 0));

            cartPanel = new JPanel();
            cartPanel.setBorder(new TitledBorder(null, "Item Return Entry", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel_1.add(cartPanel, BorderLayout.CENTER);
            cartPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(45dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(27dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(15dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(24dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(9dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(124dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(59dlu;default)"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    RowSpec.decode("top:max(31dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(15dlu;default)"),}));

            JPanel panel_3 = new JPanel();
            cartPanel.add(panel_3, "2, 2, fill, fill");
            panel_3.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
                    FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JButton btnAddItem = new JButton("Add Item");
            panel_3.add(btnAddItem, "2, 2");

            JButton btnDelete = new JButton("Remove");
            btnDelete.addActionListener(e -> {
                if (returnTable.getRowCount() > 0) {
                    int selRow = returnTable.getSelectedRow();
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
                if (table.getRowCount() > 0) {
                    int selRow = table.getSelectedRow();
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

            JLabel lblNiksasaPanaNumber = new JLabel("Return Number");
            cartPanel.add(lblNiksasaPanaNumber, "4, 4, left, default");

            txtReturnNUmber = new JTextField();
            cartPanel.add(txtReturnNUmber, "6, 4, fill, default");
            txtReturnNUmber.setColumns(10);

            JLabel lblSentDate = new JLabel("Date");
            cartPanel.add(lblSentDate, "10, 4, default, top");

            transferDateChooser = new JDateChooser();
            transferDateChooser.setDate(new Date());
            cartPanel.add(transferDateChooser, "14, 4, fill, top");

            btnSend = new JButton("Receive");
            btnSend.addActionListener(e -> {

                if (!isValidCart()) {
                    JOptionPane.showMessageDialog(null, "Please fill the required data");
                    return;
                }

                btnSend.setEnabled(false);

                SwingWorker worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() {
                        if (DataEntryUtils.confirmDBSave()) saveReturn();
                        return null;
                    }

                };
                worker.addPropertyChangeListener(evt -> {
                    System.out.println("Event " + evt + " name" + evt.getPropertyName() + " value " + evt.getNewValue());
                    if ("DONE".equals(evt.getNewValue().toString())) {
                        btnSend.setEnabled(true);
                        // task.setText("Test");
                    }
                });

                worker.execute();
            });
            cartPanel.add(btnSend, "16, 4, default, top");
        }

        return lowerPanel;
    }

    private void saveReturn() {

        try {
            ItemReturnServiceImpl.saveReturnedItem(returnTable.getIdAndQuantityMap(), txtReturnNUmber.getText().trim());
            handleTransferSuccess();
        } catch (Exception er) {
            handleDBError(er);
        }

        btnSend.setEnabled(true);

    }

    private boolean isValidCart() {
        if (returnTable.isValidCartQty() && returnTable.getRowCount() > 0 && transferDateChooser.getDate() != null) {
            return true;
        }
        return false;
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
            cellQtyEditors.clear();
        });
    }

    private void removeSelectedRowInCartTable(int selectedId, int selRow) {
        cartDataModel.removeRowWithKey(selectedId);
        cartDataModel.fireTableDataChanged();
        cellQtyEditors.remove(selRow);
    }

    private void addSelectedRowInCartTable(int selectedId) {
        try {
            Transfer bo = (Transfer) DBUtils.getById(Transfer.class, selectedId);
            int sn = cartDataModel.getRowCount();
            if (bo != null) {
                cartDataModel.addRow(new Object[]{++sn, bo.getId(), bo.getItem().getName(), bo.getItem().getCategory().getCategoryName(), "", 0,
                        bo.getItem().getUnitsString().getValue()});
                returnTable.setModel(cartDataModel);
                cartDataModel.fireTableDataChanged();

                cellQtyEditors.add(new CartTableQuantityCellEditor(bo.getRemainingQtyToReturn()));
                JComboBox comboBox = new JComboBox(damageStatusStr);
                comboBox.setEditable(false);
                DefaultCellEditor editor = new DefaultCellEditor(comboBox);
                TableColumnModel tcm = returnTable.getColumnModel();
                tcm.getColumn(4).setCellEditor(editor);
            }

        } catch (Exception e) {
            System.out.println("populateSelectedRowInForm");
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
            ItemReturnServiceImpl is = new ItemReturnServiceImpl();
            List<Category> cl = ItemReturnServiceImpl.getNonReturnableCategory();
            for (Category c : cl) {
                cmbCategory.addRow(new Object[]{c.getId(), c.getCategoryName()});
            }
        } catch (Exception e) {
            handleDBError(e);
        }
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();

            JButton btnSaveToExcel = new JButton("Save to Excel");
            btnSaveToExcel.addActionListener(e -> {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.showDialog(ItemReturnPanel.this, "Select Save location");
                String fileName = jf.getSelectedFile().getAbsolutePath();
                try {
                    ExcelUtils.writeExcelFromJTable(table, fileName + ".xls");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Could not save" + e1.getMessage());
                }
            });

            JButton btnPrev = new JButton("<");
            buttonPanel.add(btnPrev);

            JButton btnNext = new JButton(">");
            buttonPanel.add(btnNext);
            buttonPanel.add(btnSaveToExcel);
        }
        return buttonPanel;
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.clearAllFields(formPanel);
                table.setEnabled(true);
                btnSave.setEnabled(true);
                break;

            case READ:
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

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Transfer Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(128dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(26dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(125dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);

            JLabel lblN = new JLabel("Category");
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            JLabel lblVendor = new JLabel("Item Request No.");
            formPanel.add(lblVendor, "12, 4, default, top");

            JTextField txtItemRequestNumber = new JTextField();
            formPanel.add(txtItemRequestNumber, "16, 4, fill, default");
            txtItemRequestNumber.setColumns(10);

            lblFrom = new JLabel("From");
            formPanel.add(lblFrom, "4, 6");

            txtFromDate = new JDateChooser();
            formPanel.add(txtFromDate, "8, 6, fill, default");

            JLabel lblTo = new JLabel("To");
            formPanel.add(lblTo, "12, 6");

            txtToDate = new JDateChooser();
            txtToDate.setDate(new Date());
            formPanel.add(txtToDate, "16, 6, fill, default");

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> handleSearchQuery());

            JLabel lblReceiver = new JLabel("Receiver :");
            formPanel.add(lblReceiver, "4, 8, default, center");

            JPanel receiverHolder = new JPanel();
            itemReceiverPanel = new ItemReceiverPanel();
            receiverHolder.add(itemReceiverPanel);
            formPanel.add(receiverHolder, "8, 8, fill, fill");

            formPanel.add(btnSave, "18, 8, default, bottom");

            JButton btnReset = new JButton("Reset");
            formPanel.add(btnReset, "20, 8, default, bottom");
            btnReset.addActionListener(e -> {
                UIUtils.clearAllFields(formPanel);
                cmbCategory.selectDefaultItem();
                itemReceiverPanel.clearAll();
            });
        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            List<Transfer> brsL;
            int returnStatus = -1;

            brsL = TransferServiceImpl.notReturnedTransferItemQuery(txtItemname.getText(), cmbCategory.getSelectedId(),
                    itemReceiverPanel.getSelectedId(), returnStatus, -1, "", txtFromDate.getDate(),
                    txtToDate.getDate());

            if (brsL == null || brsL.size() == 0) {
                JOptionPane.showMessageDialog(null, "No Records Found");
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

    private void showListInGrid(List<Transfer> brsL) {
        dataModel.resetModel();
        int sn = 0;
        String transferTYpe;
        String sentTo;
        for (Transfer bo : brsL) {
            transferTYpe = "Official";
            sentTo = bo.getBranchOffice().getName() + "  " + bo.getBranchOffice().getAddress();

            // TODO: add person/office name, specs in column
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getItem().getName(), bo.getItem().getCategory().getCategoryName(),
                    bo.getItem().getSpeciifcationString(), DateTimeUtils.getCvDateMMMddyyyy(bo.getTransferDate()), transferTYpe, sentTo,
                    "bo.getTransferPanaNumber()", bo.getTransferRequestNumber(), bo.getRemainingQtyToReturn(), bo.getItem().getUnitsString().getValue()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
    }

    @Override
    public final String getFunctionName() {
        return "Item Return Entry";
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
        }
        return lowerPane;
    }

    private JPanel getAddToCartPane() {
        if (addToCartPanel == null) {
            addToCartPanel = new JPanel();
            addToCartPanel.setLayout(new BorderLayout());
            cartDataModel = new EasyTableModel(returnTblHeader);

            returnTable = new ReturnTable(cartDataModel);
            returnTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            returnTable.setRowSorter(null);

            JScrollPane sp = new JScrollPane(returnTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
                    JOptionPane.showMessageDialog(null, "The maximum qty remaining to return is " + maxQuantity, "Max Qty Exceed",
                            JOptionPane.INFORMATION_MESSAGE);
                    retQty = 0;
                }

            } catch (Exception e) {
                retQty = 0;
            }
            return retQty <= 0 ? "0" : retQty;
        }
    }

    class ReturnTable extends BetterJTableNoSorting {

        /**
         * ID at sec col, Qty at 6th
         */

        ReturnTable(TableModel dm) {
            super(dm);
        }

        public final boolean isCellEditable(int row, int column) {
            return column == qtyCol || column == damageStatusCol;
        }

        /**
         * if at lease 1 item has greater than 0 qty, others will be ignored
         * during saving
         *
         * @return
         */
        final boolean isValidCartQty() {
            Map<Integer, ReturnedItemDTO> cartMap = returnTable.getIdAndQuantityMap();
            for (Entry<Integer, ReturnedItemDTO> entry : cartMap.entrySet()) {
                ReturnedItemDTO ret = entry.getValue();
                int qty = ret.qty;
                int damageStatus = ret.damageStatus;
                if (qty > 0 && damageStatus > 0) {
                    return true;
                }
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

        final Map<Integer, ReturnedItemDTO> getIdAndQuantityMap() {
            Map<Integer, ReturnedItemDTO> cartIdQtyMap = new HashMap<>();
            int rows = getRowCount();
            for (int i = 0; i < rows; i++) {
                int idCol = 1;
                Integer id = Integer.parseInt(getValueAt(i, idCol).toString());
                int qty = Integer.parseInt(getValueAt(i, qtyCol).toString());
                int damageStatus = getDamageStatusIndex(getValueAt(i, damageStatusCol).toString());

                /*
                  Put the items that have qty >0 only
                 */
                if (qty > 0) {
                    cartIdQtyMap.put(id, new ReturnedItemDTO(qty, damageStatus, ""));
                }
            }
            return cartIdQtyMap;
        }

        // Determine editor to be used by row
        public final TableCellEditor getCellEditor(int row, int column) {
            if (column == qtyCol) {
                return cellQtyEditors.get(row);
            } else
                return super.getCellEditor(row, column);
        }

    }
}
