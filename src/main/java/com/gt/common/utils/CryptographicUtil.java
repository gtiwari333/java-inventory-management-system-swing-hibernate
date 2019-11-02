package com.gt.common.utils;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class CryptographicUtil {

    /**
     * The ecipher.
     */
    private Cipher ecipher = null;

    /**
     * The dcipher.
     */
    private Cipher dcipher = null;

    /**
     * The key.
     */
    private String key;

    /**
     * Instantiates a new nI cryptographic util.
     *
     * @param key the key
     */
    public CryptographicUtil(SecretKey key) {
        try {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Encrypt text.
     *
     * @param text          the text
     * @param encryptionKey the encryption key
     * @return the string
     */
    public static String encryptText(String text, String encryptionKey) {
        byte[] keyByte = encryptionKey.getBytes();
        CryptographicUtil nicUtil;
        String encryptedText = null;
        try {

            KeySpec ks = new DESKeySpec(keyByte);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);
            nicUtil = new CryptographicUtil(ky);
            encryptedText = nicUtil.encrypt(text);
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * Decrypt text.
     *
     * @param text          the text
     * @param decryptionKey the decryption key
     * @return the string
     */
    public static String decryptText(String text, String decryptionKey) {
        byte[] keyByte = decryptionKey.getBytes();
        CryptographicUtil nicUtil;
        String decryptedText = null;
        try {

            KeySpec ks = new DESKeySpec(keyByte);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);
            nicUtil = new CryptographicUtil(ky);
            decryptedText = nicUtil.decrypt(text);
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    /**
     * The main method.
     *
     * @param argv the arguments
     */
    public static void main(String[] argv) {
        String encrypted = encryptText("scs1 -", "jb6bLQ41");
        System.out.println("endrypted:" + encrypted);
        String decrypted = decryptText(encrypted, "jb6bLQ41");
        System.out.println("decrypted:" + decrypted);
    }

    /**
     * Encrypt.
     *
     * @param str the str
     * @return the string
     */
    public final String encrypt(String str) {

        byte[] utf8;

        byte[] enc;
        String encryptedText = null;
        try {
            // Encode the string into bytes using utf-8
            utf8 = str.getBytes(StandardCharsets.UTF_8);
            enc = ecipher.doFinal(utf8);
            encryptedText = new String(Base64.getEncoder().encode(enc));
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return encryptedText;
    }

    /**
     * Decrypt.
     *
     * @param str the str
     * @return the string
     */
    public final String decrypt(String str) {

        byte[] dec;
        byte[] utf8;
        String decryptedText = null;
        try {
            // Decode base64 to get bytes
            dec = Base64.getDecoder().decode(str);
            utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            decryptedText = new String(utf8, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public final String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key the new key
     */
    public final void setKey(String key) {
        this.key = key;
    }

}
