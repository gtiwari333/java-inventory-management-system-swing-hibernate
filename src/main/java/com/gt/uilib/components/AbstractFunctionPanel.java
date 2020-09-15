package com.gt.uilib.components;

import com.gt.common.constants.CommonConsts;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.inputverifier.Verifier;

import javax.swing.*;
import java.awt.*;

/**
 * com.gt.uilib.components-AbstractFunctionPanel.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public abstract class AbstractFunctionPanel extends JPanel implements Verifier {
    private static final long serialVersionUID = -5535283266424039078L;
    public boolean isReadyToClose = false;
    protected AppFrame mainApp;
    protected Status status;
    protected boolean debug;

    public AbstractFunctionPanel() {
        debug = AppFrame.debug;
        setBounds(100, 100, 450, 300);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CommonConsts.COLOR_TOOLBAR_BORDER));
    }

    /**
     * we can override this function to display different message
     */
    public static String getUnsavedExitMessage() {
        return "Are you sure to exit?";

    }

    // utility method to clear all editable text fields and combo boxes

    protected static void handleDBError(Exception e) {
        System.out.println("db error " + e.toString());
        e.printStackTrace();
        String expln = e.toString();

        String[] exp = expln.split("Exception:");
        System.err.println("Reason - " + exp[exp.length - 1]);
        JOptionPane.showMessageDialog(null, "DB Error" + e.getMessage(), "Error ! ", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * function name will be displayed in title bar
     */
    abstract public String getFunctionName();

    /**
     * initialize fields, set initial status, <br/>
     * it sh
     */
    public void init() {
        // UIUtils.updateFont(new Font("Arial", Font.PLAIN, 12), this);
        UIUtils.decorateBorders(this);
    }

    public final AppFrame getMainFrame() {
        return mainApp;
    }

    public final void validateFailed() {
        getMainFrame().getStatusLbl().setText("Please enter data properly before saving");

    }

    public void validatePassed() {

    }

    public final void changeStatus(Status status) {
        this.status = status;
        enableDisableComponents();
    }

    abstract public void handleSaveAction();

    abstract public void enableDisableComponents();
}
