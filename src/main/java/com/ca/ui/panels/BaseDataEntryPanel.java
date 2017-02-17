package com.ca.ui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;

public abstract class BaseDataEntryPanel extends AbstractFunctionPanel {
    protected JButton btnReadAll;
    protected JButton btnNew;
    protected JButton btnDeleteThis;
    protected JButton btnSave;
    protected JPanel upperPane;
    protected JPanel lowerPane;

    protected BetterJTable table;
    protected EasyTableModel dataModel;
    protected JPanel buttonPanel;
    protected Validator v;

    protected int editingPrimaryId = 0;
    protected JButton btnModify;
    protected JButton btnCancel;
    protected Class clazz;

    public BaseDataEntryPanel(double splitValue) {
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
    public void init() {
        /* never forget to call super.init() */
        super.init();
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
    }

    protected JButton getSaveButton() {
        if (btnSave == null) {
            btnSave = new JButton("Save");
            btnSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleSaveAction();

                }
            });

        }

        return btnSave;
    }

    protected JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            btnReadAll = new JButton("Read All");
            btnReadAll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    readAndShowAll(true);
                    changeStatus(Status.READ);
                }

            });
            buttonPanel.add(btnReadAll);

            btnNew = new JButton("New");
            btnNew.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    changeStatus(Status.CREATE);
                }
            });
            buttonPanel.add(btnNew);

            btnDeleteThis = new JButton("Delete This");
            btnDeleteThis.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (editingPrimaryId > 0)
                        handleDeleteAction();
                }

            });

            btnModify = new JButton("Modify");
            btnModify.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (editingPrimaryId > 0)
                        changeStatus(Status.MODIFY);
                }
            });
            buttonPanel.add(btnModify);
            buttonPanel.add(btnDeleteThis);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    changeStatus(Status.READ);
                }
            });
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }

    protected void handleDeleteAction() {
        switch (status) {
            case READ:
                deleteSelectedModel();
                break;
            default:
                break;
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
    public void enableDisableComponents() {
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

    protected void enableDisableREAD() {
        UIUtils.toggleAllChildren(getUpperFormPanel(), false);
        UIUtils.toggleAllChildren(getButtonPanel(), true);
        UIUtils.clearAllFields(getUpperFormPanel());
        table.clearSelection();
        table.setEnabled(true);
        editingPrimaryId = -1;
        btnCancel.setEnabled(false);
    }

    protected void enableDisableMODIFY() {
        UIUtils.toggleAllChildren(getUpperFormPanel(), true);
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        btnCancel.setEnabled(true);
        getSaveButton().setEnabled(true);
        table.setEnabled(false);
    }

    protected void enableDisableCREATE() {
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        UIUtils.toggleAllChildren(getUpperFormPanel(), true);
        table.setEnabled(false);
        btnCancel.setEnabled(true);
        getSaveButton().setEnabled(true);
    }

    protected void enableDisableINIT() {
        UIUtils.toggleAllChildren(getButtonPanel(), false);
        UIUtils.toggleAllChildren(getUpperFormPanel(), false);
        UIUtils.clearAllFields(getUpperFormPanel());
        btnReadAll.setEnabled(true);
        btnNew.setEnabled(true);
        table.setEnabled(true);
    }

    @Override
    public void handleSaveAction() {

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

    protected void readAndShowAll(boolean showSize0Error) {
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
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    int selRow = table.getSelectedRow();
                    if (selRow != -1) {
                        /**
                         * if second column doesnot have primary id info, then
                         */
                        int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
                        populateSelectedRowInForm(selectedId);
                    }
                }
            });
        }
        return lowerPane;
    }

    protected abstract void populateSelectedRowInForm(int selectedId);

}
