package com.gt.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5(String someText) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(someText.getBytes());

            byte[] byteData = md.digest();

            // convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5Util error " + e.getMessage());
        }
        return someText + "NOMD5";
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("gt"));
    }
}
