package com.gt.uilib.components.input;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gt.uilib.components.input.DataComboBox.Item;

public class DataComboBoxTest {
	private static final long serialVersionUID = -615634209230481880L;
	private List<Item> itemList;
	private Object[] values = {0, };
	static DataComboBox datacombobox;

	/**
	 * Purpose: initialization for test
	 * Input: -
	 * Expected: instance of DataComboBox 
	 * 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		datacombobox = new DataComboBox();
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		datacombobox = null;
	}
	
	/**
	 * Purpose: clears the all data in combo
	 * Input: -
	 * Expected: itemList == null 
	 */
	@Test
	public void testInit() {
		datacombobox.init();
		assertNull(itemList);
	}

	/**
	 * Purpose: check selected data is valid
	 * Input: -
	 * Expected: 
	 *      return false
	 *      getSelectedId() == -1
	 */
	@Test
	public void testIsValidDataChoosen() {
		boolean result = datacombobox.isValidDataChoosen();
		assertEquals(false, result);
	}
	
	/**
	 * Purpose: add values into itemList
	 * Input: Object[] values
	 * Expected: 
	 *      itemList != null
	 */

	@Test
	public void testAddRow() {
		datacombobox.addRow(values);
		assertNotNull(itemList);
	}

	/**
	 * Purpose: append values to string
	 * Input: Object[] values
	 * Expected: 
	 *      return string added values
	 *      ex) string + values + " - "
	 */
	@Test
	public void testGetStringRepresentation() {
		String result = datacombobox.getStringRepresentation(values);
		assertEquals(result, values + "  -  ");
	}


	/**
	 * Purpose: check input string is equal to itemList
	 * Input: String s("awefawefawfasfd")
	 * Expected: 
	 *       return false
	 *       itemList != s
	 */
	@Test
	public void testContainsString() {
		String s = "awefawefawfasfd";
		boolean result = datacombobox.contains(s);
		assertEquals(false, result);
	}



}
