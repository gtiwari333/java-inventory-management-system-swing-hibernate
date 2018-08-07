package com.gt.common;

import com.gt.common.utils.StringUtils;

import java.io.File;
import java.net.URI;

public class GFile extends File {

    public GFile(String pathname) {

        super(getOSFileName(pathname));

    }

    public GFile(URI uri) {

        super(uri);

    }

    public GFile(String parent, String child) {

        super(getOSFileName(parent), getOSFileName(child));

    }

    public GFile(File parent, String child) {

        super(getOSFileName(parent.getAbsolutePath()), getOSFileName(child));

    }

    private static String getOSFileName(String filename) {

        if (filename != null) {

            if (System.getProperty("os.name").contains("Windows")) {

                if (filename.indexOf("/") == 0) {

                    int driveLetter = 1;
                    filename = filename.substring(driveLetter, driveLetter + 1) + ":" + filename.substring(driveLetter + 1);

                }

                filename = StringUtils.replace(filename, "/mnt/samba/", "\\\\");
                filename = StringUtils.replace(filename, "/", File.separator);

            } else {

                if (filename.contains(":")) {

                    filename = "/" + filename.substring(filename.indexOf(":") - 1);
                    filename = StringUtils.replace(filename, ":", "");

                }

                filename = StringUtils.replace(filename, "\\\\", "/mnt/samba/");
                filename = StringUtils.replace(filename, "\\", File.separator);

            }

        }

        return filename;
    }

}
