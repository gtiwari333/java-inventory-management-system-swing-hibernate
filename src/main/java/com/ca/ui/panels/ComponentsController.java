package com.ca.ui.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.table.BetterJTable;

public class ComponentsController {

	private JPanel formPanel = null;
    private JPanel buttonPanel;
    private JButton btnReadAll;
    private JButton btnSave;
    private JButton btnNew;
    private JButton btnCancel;
    private BetterJTable table;

    private int editingPrimaryId = 0;
	
    
    
	public JPanel getFormPanel() {
		return formPanel;
	}



	public void setFormPanel(JPanel formPanel) {
		this.formPanel = formPanel;
	}



	public JPanel getButtonPanel() {
		return buttonPanel;
	}



	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}



	public JButton getBtnReadAll() {
		return btnReadAll;
	}



	public void setBtnReadAll(JButton btnReadAll) {
		this.btnReadAll = btnReadAll;
	}



	public JButton getBtnSave() {
		return btnSave;
	}



	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}



	public JButton getBtnNew() {
		return btnNew;
	}



	public void setBtnNew(JButton btnNew) {
		this.btnNew = btnNew;
	}



	public JButton getBtnCancel() {
		return btnCancel;
	}



	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}



	public BetterJTable getTable() {
		return table;
	}



	public void setTable(BetterJTable table) {
		this.table = table;
	}



	public int getEditingPrimaryId() {
		return editingPrimaryId;
	}



	public void setEditingPrimaryId(int editingPrimaryId) {
		this.editingPrimaryId = editingPrimaryId;
	}



	public void enableDisableComponents(Status status) {
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

    public void enableDisableItemComponents(Status status) {
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

}
