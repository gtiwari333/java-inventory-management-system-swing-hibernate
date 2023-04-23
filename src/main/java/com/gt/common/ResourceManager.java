package com.gt.common;

import com.gt.common.constants.StrConstants;
import com.gt.common.utils.CryptographicUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * com.gt.common-ResourceManager.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public class ResourceManager {

    public static final String resourceMapFile = "string-resource.ini";
    private static final String a = "gt?Pass,e#. ";
    private static Map<String, String> stringConstantsMap;

    public static synchronized String getString(String key) {
        if (stringConstantsMap == null) {
            try {
                stringConstantsMap = readMap(resourceMapFile, false);
            } catch (Exception e) {
                stringConstantsMap = StrConstants.getMap();
            }
        }
        return stringConstantsMap.get(key);
    }

    public static Map<String, String> readMap(String file, boolean isEncry) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        Map<String, String> map = new HashMap<>();
        String str;
        while ((str = in.readLine()) != null) {
            String[] spl = str.split(":");
            if (isEncry) {
                map.put(CryptographicUtil.decryptText(spl[0].trim(), a), CryptographicUtil.decryptText(spl[1].trim(), a));
            } else {
                map.put(spl[0].trim(), spl[1].trim());
            }
        }
        in.close();
        return map;
    }

    public static Image getImage(String fileName) {
        // src/image/
        return readImage(ResourceManager.class.getResource("/images/" + fileName).toString());
    }

    public static ImageIcon getImageIcon(String resourcePath) {
        return new ImageIcon(ResourceManager.class.getResource("/images/" + resourcePath));
    }

    public static BufferedImage readImage(String imageName) {
        try {
            File input = new File(imageName);
            BufferedImage image = ImageIO.read(input);
            return image;
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        ImageIcon ic = new ImageIcon(ResourceManager.class.getResource("/images/logout-on.png"));
        ImageIcon ic2 = getImageIcon("logout-on.png");
        System.out.println(ic.getIconHeight());
        System.out.println(ic2.getIconHeight());
    }
}
