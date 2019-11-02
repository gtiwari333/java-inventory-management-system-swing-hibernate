package com.ca.ui.panels;

import com.ca.db.model.BranchOffice;
import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

//TODO: under implementation, not used anywhere
public class NEWBranchOfficePanel extends BaseDataEntryPanel {
    private JPanel formPanel = null;
    private JTextField nameFLD;
    private JTextField phoneNumberFLD;
    private JTextArea addressFLD;
    private JPanel upperPane;

    public NEWBranchOfficePanel() {
        super(0.5);
        clazz = BranchOffice.class;
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
                NEWBranchOfficePanel panel = new NEWBranchOfficePanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }

        v = new Validator(mainApp, true);
        v.addTask(nameFLD, "Req", null, true);
        v.addTask(addressFLD, "", null, true);

    }

    private BranchOffice getModelFromForm() {
        BranchOffice bo = new BranchOffice();
        bo.setName(nameFLD.getText().trim());
        bo.setAddress(addressFLD.getText().trim());
        bo.setPhoneNumber(phoneNumberFLD.getText().trim());
        bo.setdFlag(1);

        return bo;
    }

    private void setModelIntoForm(BranchOffice bro) {
        nameFLD.setText(bro.getName());
        addressFLD.setText(bro.getAddress());
        phoneNumberFLD.setText(bro.getPhoneNumber());
    }

    protected final void save(boolean isModified) {
        initValidator();
        if (v.validate()) {
            try {

                BranchOffice newBo = getModelFromForm();
                if (isModified) {
                    BranchOffice bo = (BranchOffice) DBUtils.getById(BranchOffice.class, editingPrimaryId);
                    bo.setAddress(newBo.getAddress());
                    bo.setName(newBo.getName());
                    bo.setPhoneNumber(newBo.getPhoneNumber());
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

    protected final JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Branch Office Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 2");

            nameFLD = new JTextField();
            formPanel.add(nameFLD, "8, 2, fill, default");
            nameFLD.setColumns(10);

            JLabel lblAddress = new JLabel("Address");
            formPanel.add(lblAddress, "4, 4, default, top");

            addressFLD = new JTextArea(5, 40);
            formPanel.add(addressFLD, "8, 4, fill, fill");

            JLabel lblPhoneNumber = new JLabel("Phone Number");
            formPanel.add(lblPhoneNumber, "4, 6");

            phoneNumberFLD = new JTextField();
            formPanel.add(phoneNumberFLD, "8, 6, fill, default");
            phoneNumberFLD.setColumns(10);

            formPanel.add(getSaveButton(), "10, 6");
        }
        return formPanel;
    }

    @Override
    public final String getFunctionName() {
        return "Branch-Office Information";
    }

    protected final void populateSelectedRowInForm(int selectedId) {
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

    /*
     * (non-Javadoc)
     *
     * @see
     * com.ca.ui.panels.BaseDataEntryPanel#setModelIntoForm(java.lang.Object)
     */
    @Override
    protected final void setModelIntoForm(Object object) {
        BranchOffice bro = (BranchOffice) object;
        nameFLD.setText(bro.getName());
        addressFLD.setText(bro.getAddress());
        phoneNumberFLD.setText(bro.getPhoneNumber());

    }

    /*
     * (non-Javadoc)
     *
     * @see com.ca.ui.panels.BaseDataEntryPanel#getTableHeader()
     */
    @Override
    protected final String[] getTableHeader() {
        return new String[]{"S.N.", "ID", "Name", "Address", "PhoneNumber"};
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.ca.ui.panels.BaseDataEntryPanel#showDbModelListInGrid(java.util.List)
     */
    @Override
    protected final void showDbModelListInGrid(List<Class> list) {
        dataModel.resetModel();
        int sn = 0;
        for (Object boo : list) {
            BranchOffice bo = (BranchOffice) boo;
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getAddress(), bo.getPhoneNumber()});
        }
        // table.setTableHeader(tableHeader);
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

}
