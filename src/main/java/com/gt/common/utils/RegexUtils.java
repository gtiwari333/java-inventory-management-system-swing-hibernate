package com.gt.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean matches(String input, String regex) {

        if (StringUtils.isEmpty(input)) {
            return false;
        }

        if (StringUtils.isEmpty(regex)) {
            return true;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
