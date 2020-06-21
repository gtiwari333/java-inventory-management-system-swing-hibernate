package com.gt.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptographicUtilTest {
	static CryptographicUtil cryptographicUtil;

	
	/**
	*Purpose: ready for test
	*Input: -
	*Expected: an instance of CryptographicUtil
	*	
	*/
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String decryptionKey = "jb6bLQ41";
		byte[] keyByte = decryptionKey.getBytes();
		cryptographicUtil = CryptographicUtil.getCryptoGraphicUtil(keyByte);
		assertNotNull(cryptographicUtil);
	}

	/**
	*Purpose: end test
	*Input: -
	*Expected: -
	*	
	*/
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		cryptographicUtil = null;
	}


	/**
	*Purpose: set Key for cipher
	*Input: key ("jb6bLQ41")
	*Expected:
	*	key ("jb6bLQ41")
	*/
	@Test
	void testKey() {
		cryptographicUtil.setKey("jb6bLQ41");
		String key = cryptographicUtil.getKey();
		assertEquals(key,"jb6bLQ41");
	}

	
	/**
	*Purpose: encrypt String(text) with DES 
	*Input: encrypt("test string")
	*Expected:
	*	fM6eaXLSMtjpoFUuTtV40Q==
	*/
	@Test
	void testEncrypt() {
		String encrypted = cryptographicUtil.encrypt("test string");
		assertEquals(encrypted,"fM6eaXLSMtjpoFUuTtV40Q==");
	}

	/**
	*Purpose: Decrypt String(text) with DES 
	*Input: encrypt("fM6eaXLSMtjpoFUuTtV40Q")
	*Expected:
	*	"test string"
	*/
	@Test
	void testDecrypt() {
		String decrypted = cryptographicUtil.decrypt("fM6eaXLSMtjpoFUuTtV40Q==");
		assertEquals(decrypted,"test string");
	}

}
