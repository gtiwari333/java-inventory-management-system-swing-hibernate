package com.gt.uilib.inputverifier;

import com.ca.ui.panels.SpecificationPanel;
import com.gt.common.ResourceManager;
import com.gt.common.utils.RegexUtils;
import com.gt.common.utils.StringUtils;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.input.GTextArea;
import com.gt.uilib.components.input.NumberTextField;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.gt.uilib.inputverifier-Validator.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public class Validator {

    private List<Task> tasks;
    private Color color;
    private Window parent;
    private boolean isShowWarningDialog;
    private String dialogErrorMessage;

    // JComponent firstErrorField = null;

    public Validator(Window parent) {
        this(parent, false, "");

    }

    public Validator(Window parent, boolean isShowWarningDialog) {
        this(parent, isShowWarningDialog, "Please Enter All Required Data. Please Correct Before Proceeding");
    }

    public Validator(Window parent, boolean isShowWarningDialog, String dialogErrorMessage) {
        this.isShowWarningDialog = isShowWarningDialog;
        color = new Color(243, 255, 59);
        this.dialogErrorMessage = dialogErrorMessage;
        tasks = new ArrayList<>();
    }

    private synchronized void addTask(Validator.Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    /**
     * add new task to validator
     *
     * @param comp       input field
     * @param message    error message to display in popup (currently not supported)
     * @param regex      RegexUtils.XXXXX type of input to validate
     * @param isRequired is it required input field
     * @param showPopup  currenlty not used
     */
    public final synchronized void addTask(JComponent comp, String message, String regex, boolean isRequired, boolean showPopup) {
        addTask(new Task(comp, message, regex, isRequired, showPopup));

    }

    /**
     * add new task to validator , non required field
     *
     * @param comp    input field
     * @param message error message to display in popup (currently not supported)
     * @param regex   RegexUtils.XXXXX type of input to validate
     */
    public final void addTask(JComponent comp, String message, String regex) {
        addTask(new Task(comp, message, regex));
    }

    /**
     * add new task to validator
     *
     * @param comp       input field
     * @param message    error message to display in popup (currently not supported)
     * @param regex      RegexUtils.XXXXX type of input to validate
     * @param isRequired is it required input field
     */
    public final void addTask(JComponent comp, String message, String regex, boolean isRequired) {
        addTask(new Task(comp, message, regex, isRequired));
    }

    public final void resetErrors() {
        for (Validator.Task task : tasks) {
            task.hideErrorInfo();
        }
        tasks.clear();
    }

    public final boolean validate() {

        int errorCount = 0;
        int firstErrorCount = 0;
        // firstErrorField = null;
        for (Validator.Task task : tasks) {
            if (!validate(task)) {
                errorCount++;
                if (errorCount == 1) {
                    firstErrorCount = errorCount - 1;
                }
            }
        }

        if (isShowWarningDialog && errorCount > 0) {
            // System.out.println(firstErrorField);
            WarningDialog warn = new WarningDialog(dialogErrorMessage);
            warn.showWarning();
            // show focus in first component of validator task list
            JTextComponent jtc = (JTextComponent) tasks.get(firstErrorCount).comp;
            jtc.grabFocus();

        }
        System.out.println("validating ... " + errorCount);
        return (errorCount == 0);
    }

    protected final boolean validationCriteria(Task task) {

        JComponent jc = task.comp;

        if (jc instanceof NumberTextField) {
            NumberTextField numF = (NumberTextField) jc;
            return (numF.isNonZeroEntered());
        }
        // TextField, Combo..
        if (jc instanceof JTextComponent) {
            String input = ((JTextComponent) jc).getText();

            /*
              if value is empty and is not compulsory, then valid
             */
            if (StringUtils.isEmpty(input) && !task.isRequired) {
                return true;
            }

            /*
              if value is empty and is compulsory, the invalid
             */
            if (StringUtils.isEmpty(input) && task.isRequired) {
                return false;
            }

            if (RegexUtils.matches(input, task.regex)) {
                return true;
            }

            return false;

        }
        if (jc instanceof JDateChooser) {
            JDateChooser jdc = (JDateChooser) jc;
            Date inputDate = jdc.getDate();

            /*
              If this field can be empty and current input date is empty , then
              it is ok.
             */
            if (inputDate == null && !task.isRequired) {
                return true;
            }

            /*
              Input date should be in Specified format
             */
            String pattern = jdc.getDateFormatString();
            SimpleDateFormat format = new SimpleDateFormat(pattern);

            try {
                format.format(inputDate);
                return true;
            } catch (Exception e) {
                return false;
            }

        }
        if (jc instanceof SpecificationPanel) {
            return SpecificationPanel.isValidDataEntered();
        }
        if (jc instanceof DataComboBox) {
            DataComboBox dcb = (DataComboBox) jc;
            return dcb.isValidDataChoosen();
        }
        if (jc instanceof GTextArea) {
            GTextArea dcb = (GTextArea) jc;
            String input = dcb.getText();

            if (StringUtils.isEmpty(input) && !task.isRequired) {
                return true;
            }

            /*
              if value is empty and is compulsory, the invalid
             */
            if (StringUtils.isEmpty(input) && task.isRequired) {
                return false;
            }

            if (RegexUtils.matches(input, task.regex)) {
                return true;
            }
        }

        return false;
    }

    private boolean validate(Task task) {
        if (!validationCriteria(task)) {

            if (parent instanceof Verifier) ((Verifier) parent).validateFailed();

            task.showPopup();

            return false;
        }

        if (parent instanceof Verifier) ((Verifier) parent).validatePassed();

        return true;
    }

    private class WarningDialog extends JOptionPane {

        public WarningDialog(Object message) {
            super(message, JOptionPane.ERROR_MESSAGE);

        }

        public final void showWarning() {
            JDialog d = this.createDialog(parent, "Error");
            d.setVisible(true);
        }
    }

    private class Task {
        protected String regex;
        protected boolean isRequired;
        private JLabel image;
        private JLabel messageLabel;
        private JDialog popup;
        private JComponent comp;
        private boolean showPopup;

        /**
         * creates new validator task
         *
         * @param comp
         * @param message
         * @param regex
         * @param isRequired
         * @param showPopup
         */
        public Task(JComponent comp, String message, String regex, boolean isRequired, boolean showPopup) {
            super();
            this.comp = comp;

            this.regex = regex;
            this.isRequired = isRequired;
            this.showPopup = showPopup;
            if (showPopup) {
                popup = new JDialog(parent);

                messageLabel = new JLabel(message + " ");
                image = new JLabel(ResourceManager.getImageIcon("exclamation_mark_icon.png"));
                initPopupComponents();
            }

        }

        public Task(JComponent comp, String message, String regex, boolean isRequired) {
            this(comp, message, regex, isRequired, true);
        }

        public Task(JComponent comp, String message, String regex) {
            this(comp, message, regex, false, true);
        }

        private void initPopupComponents() {
            popup.getContentPane().setLayout(new FlowLayout());
            popup.setUndecorated(true);
            popup.getContentPane().setBackground(color);
            popup.getContentPane().add(image);
            popup.getContentPane().add(messageLabel);
            popup.setFocusableWindowState(false);
            popup.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                    popup.setVisible(false);
                }
            });
        }

        private void showPopup() {
            if (comp instanceof JTextComponent || comp instanceof JDateChooser)
                comp.setBackground(Color.PINK);
            // set background at combo
            if (comp instanceof DataComboBox) {
                DataComboBox dc = (DataComboBox) comp;
                dc.setBackground(Color.PINK);

            }
        }

        private void hideErrorInfo() {
            if (comp instanceof JTextComponent || comp instanceof JDateChooser || comp instanceof DataComboBox)
                comp.setBackground(Color.WHITE);
            if (showPopup) popup.setVisible(false);
        }
    }
}
