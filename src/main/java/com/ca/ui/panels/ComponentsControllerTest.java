package com.ca.ui.panels;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.gt.common.constants.Status;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;

public class ComponentsControllerTest {

	private ComponentsController componentsController;
 	private JPanel formPanel = null;
    private JPanel buttonPanel;
    private JButton btnReadAll;
    private JButton btnSave;
    private JButton btnNew;
    private BetterJTable table;
    private int editingPrimaryId = 0;
    private JButton btnCancel;
    private EasyTableModel dataModel;
	
    
    /**
	*Purpose: ready for test
	*Input: -
	*Expected: an instance of ComponentsController
	*/
	@Before
	public void setUp() throws Exception {
		componentsController = new ComponentsController();
		
		buttonPanel = new JPanel();
		formPanel = new JPanel();
        btnReadAll = new JButton("Read All");
        btnCancel = new JButton("Cancel");
        btnNew = new JButton("New");
        btnSave = new JButton("Save");
        
        final String[] header = new String[]{"S.N.", "ID", "Name", "Address", "District", "PhoneNumber"};

        dataModel = new EasyTableModel(header);
        table = new BetterJTable(dataModel);
        
        componentsController.setButtonPanel(buttonPanel);
        componentsController.setFormPanel(formPanel);
        componentsController.setBtnCancel(btnCancel);
        componentsController.setBtnSave(btnSave);
        componentsController.setBtnReadAll(btnReadAll);
        componentsController.setBtnNew(btnNew);
        componentsController.setTable(table);
        componentsController.setEditingPrimaryId(editingPrimaryId);
        assertNotNull(componentsController);
	}

	/**
	*Purpose: end test
	*Input: -
	*Expected: -
	*/
	@After
	public void tearDown() throws Exception {
		componentsController = null;
		assertNull(componentsController);
	}

	/**
	*Purpose: Test method enableDisableComponents with Status.NONE
	*Input: Status.NONE
	*Expected: 	btnReadAll is enabled
	*			btnNew is enabled
	*			table is enabled
	*/
	@Test
	public void testEnableDisableComponentsNONE() {
		componentsController.enableDisableComponents(Status.NONE);
		assertEquals(btnReadAll.isEnabled(), true);
		assertEquals(btnNew.isEnabled(), true);
		assertEquals(table.isEnabled(), true);
	}

	/**
	*Purpose: Test method enableDisableComponents with Status.CREATE
	*Input: Status.CREATE
	*Expected: 	btnSave is enabled
	*			btnCancel is enabled
	*			table is disabled
	*/
	@Test
	public void testEnableDisableComponentsCREATE() {
		componentsController.enableDisableComponents(Status.CREATE);
		assertEquals(table.isEnabled(), false);
		assertEquals(btnCancel.isEnabled(), true);
		assertEquals(btnSave.isEnabled(), true);
	}
	
	/**
	*Purpose: Test method enableDisableComponents with Status.MODIFY
	*Input: Status.MODIFY
	*Expected: 	btnCancel is enabled
	*			btnSAave is enabled
	*			table is disabled
	*/
	@Test
	public void testEnableDisableComponentsMODIFY() {
		componentsController.enableDisableComponents(Status.MODIFY);
		assertEquals(btnCancel.isEnabled(), true);
		assertEquals(btnSave.isEnabled(), true);
		assertEquals(table.isEnabled(), false);
	}
	
	/**
	*Purpose: Test method enableDisableComponents with Status.READ
	*Input: Status.READ
	*Expected: 	EditingPrimaryId becomes -1
	*			btnCancel is disabled
	*			table is enabled
	*/
	@Test
	public void testEnableDisableComponentsREAD() {
		componentsController.enableDisableComponents(Status.READ);
		assertEquals(componentsController.getEditingPrimaryId(), -1);
		assertEquals(btnCancel.isEnabled(), false);
		assertEquals(table.isEnabled(), true);
	}
	
	/**
	*Purpose: Test method enableDisableItemComponents with Status.NONE
	*Input: Status.NONE
	*Expected: 	btnSave is enabled
	*			table is enabled
	*/
	@Test
	public void testEnableDisableItemComponentsNONE() {
		componentsController.enableDisableItemComponents(Status.NONE);
		assertEquals(btnSave.isEnabled(), true);
		assertEquals(table.isEnabled(), true);
	}
	
	/**
	*Purpose: Test method enableDisableItemComponents with Status.READ
	*Input: Status.READ
	*Expected: 	table is enabled
	*/
	@Test
	public void testEnableDisableItemComponentsREAD() {
		componentsController.enableDisableItemComponents(Status.READ);
		assertEquals(table.isEnabled(), true);
	}
}