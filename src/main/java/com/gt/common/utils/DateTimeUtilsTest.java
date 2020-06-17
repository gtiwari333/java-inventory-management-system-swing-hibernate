package com.gt.common.utils;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DateTimeUtilsTest {
	private DateTimeUtils DTU;
	private Date date;
	@Before
	public void setUp(){
		DTU = new DateTimeUtils();
		date = null;
	}
	
	/**
	*Purpose: check true if date object is null
	*Input: new object of DateTimeUtils DTU, and null object of Date date
	*Expected:
	*	Return true
	*	
	*	
	*/
	@Test
	public void testDateIsEmpty() {
		assertTrue(DTU.isEmpty(date)); 
	}
	
	
	

	@Test
	public void testGetTodayDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCvDateMMMddyyyy() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCvDateMMDDYYYY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentFiscalYear() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearFromYYYYMMDD() {
		fail("Not yet implemented");
	}

}
