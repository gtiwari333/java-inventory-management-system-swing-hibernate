package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.ItemReturn;
import com.ca.db.model.Transfer;
import com.ca.db.service.DBUtils;
import com.ca.db.service.ItemReturnServiceImpl;
import com.gt.common.constants.Status;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.ExcelUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.GDialog;
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
import java.util.List;

public class ReturnQueryPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Category", "Transfer Date", "Niksa Type", "Sent To", "Transfer Pana Num", "Request Number",
            "Return Number", "Transfer Quantity", "Remaining Quantity to Return", "Unit"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JDateChooser txtFromDate;
    private JDateChooser txtToDate;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private DataComboBox cmbCategory;
    private JLabel lblItemReturnNumber;
    private JLabel lblPanaNumber;
    private JTextField txtPanaNumber;
    private JLabel lblFrom;
    private JLabel lblTo;
    private JButton btnSaveToExcel;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel lblItemName;
    private JTextField txtItemname;
    private JPanel receiverHolder;
    private JLabel lblReceiver;
    private ItemReceiverPanel itemReceiverPanel;
    private JTextField txtItemReturnNumber;
    private JPanel panel_1;
    private JRadioButton rdbtnReturned;
    private JRadioButton rdbtnNotReturned;
    private JRadioButton rdbtnAll;
    private JLabel lblReturnedStatus;
    private JButton btnReset;
    private JButton btnEditcorrect;
    private int editingPrimaryId = -1;

    public ReturnQueryPanel() {
        /**
         * all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.05);
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
                ReturnQueryPanel panel = new ReturnQueryPanel();
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
        resetTableData();
    }

    private void intCombo() {
        try {
            /* Category Combo */
            cmbCategory.init();
            List<Category> cl = DBUtils.readAll(Category.class);
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

            btnSaveToExcel = new JButton("Save to Excel");
            btnSaveToExcel.addActionListener(e -> {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.showDialog(ReturnQueryPanel.this, "Select Save location");
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

            btnEditcorrect = new JButton("Edit/Correct");
            btnEditcorrect.addActionListener(e -> editCorrectHandle());
            buttonPanel.add(btnEditcorrect);
        }
        return buttonPanel;
    }

    private void editCorrectHandle() {

        if (editingPrimaryId > 0) {
            System.out.println("ReturnQueryPanel.editCorrectHandle() >> ");
            GDialog cd = new GDialog(mainApp, "View Edit Delete - Item Return Information", true);
            CorrectionItemReturnPanel vp = new CorrectionItemReturnPanel(editingPrimaryId);
            cd.setAbstractFunctionPanel(vp, new Dimension(400, 370));
            cd.setResizable(false);
            init();
//			cd.addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowClosing(WindowEvent e) {
//					init();
//
//				}
//			});
        }
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
                editingPrimaryId = -1;
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

            formPanel.setBorder(new TitledBorder(null, "Returned Item Query", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(128dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(26dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(125dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,}));

            lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);

            lblPanaNumber = new JLabel("Transfer Number");
            formPanel.add(lblPanaNumber, "12, 2");

            txtPanaNumber = new JTextField();
            formPanel.add(txtPanaNumber, "16, 2, fill, default");
            txtPanaNumber.setColumns(10);

            JLabel lblN = new JLabel("Category");
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            lblItemReturnNumber = new JLabel("Return Number");
            formPanel.add(lblItemReturnNumber, "12, 4, default, top");

            txtItemReturnNumber = new JTextField();
            formPanel.add(txtItemReturnNumber, "16, 4, fill, default");
            txtItemReturnNumber.setColumns(10);

            lblFrom = new JLabel("From");
            formPanel.add(lblFrom, "4, 6");

            txtFromDate = new JDateChooser();
            formPanel.add(txtFromDate, "8, 6, fill, default");

            lblTo = new JLabel("To");
            formPanel.add(lblTo, "12, 6");

            txtToDate = new JDateChooser();
            txtToDate.setDate(new Date());
            formPanel.add(txtToDate, "16, 6, fill, default");

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> handleSearchQuery());

            lblReceiver = new JLabel("Receiver :");
            formPanel.add(lblReceiver, "4, 8, default, center");

            receiverHolder = new JPanel();
            itemReceiverPanel = new ItemReceiverPanel();
            receiverHolder.add(itemReceiverPanel);
            formPanel.add(receiverHolder, "8, 8, fill, fill");

            lblReturnedStatus = new JLabel("Returned Status");
            formPanel.add(lblReturnedStatus, "12, 8, default, center");

            panel_1 = new JPanel();
            /**
             * HIDE return status in return query
             */
            lblReturnedStatus.setVisible(false);
            panel_1.setVisible(false);
            formPanel.add(panel_1, "16, 8, fill, fill");
            panel_1.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,}));

            ButtonGroup bgRet = new ButtonGroup();
            rdbtnAll = new JRadioButton("ALL");
            rdbtnAll.setSelected(true);
            panel_1.add(rdbtnAll, "4, 2");

            rdbtnReturned = new JRadioButton("Returned");
            panel_1.add(rdbtnReturned, "4, 4");

            rdbtnNotReturned = new JRadioButton("Not Returned");
            panel_1.add(rdbtnNotReturned, "4, 6");
            bgRet.add(rdbtnAll);
            bgRet.add(rdbtnNotReturned);
            bgRet.add(rdbtnReturned);

            formPanel.add(btnSave, "18, 8, default, bottom");

            btnReset = new JButton("Reset");
            btnReset.addActionListener(e -> {
                UIUtils.clearAllFields(formPanel);
                // if(currentSpecificationPanel!=null)
                // currentSpecificationPanel.resetAll();
                cmbCategory.selectDefaultItem();
                // cmbVendor.selectDefaultItem();
                itemReceiverPanel.clearAll();
                rdbtnAll.setSelected(true);
                editingPrimaryId = -1;

            });
            formPanel.add(btnReset, "20, 8, default, bottom");
        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            ItemReturnServiceImpl is = new ItemReturnServiceImpl();
            List<ItemReturn> brsL;

            int returnStatus = -1;

            if (rdbtnNotReturned.isSelected()) {
                returnStatus = Transfer.STATUS_NOT_RETURNED;
            } else if (rdbtnReturned.isSelected()) {
                returnStatus = Transfer.STATUS_RETURNED_ALL;
            }
            brsL = ItemReturnServiceImpl.itemReturnQuery(txtItemname.getText(), cmbCategory.getSelectedId(), itemReceiverPanel.getCurrentReceiverConstant(),
                    itemReceiverPanel.getSelectedId(), returnStatus, txtPanaNumber.getText().trim(), txtItemReturnNumber.getText().trim(),
                    txtFromDate.getDate(), txtToDate.getDate());

            if (brsL == null || brsL.size() == 0) {
                if (true) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
                resetTableData();
                return;
            }
            showListInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void resetTableData() {
        try {
            dataModel.resetModel();
            dataModel.fireTableDataChanged();
            table.adjustColumns();
        } catch (Exception ignored) {

        }
    }

    private void showListInGrid(List<ItemReturn> brsL) {
        dataModel.resetModel();
        int sn = 0;
        editingPrimaryId = -1;
        String transferTYpe = "";
        String sentTo = "";
        for (ItemReturn bo : brsL) {
            transferTYpe = "";
            sentTo = "";

            if (bo.getTransfer().getTransferType() == Transfer.OFFICIAL) {
                transferTYpe = "Official";
                sentTo = bo.getTransfer().getBranchOffice().getName() + "  " + bo.getTransfer().getBranchOffice().getAddress();
            } else if (bo.getTransfer().getTransferType() == Transfer.PERSONNAL) {
                transferTYpe = "Personnal";
                sentTo = bo.getTransfer().getPerson().getFirstName() + "  " + bo.getTransfer().getPerson().getLastName();
            }
            // TODO: add person/office name, specs in column
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getTransfer().getItem().getName(),
                    bo.getTransfer().getItem().getCategory().getCategoryName(), DateTimeUtils.getCvDateMMMddyyyy(bo.getAddedDate()), transferTYpe,
                    sentTo, bo.getTransfer().getTransferPanaNumber(), bo.getTransfer().getTransferRequestNumber(), bo.getReturnNumber(), bo.getQuantity(),
                    bo.getTransfer().getRemainingQtyToReturn(), bo.getTransfer().getItem().getUnitsString().getValue()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
    }

    @Override
    public final String getFunctionName() {
        return "Item Return Query";
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
                    editingPrimaryId = (Integer) dataModel.getValueAt(selRow, 1);
                    System.out.println("----------selecting" + editingPrimaryId);
                }
            });
        }
        return lowerPane;
    }

}
