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
	*	double(123.456) ->str("123.456")
	*	
	*/
	@Test
	public void testToStringObject() {
		assertEquals("12345", StringUtils.toString((Integer)12345));
		assertEquals("123.456", StringUtils.toString((Double)123.456));
	}

	
	/**
	*Purpose: check equal that string variable apply trim
	*Input: string variable
	*Expected:
	*	Return true
	*	string(" hello java? ") -> string("hello java?")
	*	
	*/
	@Test
	public void testClean() {
		assertEquals("hello java?", StringUtils.trim(" hello java? "));
//		fail("Not yet implemented");
	}

	/**
	*Purpose: check equal that string variable apply trim
	*Input: string variable
	*Expected:
	*	Return true
	*	string(" hello java? ") -> string("hello java?")
	*	
	*/
	@Test
	public void testTrim() {
		assertEquals("hello java?", StringUtils.trim(" hello java? "));
	}
	
	
	
	/**
	*Purpose: check string is empty
	*Input: string variable
	*Expected:
	*	Return true
	*	string("hello java?") ->false(not empty)
	*	string(""), null string -> true
	*	
	*/
	@Test
	public void testIsEmpty() {
		assertTrue(StringUtils.isEmpty(""));
		assertFalse(StringUtils.isEmpty("hello java?"));
	}

	@Test
	public void testReplaceStringStringString() {
		fail("Not yet implemented");
	}

	/**
	*Purpose: check equal string replace with count
	*Input: string text, string replaceTarget, string newValue, int count 
	*Expected:
	*	Return true
	*	string("apple pie"), replaceTarget("p"), newValue("z"), count(2) ->string("azzle pie")
	*	
	*/
	@Test
	public void testReplaceStringStringStringInt() {
		assertEquals("azzle pie", StringUtils.replace("apple pie", "p", "z", 2));
	}

}
