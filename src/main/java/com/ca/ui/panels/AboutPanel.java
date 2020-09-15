package com.ca.ui.panels;


import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends AbstractFunctionPanel {
    public AboutPanel() {

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new FormLayout(new ColumnSpec[]{
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(231dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,},
                new RowSpec[]{
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(78dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}));

        JTextArea txtrHello = new JTextArea();
        txtrHello.setBackground(UIManager.getColor("Button.background"));
        txtrHello.setEditable(false);
        txtrHello.setFont(new Font("Courier New", Font.BOLD, 13));
        txtrHello.setText("Inventory management system for :\r\n" +
                ResourceManager.getString(StrConstants.APP_TITLE) +
                ",\r\n" +
                ResourceManager.getString(StrConstants.DEPARTMENT) +
                "\r\n\r\n" +
                "Developed by  : Ganesh Tiwari \r\nBlog  : http://ganeshtiwaridotcomdotnp.blogspot.com/\r\nSupport Email : gtiwari333@gmail.com");
        panel.add(txtrHello, "4, 4, 5, 3, fill, fill");
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                AboutPanel panel = new AboutPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public final String getFunctionName() {
        return "About ";
    }

    @Override
    public void handleSaveAction() {
    }

    @Override
    public void enableDisableComponents() {
    }
}
