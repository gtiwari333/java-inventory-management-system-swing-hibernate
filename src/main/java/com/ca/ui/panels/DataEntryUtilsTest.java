package com.ca.ui.panels;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gt.uilib.components.input.DataComboBox;

public class DataEntryUtilsTest {
	static DataEntryUtils dataentryutils;
	
	/**
	 * Purpose: initialization for test
	 * Input: -
	 * Expected: instance of DataEntryUtils 
	 * 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		dataentryutils = new DataEntryUtils();
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		dataentryutils = null;
	}

	/**
	 * Purpose: check YES or NO to save DB
	 * Input: msg("Are you sure to save")
	 * Expected: 
	 *      if choose YES button, return true.
	 *      else return false.
	 *      
	 */
	@Test
	public void testConfirmDBSave() {
		String msg = "Are you sure to save";
		boolean result = dataentryutils.confirm(msg);
		assertEquals(result, dataentryutils.confirmDBSave());
	}
	
	/**
	 * Purpose: check YES or NO to modify DB
	 * Input: msg("Are you sure to modify")
	 * Expected: 
	 *      if choose YES button, return true.
	 *      else return false.
	 *      
	 */

	@Test
	public void testConfirmDBUpdate() {
		String msg = "Are you sure to modify";
		boolean result = dataentryutils.confirm(msg);
		assertEquals(result, dataentryutils.confirmDBSave());
	}
	
	/**
	 * Purpose: check YES or NO to delete DB
	 * Input: msg("Are you sure to delete")
	 * Expected: 
	 *      if choose YES button, return true.
	 *      else return false.
	 *      
	 */

	@Test
	public void testConfirmDBDelete() {
		String msg = "Are you sure to delete";
		boolean result = dataentryutils.confirm(msg);
		assertEquals(result, dataentryutils.confirmDBSave());
	}

}
