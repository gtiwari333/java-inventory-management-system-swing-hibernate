package com.gt.uilib.components.input;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NumberTextFieldTest {
	private NumberTextField NTF;
	private NumberFormatDocument NFD;
	
//	@Before
//	void setup(){
//		NTF = new NumberTextField();
//	}
//	

	
	/**
	*Purpose: check the constructor is well made
	*Input: int maxLength
	*Expected:
	*	Return false of boolean isPositiveOnly
	*	
	*	
	*/
	@Test
	public void testNumberTextFieldInt() {
		NTF = new NumberTextField(15);
		assertFalse(NTF.isRestrictPositiveNumber());
	}

	@Test
	public void testNumberTextFieldIntBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberTextField() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberTextFieldBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsRestrictPositiveNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRestrictPositiveNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsNonZeroEntered() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateDefaultModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMaxLength() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDecimalPlace() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetText() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectAll() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInitProperties() {
		fail("Not yet implemented");
	}

}
