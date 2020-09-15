package com.gt.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static boolean isEmpty(Date date) {
        return date == null;
    }

    public static String getTodayDate() {
        DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        return datef.format(date);
    }

    public static String getCvDateMMMddyyyy(Date date) {
        if (date == null) return "";
        DateFormat datef = new SimpleDateFormat("MMM dd, yyyy");
        return datef.format(date);
    }

    // FIXME: DO it on the basis of Nepali calendar
    public static int getCurrentFiscalYear() {
        return getYearFromYYYYMMDD(getTodayDate());
    }

    public static int getYearFromYYYYMMDD(String systemDStringRpgDateStr) {
        int year = Integer.parseInt(systemDStringRpgDateStr.substring(0, 4));
        return year;
    }

}
