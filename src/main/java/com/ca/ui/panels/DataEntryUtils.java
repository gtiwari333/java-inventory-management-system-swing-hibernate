package com.ca.ui.panels;

import javax.swing.*;

class DataEntryUtils {
    public static boolean confirmDBSave() {
        return confirm("Are you sure to save");
    }

    public static boolean confirmDBUpdate() {
        return confirm("Are you sure to modify");
    }

    public static boolean confirmDBDelete() {
        return confirm("Are you sure to delete");
    }

    private static boolean confirm(String msg) {
        int answer = JOptionPane.showConfirmDialog(null, msg, "Are you Sure ?", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            return true;
        }
        return false;
    }
}
