package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Specification;
import com.ca.db.service.DBUtils;
import com.gt.common.utils.StringUtils;
import com.gt.common.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class SpecificationPanel extends JPanel {

    private static final long serialVersionUID = 1859550688843095275L;
    private Category category;
    private List<CategoryEntryPair> pairList;
    private boolean skipNext = false;

    /**
     * @param id category id of which we want to display Specification entry
     *           panel
     */
    public SpecificationPanel(int id) {
        readList(id);
        setLayout(new FlowLayout());
        addCategoryPanels();
        setBorder(BorderFactory.createTitledBorder("Specification :  " + category.getCategoryName()));
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.getContentPane().add(new SpecificationPanel(1));
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static boolean isValidDataEntered() {
        return true;
    }

    private boolean toSkip(String spec) {

        if (!StringUtils.isEmpty(spec) && !skipNext) {
            skipNext = false;
        } else {
            skipNext = true;
        }
        System.out.println(skipNext + " skipnext..");
        return !skipNext;
    }

    public final void disableAll() {
        for (CategoryEntryPair pair : pairList) {
            pair.tb.setEnabled(false);
            pair.tb.setEditable(false);
            Color disabledColor = (Color) UIManager.get("TextField.inactiveBackground");
            pair.tb.setBackground(disabledColor);
        }
    }

    public final void resetAll() {
        for (CategoryEntryPair pair : pairList) {

            pair.tb.setText("");
        }
    }

    public final void enableAll() {
        UIUtils.toggleAllChildren(this, true);
    }

    private void addCategoryPanels() {
        pairList = new LinkedList<>();
        synchronized (pairList) {
            if (toSkip(category.getSpecification1()))
                pairList.add(new CategoryEntryPair(category.getSpecification1()));
            if (toSkip(category.getSpecification2()))
                pairList.add(new CategoryEntryPair(category.getSpecification2()));
            if (toSkip(category.getSpecification3()))
                pairList.add(new CategoryEntryPair(category.getSpecification3()));
            if (toSkip(category.getSpecification4()))
                pairList.add(new CategoryEntryPair(category.getSpecification4()));
            if (toSkip(category.getSpecification5()))
                pairList.add(new CategoryEntryPair(category.getSpecification5()));
            if (toSkip(category.getSpecification6()))
                pairList.add(new CategoryEntryPair(category.getSpecification6()));
            if (toSkip(category.getSpecification7()))
                pairList.add(new CategoryEntryPair(category.getSpecification7()));
            if (toSkip(category.getSpecification8()))
                pairList.add(new CategoryEntryPair(category.getSpecification8()));
            if (toSkip(category.getSpecification9()))
                pairList.add(new CategoryEntryPair(category.getSpecification9()));
            if (toSkip(category.getSpecification10()))
                pairList.add(new CategoryEntryPair(category.getSpecification10()));

            for (CategoryEntryPair pair : pairList) {
                add(pair);
            }
            if (pairList.size() == 0) {
                System.out.println("SpecificationPanel.addCategoryPanels() >>>>> spec size 0");
                this.setVisible(false);
                this.removeAll();
                add(new JLabel("No Specification was set for " + category.getCategoryName()));
                this.setVisible(true);
            } else {
                this.setVisible(true);
                setBorder(BorderFactory.createTitledBorder("Specification :  " + category.getCategoryName()));
            }
        }
    }

    /**
     * @return list of specifications (String) entered on displayed textfields
     */
    public final List<String> getSpecificationsStringList() {
        List<String> sps = new LinkedList<>();
        for (CategoryEntryPair pair : pairList) {
            System.out.println(">>>> " + pair.jl.getText());
            System.out.println("SpecificationPanel.getSpecificationsStringList() >>> " + pair.tb.getText());
            sps.add(pair.tb.getText().trim());
        }
        return sps;
    }

    public final Specification getSpecificationsObject() {
        Specification sps = new Specification();
        try {
            sps.setSpecification1(pairList.get(0).tb.getText().trim());
            sps.setSpecification2(pairList.get(1).tb.getText().trim());
            sps.setSpecification3(pairList.get(2).tb.getText().trim());
            sps.setSpecification4(pairList.get(3).tb.getText().trim());
            sps.setSpecification5(pairList.get(4).tb.getText().trim());
            sps.setSpecification6(pairList.get(5).tb.getText().trim());
            sps.setSpecification7(pairList.get(6).tb.getText().trim());
            sps.setSpecification8(pairList.get(7).tb.getText().trim());
            sps.setSpecification9(pairList.get(8).tb.getText().trim());
            sps.setSpecification10(pairList.get(9).tb.getText().trim());
        } catch (Exception e) {
            // no problem
        }
        return sps;
    }

    public final void populateValues(Specification spec) {
        try {
            pairList.get(0).tb.setText(spec.getSpecification1());
            pairList.get(1).tb.setText(spec.getSpecification2());
            pairList.get(2).tb.setText(spec.getSpecification3());
            pairList.get(3).tb.setText(spec.getSpecification4());
            pairList.get(4).tb.setText(spec.getSpecification5());
            pairList.get(5).tb.setText(spec.getSpecification6());
            pairList.get(6).tb.setText(spec.getSpecification7());
            pairList.get(7).tb.setText(spec.getSpecification8());
            pairList.get(8).tb.setText(spec.getSpecification9());
            pairList.get(9).tb.setText(spec.getSpecification10());

        } catch (Exception ignored) {
            // no problem
        }

    }

    private void readList(int id) {
        try {
            category = (Category) DBUtils.getById(Category.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class CategoryEntryPair extends JPanel {
        private static final long serialVersionUID = -623894842418823841L;
        final JLabel jl;
        final JTextField tb;

        CategoryEntryPair(String name) {
            jl = new JLabel(name);
            tb = new JTextField(8);
            setLayout(new FlowLayout());
            add(jl);
            add(tb);
        }

        public final String getValue() {
            return tb.getText().trim();
        }

        public final boolean isEmpty() {
            return (tb.getText().trim().equals(""));
        }
    }
}
