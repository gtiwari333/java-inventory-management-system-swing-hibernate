package com.gt.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String getSha256(String someText) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");

            md.update(someText.getBytes());

            byte[] byteData = md.digest();

            // convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.getSha256("gt"));
    }
}
