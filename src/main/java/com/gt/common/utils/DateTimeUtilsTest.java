package com.gt.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.gt.common.ResourceManager;

class DateTimeUtilsTest {
	
	private static DateTimeUtils dateTimeUtils;
	
	@Before
	public void setup() throws Exception {
		dateTimeUtils = new DateTimeUtils();
	}
	
	@After
	public void tearDown() throws Exception {
		dateTimeUtils = null;
	} 

	/**
	 * Purpose : Testing for DateTimeUtils method
	 * Input :
	 * Expected : Return False (Not empty)
	 */
	@Test
	void testIsEmpty() {
		Date date = new Date();
		assertFalse(dateTimeUtils.isEmpty(date));
	}

	/**
	 * Purpose : Test whether the output format is working properly
	 * Input :
	 * Expected : Return Ture (yyyy-MM-dd)
	 */
	@Test
	void testGetTodayDate() {
		char delim = '-';
		String resultDate;
		boolean check = false;
		resultDate = dateTimeUtils.getTodayDate();
		if(resultDate.charAt(4) == delim && resultDate.charAt(7) == delim) check = true;
		
		assertTrue(check);
	}

	/**
	 * Purpose : Test whether the output format is working properly
	 * Input : Date object
	 * Expected : Return True (MMM dd, yyyy)
	 */
	@Test
	void testGetCvDateMMMddyyyy() {
		char delim = ',';
		String resultDate;
		Date date = new Date();
		boolean check = false;
		resultDate = dateTimeUtils.getCvDateMMMddyyyy(date);
		if(resultDate.charAt(5) == delim) check = true;
		
		assertTrue(check);
	}

	/**
	 * Purpose : Test whether the output format is working properly
	 * Input : Date object
	 * Expected : Return False (MM/dd/yyyy)
	 */
	@Test
	void testGetCvDateMMDDYYYY() {
		char delim = '/';
		String resultDate;
		Date date = new Date();
		boolean check = false;
		resultDate = dateTimeUtils.getCvDateMMDDYYYY(date);
		if(resultDate.charAt(2) == delim && resultDate.charAt(5) == delim) check = true;
		
		assertTrue(check);
	}

	/**
	 * Purpose : Test whether the output format is working properly
	 * Input : Date 20200621
	 * Expected : same (2020)
	 */
	@Test
	void testGetYearFromYYYYMMDD() {
		String testDate = "20200621";
		int year = 2020;
		assertEquals(year, dateTimeUtils.getYearFromYYYYMMDD(testDate));
	}

}
