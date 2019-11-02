package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Transfer;
import com.ca.db.service.DBUtils;
import com.ca.db.service.TransferServiceImpl;
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

public class TransferQueryPanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Category", "Specification", "Transfer Date", "Transfer Type", "Sent To", "Transfer Book Num",
            "Request Number", "Transfer Quantity", "Remaining Quantity to Return", "Units"};
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
    // SpecificationPanel currentSpecificationPanel;
    // private JPanel specPanelHolder;
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
    private JLabel lblReceiver;
    private ItemReceiverPanel itemReceiverPanel;
    private JTextField txtItemRequestNumber;
    private JPanel returnStatusPanel;
    private JRadioButton rdbtnReturned;
    private JRadioButton rdbtnNotReturned;
    private JRadioButton rdbtnAll;

    private JLabel lblReturnedStatus;
    private JButton btnReset;
    private JLabel lblHastantaranStatus;
    private JPanel hastantaranStatus;
    private JRadioButton rdbtnHastanAll;
    private JRadioButton rdbtnHastantaranreceived;
    private JRadioButton rdbtnHastanNotReceived;
    private JButton btnEditcorrect;
    private int editingPrimaryId = -1;

    public TransferQueryPanel() {
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
                TransferQueryPanel panel = new TransferQueryPanel();
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
            // List<Vendor> vl = DBUtils.readAll(Vendor.class);
            // for (Vendor v : vl) {
            // cmbVendor.addRow(new Object[] { v.getId(), v.getName(),
            // v.getAddress() });
            // }

        } catch (Exception e) {
            handleDBError(e);
        }
        // /* Item listener on cmbCategory - to change specification panel */
        // cmbCategory.addItemListener(new ItemListener() {
        //
        // public void itemStateChanged(ItemEvent e) {
        // int id = cmbCategory.getSelectedId();
        // specPanelHolder.removeAll();
        // currentSpecificationPanel = null;
        // if (id > 0) {
        // currentSpecificationPanel = new SpecificationPanel(id);
        // specPanelHolder.add(currentSpecificationPanel, FlowLayout.LEFT);
        // // if (status == Status.CREATE || status == Status.MODIFY)
        // // currentSpecificationPanel.enableAll();
        // // else
        // // currentSpecificationPanel.disableAll();
        // }
        // specPanelHolder.repaint();
        // specPanelHolder.revalidate();
        //
        // }
        // });

    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();

            btnSaveToExcel = new JButton("Save to Excel");
            btnSaveToExcel.addActionListener(e -> {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.showDialog(TransferQueryPanel.this, "Select Save location");
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

            GDialog cd = new GDialog(mainApp, "View Edit Delete - Transfer Information", true);
            CorrectionTransferPanel vp = new CorrectionTransferPanel(editingPrimaryId);
            cd.setAbstractFunctionPanel(vp, new Dimension(400, 370));
            cd.setResizable(false);
            init();
//			cd.addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowClosing(WindowEvent e) {
//					init();
//
//				}
//				@Override
//				public void windowLostFocus(WindowEvent e) {
//					// TODO Auto-generated method stub
//					super.windowLostFocus(e);
//					init();
//				}
//			});
        }
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

            formPanel.setBorder(new TitledBorder(null, "Transfer Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(128dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(26dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(125dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,},
                    new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,}));

            lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);
            //
            // specPanelHolder = new JPanel();
            // formPanel.add(specPanelHolder, "4, 6, 15, 1, fill, fill");
            // specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            lblPanaNumber = new JLabel("Transfer Number");
            formPanel.add(lblPanaNumber, "12, 2");

            txtPanaNumber = new JTextField();
            formPanel.add(txtPanaNumber, "16, 2, fill, default");
            txtPanaNumber.setColumns(10);

            JLabel lblN = new JLabel("Category");
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            lblVendor = new JLabel("Item Request No.");
            formPanel.add(lblVendor, "12, 4, default, top");

            txtItemRequestNumber = new JTextField();
            formPanel.add(txtItemRequestNumber, "16, 4, fill, default");
            txtItemRequestNumber.setColumns(10);

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

            lblHastantaranStatus = new JLabel("Hastantaran Status");
            formPanel.add(lblHastantaranStatus, "4, 8");

            hastantaranStatus = new JPanel();
            formPanel.add(hastantaranStatus, "8, 8, fill, fill");
            hastantaranStatus.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,}));

            rdbtnHastanAll = new JRadioButton("ALL");
            hastantaranStatus.add(rdbtnHastanAll, "4, 2");

            rdbtnHastantaranreceived = new JRadioButton("Received");
            hastantaranStatus.add(rdbtnHastantaranreceived, "4, 4");

            rdbtnHastanNotReceived = new JRadioButton("Not Received");
            hastantaranStatus.add(rdbtnHastanNotReceived, "4, 6");

            ButtonGroup bgHastan = new ButtonGroup();
            bgHastan.add(rdbtnHastanAll);
            bgHastan.add(rdbtnHastantaranreceived);
            bgHastan.add(rdbtnHastanNotReceived);

            lblReturnedStatus = new JLabel("Returned Status");
            formPanel.add(lblReturnedStatus, "12, 8, default, center");

            returnStatusPanel = new JPanel();
            formPanel.add(returnStatusPanel, "16, 8, fill, fill");
            returnStatusPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,}));

            ButtonGroup bgRet = new ButtonGroup();
            rdbtnAll = new JRadioButton("ALL");
            rdbtnAll.setSelected(true);
            returnStatusPanel.add(rdbtnAll, "4, 2");

            rdbtnReturned = new JRadioButton("Returned");
            returnStatusPanel.add(rdbtnReturned, "4, 4");

            rdbtnNotReturned = new JRadioButton("Not Returned");
            returnStatusPanel.add(rdbtnNotReturned, "4, 6");
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
                rdbtnHastanAll.setSelected(true);
                editingPrimaryId = -1;
            });
            formPanel.add(btnReset, "20, 8, default, bottom");

            lblReceiver = new JLabel("Receiver :");
            formPanel.add(lblReceiver, "4, 10, default, center");
            itemReceiverPanel = new ItemReceiverPanel();
            formPanel.add(itemReceiverPanel, "8, 10");
        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            TransferServiceImpl is = new TransferServiceImpl();
            List<Transfer> brsL;
            // FIXME : pananumber vs - request number ??
            int returnStatus = -1;
            if (rdbtnNotReturned.isSelected()) {
                returnStatus = Transfer.STATUS_NOT_RETURNED;
            } else if (rdbtnReturned.isSelected()) {
                returnStatus = Transfer.STATUS_RETURNED_ALL;
            }

            int hastantaranStatus = -1;
            if (rdbtnHastanNotReceived.isSelected()) {
                hastantaranStatus = Transfer.HASTANTARAN_NOT_RECEIVED;
            } else if (rdbtnHastantaranreceived.isSelected()) {
                hastantaranStatus = Transfer.HASTANTARAN_RECEIVED;
            }

            brsL = TransferServiceImpl.allTransferItemQuery(txtItemname.getText(), cmbCategory.getSelectedId(), itemReceiverPanel.getCurrentReceiverConstant(),
                    itemReceiverPanel.getSelectedId(), returnStatus, hastantaranStatus, txtPanaNumber.getText().trim(), "", txtFromDate.getDate(),
                    txtToDate.getDate());

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

    private void showListInGrid(List<Transfer> brsL) {
        dataModel.resetModel();
        int sn = 0;
        editingPrimaryId = -1;
        String transferTYpe = "";
        String sentTo = "";
        for (Transfer bo : brsL) {
            transferTYpe = "";
            sentTo = "";

            switch (bo.getTransferType()) {
                case Transfer.OFFICIAL:
                    transferTYpe = "Official";
                    sentTo = bo.getBranchOffice().getName() + "  " + bo.getBranchOffice().getAddress();
                    break;
                case Transfer.PERSONNAL:
                    transferTYpe = "Personnal";
                    sentTo = bo.getPerson().getFirstName() + "  " + bo.getPerson().getLastName();
                    break;
                case Transfer.LILAM:
                    transferTYpe = "Lilam";
                    sentTo = "Lilam";
                    break;
            }
            // TODO: add person/office name, specs in column
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getItem().getName(), bo.getItem().getCategory().getCategoryName(),
                    bo.getItem().getSpeciifcationString(), DateTimeUtils.getCvDateMMMddyyyy(bo.getTransferDate()), transferTYpe, sentTo,
                    bo.getTransferPanaNumber(), bo.getTransferRequestNumber(), bo.getQuantity(), bo.getRemainingQtyToReturn(),
                    bo.getItem().getUnitsString().getValue()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
    }

    @Override
    public final String getFunctionName() {
        return "Transfer Information Query";
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
            table.getSelectionModel().addListSelectionListener(e -> {
                int selRow = table.getSelectedRow();
                if (selRow != -1) {
                    /**
                     * if second column doesnot have primary id info, then
                     */
                    editingPrimaryId = (Integer) dataModel.getValueAt(selRow, 1);

                }
            });
        }
        return lowerPane;
    }

}
