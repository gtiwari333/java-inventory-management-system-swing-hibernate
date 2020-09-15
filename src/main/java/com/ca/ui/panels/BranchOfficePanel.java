package com.ca.ui.panels;

import com.ca.db.model.BranchOffice;
import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.GTextArea;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class BranchOfficePanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Address", "District", "PhoneNumber"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JTextField nameFLD;
    private JTextField phoneNumberFLD;
    private GTextArea addressFLD;
    private JButton btnReadAll;
    private JButton btnNew;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private int editingPrimaryId = 0;
    private JButton btnCancel;
    private JTextField txtDistrict;

    public BranchOfficePanel() {
        /*
          all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.2);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
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
                BranchOfficePanel panel = new BranchOfficePanel();
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
                if (editingPrimaryId > 0)
                    handleDeleteAction();
            });

            JButton btnModify = new JButton("Modify");
            btnModify.addActionListener(e -> {
                if (editingPrimaryId > 0)
                    changeStatus(Status.MODIFY);
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
            deleteSelectedBranchOffice();
        }

    }

    private void deleteSelectedBranchOffice() {
        try {
            DBUtils.deleteById(BranchOffice.class, editingPrimaryId);
            changeStatus(Status.READ);
            JOptionPane.showMessageDialog(null, "Deleted");
            readAndShowAll(false);
        } catch (Exception e) {
            handleDBError(e);
        }
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.clearAllFields(formPanel);
                btnReadAll.setEnabled(true);
                btnNew.setEnabled(true);
                table.setEnabled(true);
                break;
            case CREATE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, true);
                table.setEnabled(false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                break;
            case MODIFY:
                UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(buttonPanel, false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                table.setEnabled(false);
                break;

            case READ:
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.clearAllFields(formPanel);
                table.clearSelection();
                table.setEnabled(true);
                editingPrimaryId = -1;
                btnCancel.setEnabled(false);
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
        v.addTask(nameFLD, "Req", null, true);
    }

    private BranchOffice getModelFromForm() {
        BranchOffice bo = new BranchOffice();
        bo.setName(nameFLD.getText().trim());
        bo.setAddress(addressFLD.getText().trim());
        bo.setPhoneNumber(phoneNumberFLD.getText().trim());
        bo.setDistrict(txtDistrict.getText().trim());
        bo.setdFlag(1);
        return bo;
    }

    private void setModelIntoForm(BranchOffice bro) {
        nameFLD.setText(bro.getName());
        addressFLD.setText(bro.getAddress());
        phoneNumberFLD.setText(bro.getPhoneNumber());
        txtDistrict.setText(bro.getDistrict());
    }

    private void save(boolean isModified) {
        initValidator();
        if (v.validate()) {
            try {

                BranchOffice newBo = getModelFromForm();
                if (isModified) {
                    BranchOffice bo = (BranchOffice) DBUtils.getById(BranchOffice.class, editingPrimaryId);
                    bo.setAddress(newBo.getAddress());
                    bo.setName(newBo.getName());
                    bo.setPhoneNumber(newBo.getPhoneNumber());
                    bo.setDistrict(newBo.getDistrict());
                    DBUtils.saveOrUpdate(bo);
                } else {
                    DBUtils.saveOrUpdate(newBo);
                }
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                changeStatus(Status.READ);
                UIUtils.clearAllFields(upperPane);
                readAndShowAll(false);
            } catch (Exception e) {
                handleDBError(e);
            }
        }
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Branch Office Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),},
                    new RowSpec[]{
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 2");

            nameFLD = new JTextField();
            formPanel.add(nameFLD, "8, 2, fill, default");
            nameFLD.setColumns(10);

            JLabel lblAddress = new JLabel("Address");
            formPanel.add(lblAddress, "4, 4, default, top");

            addressFLD = new GTextArea(5, 30);
            formPanel.add(addressFLD, "8, 4, fill, fill");

            JLabel lblDistrict = new JLabel("District");
            formPanel.add(lblDistrict, "4, 6");

            txtDistrict = new JTextField();
            formPanel.add(txtDistrict, "8, 6, fill, default");
            txtDistrict.setColumns(10);

            JLabel lblPhoneNumber = new JLabel("Phone Number");
            formPanel.add(lblPhoneNumber, "4, 8");

            phoneNumberFLD = new JTextField();
            formPanel.add(phoneNumberFLD, "8, 8, fill, default");
            phoneNumberFLD.setColumns(10);

            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                handleSaveAction();
                btnSave.setEnabled(true);
            });
            formPanel.add(btnSave, "10, 8");
        }
        return formPanel;
    }

    private void readAndShowAll(boolean showSize0Error) {
        try {
            List<BranchOffice> brsL = DBUtils.readAll(BranchOffice.class);
            editingPrimaryId = -1;
            if (brsL == null || brsL.size() == 0) {
                if (showSize0Error) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
            }
            showBranchOfficesInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void showBranchOfficesInGrid(List<BranchOffice> brsL) {
        dataModel.resetModel();
        int sn = 0;
        for (BranchOffice bo : brsL) {
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getAddress(), bo.getDistrict(), bo.getPhoneNumber()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

    @Override
    public final String getFunctionName() {
        return "Branch-Office Information";
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
            BranchOffice bro = (BranchOffice) DBUtils.getById(BranchOffice.class, selectedId);
            if (bro != null) {
                setModelIntoForm(bro);
                editingPrimaryId = bro.getId();
            }
        } catch (Exception e) {
            handleDBError(e);
        }

    }

}
