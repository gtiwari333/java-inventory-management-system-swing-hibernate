package com.gt.uilib.components;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActionMenuItemTest {

	/**
	 * Purpose: initialization for test
	 * Input: text("asdfawef"), fileFullName("file1"), panelQualifiedClassName("a3fq23f")
	 * Expected: instance of ActionMenuItem
	 */
	@Test
	public void testActionMenuItem() {
		String text = "asdfawef";
		String fileFullName = "file1";
		String panelQualifiedClassName = "a3fq23f";
		ActionMenuItem item = new ActionMenuItem(text, fileFullName, panelQualifiedClassName);
		
	}

	/**
	 * Purpose: create instance of ActionMenuItem
	 * Input: text("awewafasdf"), fileName("file1"), panelQualifiedClassName("awefawefaw")
	 * Expected: 
	 *     create file "file1-menu.png"
	 */
	@Test
	public void testCreate() {
		String text = "awewafasdf";
		String fileName = "file1";
		String panelQualifiedClassName = "awefawefaw";
		ActionMenuItem item = ActionMenuItem.create(text, fileName, panelQualifiedClassName);
		
		
	}

}
