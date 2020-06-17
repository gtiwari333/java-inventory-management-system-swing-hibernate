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

	
	
	/**
	*Purpose: check equal date format if date object has a format like "MMM dd, yyyy"
	*Input: date format that instance of Date, date(Calendar.getInstance().getTime())
	*Expected:
	*	Return date format("MMM dd, yyyy")
	*	
	*	ex) "6¿ù 17, 2020" = DTU.getCvDateMMMddyyyy(date)
	*	ex) "Jun 17, 2020" = DTU.getCvDateMMMddyyyy(date)
	*/
	@Test
	public void testGetCvDateMMMddyyyy() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		Date date = Calendar.getInstance().getTime();
		assertEquals("6¿ù 17, 2020", DTU.getCvDateMMMddyyyy(date));
//		assertEquals(sdf.format(date), DTU.getCvDateMMMddyyyy(date));
	}

	
	/**
	*Purpose: check equal date format if date object has a format like "MM/dd/yyyy"
	*Input: date format that instance of Date, date(Calendar.getInstance().getTime())
	*Expected:
	*	Return string of date format("MM/dd/yyyy")
	*	
	*	ex) "06/17/2020" = DTU.getCvDateMMDDYYYY(date)
	*/
	@Test
	public void testGetCvDateMMDDYYYY() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = Calendar.getInstance().getTime();
		assertEquals("06/17/2020", DTU.getCvDateMMDDYYYY(date));
//		assertEquals(sdf.format(date), DTU.getCvDateMMDDYYYY(date));
	}

	@Test
	public void testGetCurrentFiscalYear() {
		fail("Not yet implemented");
	}

	/**
	*Purpose: check integer of today's year
	*Input: string that date format that instance of Date, date(Calendar.getInstance().getTime())
	*Expected:
	*	Return int of date year
	*	
	*	ex) "2020-06-17" = DTU.testGetTodayDate()
	*		-> 2020
	*/
	@Test
	public void testGetYearFromYYYYMMDD() {
		assertEquals(2020, DTU.getYearFromYYYYMMDD(DTU.getTodayDate()));
	}

}
