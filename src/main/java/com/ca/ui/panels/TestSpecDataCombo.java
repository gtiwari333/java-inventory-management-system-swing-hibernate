package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.service.DBUtils;
import com.gt.uilib.components.input.DataComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestSpecDataCombo extends JPanel {
    private SpecificationPanel currentSpecificationPanel;
    private DataComboBox comboBox;
    private JPanel panel;
    private JLabel lblCategory;
    private JPanel panel_1;

    private TestSpecDataCombo() {
        initComponents();
        intCombo();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                TestSpecDataCombo panel = new TestSpecDataCombo();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.pack();
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void intCombo() {
        try {
            List<Category> cl = DBUtils.readAll(Category.class);
            for (Category c : cl) {
                comboBox.addRow(new Object[]{c.getId(), c.getCategoryName()});
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        comboBox.addItemListener(e -> {
            int id = comboBox.getSelectedId();
            getPanel().removeAll();
            currentSpecificationPanel = null;
            if (id > 0) {
                currentSpecificationPanel = new SpecificationPanel(id);
                getPanel().add(currentSpecificationPanel, FlowLayout.LEFT);
            }
            getPanel().repaint();
            getPanel().revalidate();

        });

    }

    private void initComponents() {
        setLayout(null);
        add(getPanel());
        add(getPanel_1());
    }

    private DataComboBox getComboBox() {
        if (comboBox == null) {
            comboBox = new DataComboBox();
            comboBox.setBounds(72, 5, 145, 20);
        }
        return comboBox;
    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setBounds(10, 56, 583, 89);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        }
        return panel;
    }

    private JLabel getLblCategory() {
        if (lblCategory == null) {
            lblCategory = new JLabel("Category :");
            lblCategory.setBounds(10, 8, 52, 14);
        }
        return lblCategory;
    }

    private JPanel getPanel_1() {
        if (panel_1 == null) {
            panel_1 = new JPanel();
            panel_1.setBounds(10, 11, 249, 34);
            panel_1.setLayout(null);
            panel_1.add(getLblCategory());
            panel_1.add(getComboBox());
        }
        return panel_1;
    }
}
