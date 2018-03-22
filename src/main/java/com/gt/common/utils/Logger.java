package com.gt.common.utils;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static String E = "ERROR";
    public static String F = "FATAL";
    public static String I = "INFO";
    public static String W = "WARNING";
    private static FileWriter writer;
    private static Logger logger;

    private Logger() {
    }

    private synchronized static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    private static FileWriter getWriter() throws Exception {
        if (writer == null) {
            writer = new FileWriter("errLog" + DateTimeUtils.getTodayDate() + ".txt", true);
        }
        return writer;
    }

    public static void L(String type, String message) {
        getLogger().logg(Logger.I, message);
    }

    public static void I(String message) {
        getLogger().logg(Logger.I, message);
    }

    public static void E(String message) {
        getLogger().logg(Logger.E, message);
    }

    public static void F(String message) {
        getLogger().logg(Logger.F, message);
    }

    public static void W(String message) {
        getLogger().logg(Logger.W, message);
    }

    public static boolean closeWriter() {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }

        }
        return true;

    }

    private void logg(String type, String message) {
        /**
         * at com.gt.common.utils.Logger.logg(Logger.java:53) at
         * com.gt.common.utils.Logger.I(Logger.java:36) at
         * com.gt.uilib.components.AppFrame.loginSuccess(AppFrame.java:180)
         * //-get third line
         */
        Throwable t = new Throwable();
        StackTraceElement traceLine = t.getStackTrace()[2];
        // t.printStackTrace();
        String className = traceLine.getClassName();
        String methodName = traceLine.getMethodName();
        int lineNumber = traceLine.getLineNumber();
        StringBuilder sb = new StringBuilder(120);
        sb.append("[").append(type).append("]");
        sb.append("\t").append(DateTimeUtils.getCurrentTimeHMS());
        sb.append("\t").append(className).append("\t").append(methodName);
        sb.append("\t").append(lineNumber);
        sb.append("\t").append(message).append("\n");
        write(sb.toString());
    }

    private static void write(String message) {

        try {
            getWriter();
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            closeWriter();
        }

    }
}
