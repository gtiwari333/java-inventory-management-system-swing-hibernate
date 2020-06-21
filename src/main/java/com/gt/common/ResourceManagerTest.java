package com.gt.common;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class ResourceManagerTest {
	
	private static ResourceManager resourceManager;
	
	@Before
	public void setup() throws Exception {
		resourceManager = new ResourceManager();
	}
	
	@After
	public void tearDown() throws Exception {
		resourceManager = null;
	}

	/**
	 * Purpose : Testing for ResourceManager method
	 * Input :
	 * Expected : Return not null value
	 */
	@Test
	void testGetReader() {
		assertNotNull(resourceManager.getReader());
	}

	/**
	 * Purpose : Testing for matched value in hash map about not exist key value
	 * Input : Not exist key value
	 * Expected : Return null value
	 */
	@Test
	void testGetString() {
		String key = "NotExistKey";
		assertNull(resourceManager.getString(key));
	}

	/**
	 * Purpose : Testing whether an exception occur or not when ReadMap method run
	 * Input :
	 * Expected : exception is not occurred
	 */
	@Test
	void testReadMap() {
		boolean thrown = false;
		try {
			String file = "File";
			boolean isEncry = false;
			Map<String, String> map;
			map = resourceManager.readMap(file, isEncry);
			
		} catch (IOException e) {
			thrown = true;
		}
		assertFalse(thrown);
	}

	/**
	 * Purpose : Testing whether SaveMap method successfully saved the hash map
	 * Input : file name, hash map, boolean value for testing
	 * Expected : Return true (Success)
	 */
	@Test
	void testSaveMap() {
		String file = "File";
		boolean isEncry = false;
		Map<String, String> map = new HashMap<>();
		
		assertTrue(resourceManager.saveMap(file, map, isEncry));
	}

	/**
	 * Purpose : Testing whether an exception occur or not when GetConfigParam method run
	 * Input :
	 * Expected : exception is not occurred
	 */
	@Test
	void testGetConfigParam() {
		String key = "key";
		boolean thrown = false;
		
		try {
			resourceManager.getConfigParam(key);
		} catch(Exception e) {
			thrown = true;
		}
		
		assertFalse(thrown);
	}

	/**
	 * Purpose : Test whether the method reads the image successfully without causing an exception
	 * Input :
	 * Expected : Not occur exception (false)
	 */
	@Test
	void testReadImage() {
		boolean thrown = false;
		String imageName = "image";
		try {
			resourceManager.readImage(imageName);
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}
	
}
