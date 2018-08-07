package com.gt.uilib.resource;

import javax.swing.*;

public class Icons {
    public static final ImageIcon MAIN_ICON_0 = getIcon("/images/pomodoro16_0.png");
    public static final ImageIcon MAIN_ICON_12_5 = getIcon("/images/pomodoro16-12.5.png");
    public static final ImageIcon MAIN_ICON_25 = getIcon("/images/pomodoro16-25.png");
    public static final ImageIcon MAIN_ICON_37_5 = getIcon("/images/pomodoro16-37.5.png");
    public static final ImageIcon MAIN_ICON_50 = getIcon("/images/pomodoro16-50.png");
    public static final ImageIcon MAIN_ICON_62_5 = getIcon("/images/pomodoro16-62.5.png");
    public static final ImageIcon MAIN_ICON_75 = getIcon("/images/pomodoro16-75.png");
    public static final ImageIcon MAIN_ICON_87_5 = getIcon("/images/pomodoro16-87.5.png");
    public static final ImageIcon MAIN_ICON = getIcon("/images/pomodoro16.png");
    public static final ImageIcon[] MAIN_ICON_PROGRESSIVE = new ImageIcon[]{
            MAIN_ICON_0,
            MAIN_ICON_12_5,
            MAIN_ICON_25,
            MAIN_ICON_37_5,
            MAIN_ICON_50,
            MAIN_ICON_62_5,
            MAIN_ICON_75,
            MAIN_ICON_87_5,
            MAIN_ICON
    };
    public static final ImageIcon SPLASH_ICON = getIcon("/images/pomodoroTechnique128.png");
    public static final ImageIcon CREATE_ICON_ON = getIcon("/images/createButton2.png");

    private static ImageIcon getIcon(String resourcePath) {
//        return new ImageIcon(getResource(resourcePath));
        return null;
        //TODO:
    }
}
