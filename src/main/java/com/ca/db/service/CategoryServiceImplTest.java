package com.ca.db.service;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ca.db.model.Category;
import com.ca.db.model.SubCategory;

public class CategoryServiceImplTest {

	static CategoryServiceImpl categoryServiceImpl;
	
	/**
	*Purpose: ready for test
	*Input: -
	*Expected: an instance of CategoryServiceImpl
	*/
	@Before
	public void setUp() throws Exception {
		categoryServiceImpl = new CategoryServiceImpl();
	}

	/**
	*Purpose: end test
	*Input: -
	*Expected: -
	*/
	@After
	public void tearDown() throws Exception {
		categoryServiceImpl = null;
	}

	
	/**
	*Purpose: Test for existing categories
	*Input: String for category ("식품")
	*Expected: boolean true
	*/
	@Test
	public void testisCategoryExistsTrue() throws Exception {
		boolean result = categoryServiceImpl.isCategoryExists("식품");
		assertEquals(result, true);
	}

	/**
	*Purpose: Test for not existing categories
	*Input:  String for category ("notexsists")
	*Expected: boolean false
	*/
	@Test
	public void testisCategoryExistsFalse() throws Exception {
		boolean result = categoryServiceImpl.isCategoryExists("notexsists");
		assertEquals(result, false);
	}
}