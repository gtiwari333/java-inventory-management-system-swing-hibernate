package com.gt.common.utils;

import com.gt.uilib.components.input.DataComboBox;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class UIUtils {
    private static final Color COMPONENT_BORDER_COLOR = Color.GRAY;

    public static boolean isEmpty(JTextComponent jt) {
        String str = jt.getText();
        if (StringUtils.isEmpty(str)) {
            return true;
        }

        return false;
    }

    public static void clearAllFields(Component parent, Component... ignoreList) {
        List<Component> list = null;

        if (ignoreList != null) {
            list = Arrays.asList(ignoreList);
        }

        if (parent instanceof JTextField) {
            ((JTextField) parent).setText("");
        } else if (parent instanceof JTextArea) {
            ((JTextArea) parent).setText("");
        } else if (parent instanceof JCheckBox) {
            ((JCheckBox) parent).setSelected(false);
        } else if (parent instanceof JComponent) {
            Component[] children = ((JComponent) parent).getComponents();
            for (Component aChildren : children) {
                if (list != null && list.contains(aChildren)) {
                    continue;
                }
                clearAllFields(aChildren);
            }
        }
    }

    public static void clearAllFields(Component parent) {
        if (parent instanceof JTextField) {
            ((JTextField) parent).setText("");
        }
        if (parent instanceof JTextArea) {
            if (((JTextArea) parent).isEditable()) {
                ((JTextArea) parent).setText("");
            }
        } else if (parent instanceof JPasswordField) {
            if (((JPasswordField) parent).isEditable()) {
                ((JPasswordField) parent).setText("");
            }
        } else if (parent instanceof DataComboBox) {
            ((DataComboBox) parent).selectDefaultItem();
        } else if (parent instanceof JComponent) {
            Component[] children = ((JComponent) parent).getComponents();
            for (Component aChildren : children) {
                clearAllFields(aChildren);
            }
        }
    }

    public static void toggleAllChildren(Component parent, boolean enabled, Component... ignoreList) {
        List<Component> igList = Arrays.asList(ignoreList);

        if (parent == null) {
            return;
        }
        Color disabledColor = (Color) UIManager.get("TextField.inactiveBackground");
        Color enabledColor = (Color) UIManager.get("TextField.background");
        if (!igList.contains(parent)) {
            parent.setEnabled(enabled);
            if (parent instanceof JTextComponent || parent instanceof JDateChooser || parent instanceof JComboBox) {
                if (!enabled) parent.setBackground(disabledColor);
                if (enabled) parent.setBackground(enabledColor);
            }
        }

        if (parent instanceof JComponent) {
            Component[] children = ((JComponent) parent).getComponents();
            for (Component aChildren : children) {
                if (aChildren instanceof JLabel) {
                    continue;
                }
                if (!igList.contains(aChildren)) {
                    toggleAllChildren(aChildren, enabled);
                }
            }
        }
    }

    public static void decorateBorders(JComponent p) {
        for (Component c : p.getComponents()) {
            if (c instanceof JToolBar) {
                continue;
            } else if (c instanceof JComboBox) {
                ((JComboBox) c).setBorder(BorderFactory.createLineBorder(COMPONENT_BORDER_COLOR, 1));
            } else if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;

                if (jc.getComponentCount() > 0) {
                    decorateBorders(jc);
                }
                if (jc instanceof JTextField) {
                    jc.setBorder(BorderFactory.createLineBorder(COMPONENT_BORDER_COLOR, 1));
                }
                if (jc instanceof JTextArea) {
                    jc.setBorder(BorderFactory.createLineBorder(COMPONENT_BORDER_COLOR, 1));
                }

                jc.updateUI();
            }
        }
    }
}
