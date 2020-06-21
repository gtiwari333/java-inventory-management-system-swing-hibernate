package com.gt.db;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class SessionUtilsTest {

	private static SessionUtils sessionUtils;
	
	@Before
	public void setup() throws Exception {
		sessionUtils = new SessionUtils();
	}

	@After
	public void tearDown() throws Exception {
		sessionUtils = null;
	}

	/**
	 * Purpose : Test whether exceptions occur or not when the method is run
	 * Input :
	 * Expected : Return False (Not occur exception)
	 */

	@Test
	void testGet() {
		boolean thrown = false;
		try {
			sessionUtils.get();
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	/**
	 * Purpose : Test whether exceptions occur or not when the method is run
	 * Input :
	 * Expected : Return False (Not occur exception)
	 */

	@Test
	void testGetSession() {
		boolean thrown = false;
		try {
			sessionUtils.getSession();
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	/**
	 * Purpose : Test whether exceptions occur or not when the method is run
	 * Input :
	 * Expected : Return False (Not occur exception)
	 */

	@Test
	void testClose() {
		Session session;
		boolean thrown = false;
		try {
			sessionUtils.getSession()
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	/**
	 * Purpose : Test whether exceptions occur or not when the method is run
	 * Input :
	 * Expected : Return False (Not occur exception)
	 */

	@Test
	void testRollback() {
		Transaction tx = null;
		boolean thrown = false;
		try {
			sessionUtils.rollback(tx);
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

}
