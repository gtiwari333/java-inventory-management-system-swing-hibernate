package com.gt.common.utils;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
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
	
	/**
	*Purpose: check false if date object is not null
	*Input: new object of DateTimeUtils DTU, and object of Date date
	*Expected:
	*	Return true
	*	
	*	
	*/
	@Test
	public void testDateIsNotEmpty() {
		date = Calendar.getInstance().getTime();
		assertFalse(DTU.isEmpty(date)); 
	}
	
	/**
	*Purpose: check equal date format if date object has today's instance
	*Input: instance of Date today's date(Calendar.getInstance().getTime())
	*Expected:
	*	Return today's date("yyyy-MM-dd")
	*	
	*	ex) "2020-06-17" = DTU.getTodayDate()
	*/
	@Test
	public void testGetTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = Calendar.getInstance().getTime();
//		sdf.format(date);
//		assertEquals("2020-06-17", DTU.getTodayDate());
		assertEquals(sdf.format(date), DTU.getTodayDate());
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
