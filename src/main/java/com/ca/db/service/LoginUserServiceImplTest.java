package com.ca.db.service;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ca.db.model.LoginUser;

public class LoginUserServiceImplTest {
	
	static LoginUserServiceImpl loginUserServiceImpl;
	
	/**
	*Purpose: ready for test
	*Input: -
	*Expected: an instance of LoginUserServiceImpl
	*/
	@Before
	public void setUp() throws Exception {

		loginUserServiceImpl = new LoginUserServiceImpl();
	}


	/**
	*Purpose: end for test
	*Input: -
	*Expected: -
	*/
	@After
	public void tearDown() throws Exception {
		loginUserServiceImpl = null;
	}


	/**
	*Purpose: test getter method of LoginService
	*Input:  super user account
	*Expected: an instance of LoginUser
	*/
	@Test
	public void testGetLoginUser() throws Exception{
		String PASS = "gt";	
		String NAME = "gt_ebuddy";
		LoginUser login = loginUserServiceImpl.getLoginUser(NAME,PASS);
		assertNotNull(login);
	}
	
	/**
	*Purpose: test getter method of LoginService
	*Input:  not existing user name , password
	*Expected: null object
	*/
	@Test
	public void testGetLoginUserError() throws Exception{
		String PASS = "notexsists";	
		String NAME = "errorname";
		LoginUser login = loginUserServiceImpl.getLoginUser(NAME,PASS);	
		assertNull(login);
	}
	
	/**
	*Purpose: check if any user object is in DB
	*Input:  -
	*Expected: boolean value(true)
	*/
	@Test
	public void testUserExists() throws Exception{
		boolean result = loginUserServiceImpl.userExists();
		assertEquals(result, true);
	}
	

	/**
	*Purpose: test change of login user
	*Input:  super user account
	*Expected: session object (checked by result)
	*/
	@Test
	public void testChangeLogin() throws Exception{
		String PASS = "gt";	
		String NAME = "gt_ebuddy";
		Session s = loginUserServiceImpl.getSession();
		
		loginUserServiceImpl.changeLogin(NAME,PASS);
		boolean result = s.isOpen();
		assertEquals(result, true);
	}
}

