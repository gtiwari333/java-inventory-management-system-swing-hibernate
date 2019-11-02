package com.ca.ui.panels;

import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class BaseDataEntryPanel extends AbstractFunctionPanel {
    private JButton btnReadAll;
    private JButton btnNew;
    private JButton btnDeleteThis;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;

    BetterJTable table;
    EasyTableModel dataModel;
    private JPanel buttonPanel;
    Validator v;

    int editingPrimaryId = 0;
    private JButton btnModify;
    private JButton btnCancel;
    Class clazz;

    BaseDataEntryPanel(double splitValue) {
        /**
         * all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(splitValue);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        /**
         * never forget to call after setting up UI
         */
        init();
    }

    @Override
    public final void init() {
        /* never forget to call super.init() */
        super.init();
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
    }

    final JButton getSaveButton() {
        if (btnSave == null) {
            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> handleSaveAction());

        }

        return btnSave;
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

            btnDeleteThis = new JButton("Delete This");
            btnDeleteThis.addActionListener(e -> {
                if (editingPrimaryId > 0)
                    handleDeleteAction();
            });

            btnModify = new JButton("Modify");
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
            deleteSelectedModel();
        }

    }

    private void deleteSelectedModel() {
        try {
            DBUtils.deleteById(clazz, editingPrimaryId);
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
                enableDisableINIT();
                break;
            case CREATE:
                enableDisableCREATE();
                break;
            case MODIFY:
                enableDisableMODIFY();
                break;

            case READ:
                enableDisableREAD();

                break;

            default:
                break;
        }
    }

    private void enableDisableREAD() {
        UIUtils.toggleAllChildren(getUpperFormPanel(), false);
        UIUtils.toggleAllChildren(getButtonPanel(), true);
        UIUtils.clearAllFields(getUpperFormPanel());
        table.clearSelection();
        table.setEnabled(true);
        editingPrimaryId = -1;
        btnCancel.setEnabled(false);
    }

    private void enableDisableMODIFY() {
        UIUtils.toggleAllChildren(getUpperFormPanel(), true);
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        btnCancel.setEnabled(true);
        getSaveButton().setEnabled(true);
        table.setEnabled(false);
    }

    private void enableDisableCREATE() {
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        UIUtils.toggleAllChildren(getUpperFormPanel(), true);
        table.setEnabled(false);
        btnCancel.setEnabled(true);
        getSaveButton().setEnabled(true);
    }

    private void enableDisableINIT() {
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        UIUtils.toggleAllChildren(getUpperFormPanel(), false);
        UIUtils.clearAllFields(getUpperFormPanel());
        btnReadAll.setEnabled(true);
        btnNew.setEnabled(true);
        table.setEnabled(true);
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

    protected abstract void setModelIntoForm(Object object);

    protected abstract void save(boolean isModified);

    protected abstract JPanel getUpperFormPanel();

    protected abstract String[] getTableHeader();

    protected abstract void showDbModelListInGrid(List<Class> list);

    final void readAndShowAll(boolean showSize0Error) {
        try {
            List<Class> brsL = DBUtils.readAll(clazz);
            editingPrimaryId = -1;
            if (brsL == null || brsL.size() == 0) {
                if (showSize0Error) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
                return;
            }
            showDbModelListInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
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
            dataModel = new EasyTableModel(getTableHeader());

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
                    populateSelectedRowInForm(selectedId);
                }
            });
        }
        return lowerPane;
    }

    protected abstract void populateSelectedRowInForm(int selectedId);

}
