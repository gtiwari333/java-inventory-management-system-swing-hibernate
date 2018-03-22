package com.gt.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static boolean isEmpty(Date date) {
        return date == null;
    }

    public static String getCurrentTimeHMS() {

        DateFormat timef = new SimpleDateFormat("hh:mm:ss");
        Date date = Calendar.getInstance().getTime();
        return timef.format(date);
    }

    public static String getCurrentTimeHM() {

        DateFormat timef = new SimpleDateFormat("hh:mm");
        Date date = Calendar.getInstance().getTime();
        return timef.format(date);
    }

    public static String getCurrentTimeHMSA() {

        DateFormat timef = new SimpleDateFormat("hh:mm:ss a");
        Date date = Calendar.getInstance().getTime();
        return timef.format(date);
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

    public static String getCvDateMMDDYYYY(Date date) {
        if (date == null) return "";
        DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
        return datef.format(date);
    }

    // FIXME: DO it on the basis of Nepali calendar
    public static int getCurrentFiscalYear() {
        return getYearFromYYYYMMDD(getTodayDate());
    }

    public static String getMySqlDate(java.util.Date utildate) {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(utildate);
    }

    public static int getCurrentMonth() {
        String mon = "";
        DateFormat datef = new SimpleDateFormat("MM");
        Date date = Calendar.getInstance().getTime();
        mon = datef.format(date);
        return Integer.parseInt(mon);
    }

    public static int getCurrentYear() {
        String mon = "";
        DateFormat datef = new SimpleDateFormat("yyyy");
        Date date = Calendar.getInstance().getTime();
        mon = datef.format(date);
        return Integer.parseInt(mon);
    }

    public static int getDayFromYYYYMMDD(String systemDStringRpgDateStr) {
        int day = Integer.parseInt(systemDStringRpgDateStr.substring(6, 8));
        return day;
    }

    public static int getMonthFromYYYYMMDD(String systemDStringRpgDateStr) {
        int month = Integer.parseInt(systemDStringRpgDateStr.substring(4, 6));
        return month;
    }

    public static int getYearFromYYYYMMDD(String systemDStringRpgDateStr) {
        int year = Integer.parseInt(systemDStringRpgDateStr.substring(0, 4));
        return year;
    }

}
