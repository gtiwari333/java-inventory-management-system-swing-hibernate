package com.gt.common.utils;

public final class StringUtils {

    private StringUtils() {
    }

    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    public static String clean(String str) {
        return (str == null ? "" : str.trim());
    }

    public static String trim(String str) {
        return (str == null ? null : str.trim());
    }


    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() < 1) {
            return true;
        }
        return false;
    }


    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max) {
        if (text == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder(text.length());
        int start = 0, end;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text, start, end).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

}
