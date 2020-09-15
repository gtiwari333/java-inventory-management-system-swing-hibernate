package com.gt.uilib.components;

import com.ca.ui.panels.AboutPanel;
import com.ca.ui.panels.ChangePasswordPanel;
import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.gt.uilib.components.button.ActionButton;
import com.gt.uilib.components.button.ExitButton;
import com.gt.uilib.components.button.LogOutButton;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GT
 * <p>
 * Mar 7, 2012 com.ca.ui-AppFrame.java
 */
public class AppFrame extends JFrame {

    public static final String loginPanel = com.ca.ui.panels.LoginPanel.class.getName();
    public static AbstractFunctionPanel currentWindow;
    public static boolean isLoggedIn = false;
    public static boolean debug = true;
    static Logger logger = Logger.getLogger(AppFrame.class);
    private static AppFrame _instance;
    private static JMenuBar menuBar;
    private static JPanel bodyPanel;
    private static JPanel toolBarPanel;
    WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
        }
    };
    private JLabel statusLbl;

    private AppFrame() {
        setTitle(StrConstants.APP_TITLE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 850, 500);

        this.setIconImage(ResourceManager.getImage("logo2.png"));

        getContentPane().setLayout(new BorderLayout(0, 0));
        setJMenuBar(getMenuBarr());
        getContentPane().add(getStatusPanel(), BorderLayout.SOUTH);
        getContentPane().add(getBodyPanel(), BorderLayout.CENTER);
        getContentPane().add(getToolBarPanel(), BorderLayout.NORTH);

        addWindowListener(exitListener);

        currentWindow = getFunctionPanelInstance(loginPanel);
        setWindow(currentWindow);

        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();

        setMaximizedBounds(e.getMaximumWindowBounds());
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

    }

    public static AppFrame getInstance() {
        if (_instance == null) {
            _instance = new AppFrame();
        }
        return _instance;
    }

    public static void loginSuccess() {
        isLoggedIn = true;
        getInstance().setWindow(com.ca.ui.panels.HomeScreenPanel.class.getName());
        logger.info("logged in");
    }

    private static JPanel getBodyPanel() {
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(new JLabel("Hello"));

        return bodyPanel;
    }

    protected static AbstractFunctionPanel getFunctionPanelInstance(String className) {
        AbstractFunctionPanel object = null;
        try {
            object = (AbstractFunctionPanel) Class.forName(className).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println(e);
        }
        return object;
    }

    private JMenuBar getMenuBarr() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            /*
              First menu -
             */
            JMenu startMnu = new JMenu("Application");
            JMenuItem logOutMnuItem = new JMenuItem("Log Out");
            logOutMnuItem.addActionListener(e -> LogOutButton.handleLogout());
            startMnu.add(logOutMnuItem);
            JMenuItem exitMnuItem = new JMenuItem("Close");
            exitMnuItem.addActionListener(e -> ExitButton.handleExit());
            startMnu.add(exitMnuItem);
            menuBar.add(startMnu);
            /*
              Entry Menu
             */
            JMenu entryMenu = new JMenu("Entry");
            entryMenu.add(ActionMenuItem.create("New Item Entry", "sitementry", com.ca.ui.panels.ItemEntryPanel.class.getName()));
            entryMenu.add(new JSeparator());
            JMenu initRecordMenuSub = new JMenu("Initial Records");

            initRecordMenuSub.add(ActionMenuItem.create("Add Category", "a", com.ca.ui.panels.CategoryPanel.class.getName()));
            initRecordMenuSub.add(ActionMenuItem.create("Add Vendor", "vendor", com.ca.ui.panels.VendorPanel.class.getName()));
            initRecordMenuSub.add(ActionMenuItem.create("Add Branch Office", "vendor", com.ca.ui.panels.BranchOfficePanel.class.getName()));
            initRecordMenuSub.add(ActionMenuItem.create("Add New Unit Type", "a", com.ca.ui.panels.UnitsStringPanel.class.getName()));
            entryMenu.add(initRecordMenuSub);
            menuBar.add(entryMenu);

            /*
              Search
             */
            JMenu searchMnu = new JMenu("Search");
            searchMnu.add(ActionMenuItem.create("Stock Search", "sfind", com.ca.ui.panels.StockQueryPanel.class.getName()));
            menuBar.add(searchMnu);

            /*
              Tools Menu This ActionMenuItem should be displayed on JDialog
             */
            JMenu toolsMenu = new JMenu("Tools");
            JMenuItem jmChang = new JMenuItem("Change UserName/Password");
            jmChang.addActionListener(e -> {
                if (isLoggedIn) {
                    GDialog cd = new GDialog(AppFrame.this, "Change Username/Password", true);
                    ChangePasswordPanel vp = new ChangePasswordPanel();
                    cd.setAbstractFunctionPanel(vp, new Dimension(480, 340));
                    cd.setResizable(false);
                    cd.setVisible(true);
                }

            });
            toolsMenu.add(jmChang);
            menuBar.add(toolsMenu);
            /*
              Last Menu
             */
            JMenu helpMenu = new JMenu("Help");
            JMenuItem readmanualItem = new JMenuItem("Read Manual");
            helpMenu.add(readmanualItem);
            helpMenu.add(new JSeparator());
            JMenuItem supportMnu = new JMenuItem("Support");
            helpMenu.add(supportMnu);

            readmanualItem.addActionListener(e -> {
                try {
                    String cmd = "cmd.exe /c start ";
                    String file = "help.pdf";
                    Runtime.getRuntime().exec(cmd + file);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(AppFrame.this, "Could not open help file " + e2.getMessage(), "Error opening file",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            supportMnu.addActionListener(e -> {
                GDialog cd = new GDialog(AppFrame.this, "About/Support", true);
                AboutPanel vp = new AboutPanel();
                cd.setAbstractFunctionPanel(vp, new Dimension(400, 190));
                cd.setVisible(true);

            });
            menuBar.add(helpMenu);
        }
        return menuBar;

    }

    private JPanel getStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.add(getStatusLbl());
        return statusPanel;
    }

    public final JLabel getStatusLbl() {
        if (statusLbl == null) {
            statusLbl = new JLabel("-(:::)-");
        }
        return statusLbl;

    }

    public final void setWindow(String curQfn) {
        AbstractFunctionPanel cur = getFunctionPanelInstance(curQfn);
        if (cur != null && isLoggedIn) {
            if (!currentWindow.getFunctionName().equals(cur.getFunctionName())) {
                setWindow(cur);
            }

        }
        if (!isLoggedIn) {
            JOptionPane.showMessageDialog(null, "You must Log in First");
        }
    }

    private void setWindow(final AbstractFunctionPanel next) {
        SwingUtilities.invokeLater(() -> {
            currentWindow = next;
            bodyPanel.removeAll();
            bodyPanel.add(next, BorderLayout.CENTER);
            bodyPanel.revalidate();
            bodyPanel.repaint();
            setTitle(StrConstants.APP_TITLE + " : " + next.getFunctionName());
            next.init();
        });

    }

    private JPanel getToolBarPanel() {
        if (toolBarPanel == null) {
            toolBarPanel = new JPanel();
            toolBarPanel.setLayout(new BorderLayout(20, 10));

            List<JLabel> buttons = new ArrayList<>();
            buttons.add(ActionButton.create("HOME", "home", com.ca.ui.panels.HomeScreenPanel.class.getName()));
            buttons.add(ActionButton.create("Stock Query", "find", com.ca.ui.panels.StockQueryPanel.class.getName()));
            buttons.add(ActionButton.create("Item Entry", "itementry", com.ca.ui.panels.ItemEntryPanel.class.getName()));
            buttons.add(ActionButton.create("Transfer", "itemtransfer", com.ca.ui.panels.ItemTransferPanel.class.getName()));
            buttons.add(ActionButton.create("Return", "return", com.ca.ui.panels.ItemReturnPanel.class.getName()));
            buttons.add(new JLabel());
            buttons.add(LogOutButton.create("Logout", "logout", com.ca.ui.panels.HomeScreenPanel.class.getName()));
            buttons.add(ExitButton.create("Exit", "exit", com.ca.ui.panels.HomeScreenPanel.class.getName()));

            toolBarPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            toolBarPanel.setPreferredSize(new Dimension(getWidth(), 80));
            toolBarPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0.5;
            for (JLabel button : buttons) {
                toolBarPanel.add(button, c);
                c.gridx++;
            }
        }
        return toolBarPanel;
    }

    public final void handleLogOut() {
        setWindow(AppFrame.loginPanel);
        isLoggedIn = false;

    }

}
