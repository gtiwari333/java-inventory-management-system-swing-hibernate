package com.gt.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

	/**
	*Purpose: check equal between object data and String
	*Input: object variable
	*Expected:
	*	Return true
	*	int(12345) -> str("12345")
	*	
	*/
	@Test
	public void testToStringObject() {
		assertEquals("12345", StringUtils.toString((Integer)12345));
	}

	@Test
	public void testClean() {
		fail("Not yet implemented");
	}

	@Test
	public void testTrim() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public void testReplaceStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testReplaceStringStringStringInt() {
		fail("Not yet implemented");
	}

}
