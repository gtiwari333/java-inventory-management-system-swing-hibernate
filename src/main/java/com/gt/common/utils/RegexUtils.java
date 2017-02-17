package com.gt.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static final String TEXT_FIELD = "^(\\S)(.){1,75}(\\S)$";
    public static final String NON_NEGATIVE_INTEGER_FIELD = "(\\d){1,9}";
    public static final String INTEGER_FIELD = "(-)?" + NON_NEGATIVE_INTEGER_FIELD;
    public static final String NON_NEGATIVE_FLOATING_POINT_FIELD = "(\\d){1,10}\\.(\\d){1,10}";
    public static final String FLOATING_POINT_FIELD = "(-)?" + NON_NEGATIVE_FLOATING_POINT_FIELD;
    public static final String NON_NEGATIVE_MONEY_FIELD = "(\\d){1,15}(\\.(\\d){1,2})?";//XXXXXXXXXX.XX
    public static final String MONEY_FIELD = "(-)?" + NON_NEGATIVE_MONEY_FIELD;
    public static final String DATE_DDMMYYYY = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    public static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static final String HEX_COLOR_CODE = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    public static final String NON_EMPTY_ALPHA_NUMERIC_STRING = "\\w?";
    public static final String TELEPHONE_REGEX = "[0-9\\-]*";

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
