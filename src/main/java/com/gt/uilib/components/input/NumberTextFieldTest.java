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

	/**
	*Purpose: check the constructor is well made
	*Input: int maxLength, boolean isPositiveOnly
	*Expected:
	*	Return
	*
	*			true -> isPositiveOnly(true)
	*			false -> isPositiveOnly(false)
	*	
	*/
	@Test
	public void testNumberTextFieldIntBoolean() {
		NTF = new NumberTextField(15, true);
		assertTrue(NTF.isRestrictPositiveNumber());
		NTF = new NumberTextField(15, false);
		assertFalse(NTF.isRestrictPositiveNumber());
	}

	/**
	*Purpose: check the constructor is well made
	*Input: just constructor
	*Expected:
	*	Return false of boolean isPositiveOnly
	*	
	*/
	@Test
	public void testNumberTextField() {
		NTF = new NumberTextField();
		assertFalse(NTF.isRestrictPositiveNumber());
	}

	
	/**
	*Purpose: check the constructor is well made
	*Input: boolean isPositiveOnly
	*Expected:
	*	Return 
	*			false if boolean isPositiveOnly is false
	*			true if boolean isPositiveOnly is true
	*	
	*/
	@Test
	public void testNumberTextFieldBoolean() {
		NTF = new NumberTextField(true);
		assertTrue(NTF.isRestrictPositiveNumber());
		NTF = new NumberTextField();
		assertFalse(NTF.isRestrictPositiveNumber());
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
