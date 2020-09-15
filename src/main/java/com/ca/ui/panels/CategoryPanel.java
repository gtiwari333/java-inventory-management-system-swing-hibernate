package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.StringUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends AbstractFunctionPanel {
    // --
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Type", "Specification1", "Specification2", "Specification3", "Specification4",
            "Specification5", "Specification6", "Specification7", "Specification8", "Specification9", "Specification10"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JButton btnReadAll;
    private JButton btnNew;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private int editingPrimaryId = 0;
    private JButton btnCancel;
    private JTextField sp1;
    private JTextField sp2;
    private JTextField sp3;
    private JTextField sp4;
    private JTextField sp5;
    private JTextField sp6;
    private JTextField sp7;
    private JTextField sp8;
    private JTextField sp9;
    private JTextField sp10;
    private JTextField txtName;
    private JRadioButton rdbtnReturnable;
    private JRadioButton rdbtnNonReturnable;

    public CategoryPanel() {
        /*
          all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.1);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(0);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());

        /*
          never forget to call after setting up UI
         */
        init();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                CategoryPanel panel = new CategoryPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean checkEntryOrder(List<String> strL) {
        boolean isEmptyFind = false;
        for (String st : strL) {
            if (!isEmptyFind && StringUtils.isEmpty(st)) {
                isEmptyFind = true;
            }
            if (isEmptyFind && !StringUtils.isEmpty(st)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public final void init() {
        /* never forget to call super.init() */
        super.init();
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            btnReadAll = new JButton("Read All");
            btnReadAll.addActionListener(e -> {
                readAndShowAll(true);
                changeStatus(Status.READ);
            });
            buttonPanel.add(btnReadAll);

            btnNew = new JButton("New");
            btnNew.addActionListener(e -> changeStatus(Status.CREATE));
            buttonPanel.add(btnNew);

            JButton btnDeleteThis = new JButton("Delete This");
            btnDeleteThis.addActionListener(e -> {
                if (editingPrimaryId > 0) handleDeleteAction();
            });

            JButton btnModify = new JButton("Modify");
            btnModify.addActionListener(e -> {
                if (editingPrimaryId > 0) changeStatus(Status.MODIFY);
            });
            buttonPanel.add(btnModify);
            buttonPanel.add(btnDeleteThis);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> changeStatus(Status.READ));
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }

    public final void changeStatusToCreate() {
        changeStatus(Status.CREATE);
        readAndShowAll(false);
    }

    private void handleDeleteAction() {
        if (status == Status.READ) {
            if (DataEntryUtils.confirmDBDelete()) deleteSelectedBranchOffice();
        }

    }

    private void deleteSelectedBranchOffice() {
        try {
            DBUtils.deleteById(Category.class, editingPrimaryId);
            changeStatus(Status.READ);
            JOptionPane.showMessageDialog(null, "Deleted");
            readAndShowAll(false);
        } catch (Exception e) {
            handleDBError(e);
        }
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.clearAllFields(formPanel);
                btnReadAll.setEnabled(true);
                btnNew.setEnabled(true);
                table.setEnabled(true);
                break;
            case CREATE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, true);
                table.setEnabled(false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                break;
            case MODIFY:
                UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(buttonPanel, false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                table.setEnabled(false);

                break;

            case READ:
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.clearAllFields(formPanel);
                table.clearSelection();
                table.setEnabled(true);
                editingPrimaryId = -1;
                btnCancel.setEnabled(false);
                break;

            default:
                break;
        }
    }

    @Override
    public final void handleSaveAction() {
        switch (status) {
            case CREATE:
                // create new
                save(false);
                break;
            case MODIFY:
                // modify
                if (DataEntryUtils.confirmDBUpdate()) save(true);
                break;

            default:
                break;
        }
    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }

        v = new Validator(mainApp, true);
        v.addTask(txtName, "Name is Required", null, true);
        v.addTask(sp1, "At Least one Spec. is needed", null, true);

    }

    private Category getModelFromForm() {
        Category bo = new Category();
        bo.setCategoryName(txtName.getText().trim());
        bo.setSpecification1(sp1.getText().trim());
        bo.setSpecification2(sp2.getText().trim());
        bo.setSpecification3(sp3.getText().trim());
        bo.setSpecification4(sp4.getText().trim());
        bo.setSpecification5(sp5.getText().trim());
        bo.setSpecification6(sp6.getText().trim());
        bo.setSpecification7(sp7.getText().trim());
        bo.setSpecification8(sp8.getText().trim());
        bo.setSpecification9(sp9.getText().trim());
        bo.setSpecification10(sp10.getText().trim());
        bo.setdFlag(1);
        // at least one is already selected
        System.out.println(rdbtnReturnable.isSelected() + " expendable selected ??");
        if (rdbtnReturnable.isSelected()) {
            bo.setCategoryType(Category.TYPE_RETURNABLE);
        } else {
            bo.setCategoryType(Category.TYPE_NON_RETURNABLE);
        }
        return bo;
    }

    private void setModelIntoForm(Category bro) {
        txtName.setText(bro.getCategoryName());
        sp1.setText(bro.getSpecification1());
        sp2.setText(bro.getSpecification2());
        sp3.setText(bro.getSpecification3());
        sp4.setText(bro.getSpecification4());
        sp5.setText(bro.getSpecification5());
        sp6.setText(bro.getSpecification6());
        sp7.setText(bro.getSpecification7());
        sp8.setText(bro.getSpecification8());
        sp9.setText(bro.getSpecification9());
        sp10.setText(bro.getSpecification10());
        System.out.println(bro.getCategoryType() + " TYPE_KHARCHA_HUNE");
        // type
        if (bro.getCategoryType() == Category.TYPE_NON_RETURNABLE) {
            rdbtnNonReturnable.setSelected(true);
        } else {
            rdbtnReturnable.setSelected(true);
        }
    }

    private boolean isThereNoDiscontinuity() {
        List<String> li = new ArrayList<>();
        li.add(sp1.getText());
        li.add(sp2.getText());
        li.add(sp3.getText());
        li.add(sp4.getText());
        li.add(sp5.getText());
        li.add(sp6.getText());
        li.add(sp7.getText());
        li.add(sp8.getText());
        li.add(sp9.getText());
        li.add(sp10.getText());
        return checkEntryOrder(li);
    }

    private boolean isCategoryTypeSelected() {
        if (!rdbtnReturnable.isSelected() && !rdbtnNonReturnable.isSelected()) {
            return false;
        }
        return true;
    }

    private void save(boolean isModified) {
        initValidator();
        if (!v.validate() || !isCategoryTypeSelected()) {
            return;
        }

        if (isThereNoDiscontinuity()) {
            try {

                Category newBo = getModelFromForm();
                if (isModified) {
                    Category bo = (Category) DBUtils.getById(Category.class, editingPrimaryId);
                    bo.setCategoryName(newBo.getCategoryName());
                    bo.setCategoryType(newBo.getCategoryType());
                    bo.setSpecification1(newBo.getSpecification1());
                    bo.setSpecification2(newBo.getSpecification2());
                    bo.setSpecification3(newBo.getSpecification3());
                    bo.setSpecification4(newBo.getSpecification4());
                    bo.setSpecification5(newBo.getSpecification5());
                    bo.setSpecification6(newBo.getSpecification6());
                    bo.setSpecification7(newBo.getSpecification7());
                    bo.setSpecification8(newBo.getSpecification8());
                    bo.setSpecification9(newBo.getSpecification9());
                    bo.setSpecification10(newBo.getSpecification10());
                    DBUtils.saveOrUpdate(bo);
                } else {
                    newBo.setdFlag(1);
                    DBUtils.saveOrUpdate(newBo);
                }
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                changeStatus(Status.READ);
                UIUtils.clearAllFields(upperPane);
                readAndShowAll(false);
            } catch (Exception e) {
                handleDBError(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Enter Properly");
        }
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Category Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(119dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,},
                    new RowSpec[]{
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            RowSpec.decode("max(21dlu;default)"),
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 2");

            txtName = new JTextField();
            formPanel.add(txtName, "8, 2, fill, default");
            txtName.setColumns(10);

            JLabel lblType = new JLabel("Type");
            formPanel.add(lblType, "4, 4");

            JPanel typePanel = new JPanel();
            formPanel.add(typePanel, "8, 4, fill, fill");

            rdbtnReturnable = new JRadioButton("Returnable");
            typePanel.add(rdbtnReturnable);

            rdbtnNonReturnable = new JRadioButton("Non-Returnable");
            typePanel.add(rdbtnNonReturnable);

            ButtonGroup bg = new ButtonGroup();
            bg.add(rdbtnReturnable);
            bg.add(rdbtnNonReturnable);

            JLabel lblSpecification = new JLabel("Specification1");
            formPanel.add(lblSpecification, "4, 6");

            sp1 = new JTextField();
            formPanel.add(sp1, "8, 6, fill, default");
            sp1.setColumns(10);

            JLabel lblSpecification_1 = new JLabel("Specification2");
            formPanel.add(lblSpecification_1, "4, 8");

            sp2 = new JTextField();
            formPanel.add(sp2, "8, 8, fill, default");
            sp2.setColumns(10);

            JLabel lblSpecification_2 = new JLabel("Specification3");
            formPanel.add(lblSpecification_2, "4, 10");

            sp3 = new JTextField();
            formPanel.add(sp3, "8, 10, fill, default");
            sp3.setColumns(10);

            JLabel lblSpecification_3 = new JLabel("Specification4");
            formPanel.add(lblSpecification_3, "4, 12");

            sp4 = new JTextField();
            formPanel.add(sp4, "8, 12, fill, default");
            sp4.setColumns(10);

            JLabel lblSpecification_4 = new JLabel("Specification5");
            formPanel.add(lblSpecification_4, "4, 14");

            sp5 = new JTextField();
            formPanel.add(sp5, "8, 14, fill, default");
            sp5.setColumns(10);

            JLabel lblSpecification_5 = new JLabel("Specification6");
            formPanel.add(lblSpecification_5, "4, 16");

            sp6 = new JTextField();
            formPanel.add(sp6, "8, 16, fill, default");
            sp6.setColumns(10);

            JLabel lblSpecification_6 = new JLabel("Specification7");
            formPanel.add(lblSpecification_6, "4, 18");

            sp7 = new JTextField();
            formPanel.add(sp7, "8, 18, fill, default");
            sp7.setColumns(10);

            JLabel lblSpecification_7 = new JLabel("Specification8");
            formPanel.add(lblSpecification_7, "4, 20");

            sp8 = new JTextField();
            formPanel.add(sp8, "8, 20, fill, default");
            sp8.setColumns(10);

            JLabel lblSpecification_8 = new JLabel("Specification9");
            formPanel.add(lblSpecification_8, "4, 22");

            sp9 = new JTextField();
            formPanel.add(sp9, "8, 22, fill, default");
            sp9.setColumns(10);

            JLabel lblSpecification_9 = new JLabel("Specification10");
            formPanel.add(lblSpecification_9, "4, 24");

            sp10 = new JTextField();
            formPanel.add(sp10, "8, 24, fill, default");
            sp10.setColumns(10);

            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                handleSaveAction();
                btnSave.setEnabled(true);
            });
            formPanel.add(btnSave, "10, 24, right, default");
        }
        return formPanel;
    }

    private void readAndShowAll(boolean showSize0Error) {
        try {
            List<Category> brsL = DBUtils.readAll(Category.class);
            editingPrimaryId = -1;
            if (brsL == null || brsL.size() == 0) {
                if (showSize0Error) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
            }
            showBranchOfficesInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void showBranchOfficesInGrid(List<Category> brsL) {
        dataModel.resetModel();
        int sn = 0;
        String type;
        for (Category bo : brsL) {
            System.out.println(bo.getCategoryType() + "  >> ");
            if (bo.getCategoryType() == Category.TYPE_NON_RETURNABLE) {
                type = "Non Returnable";
            } else {
                type = "Returnable";
            }
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getCategoryName(), type, bo.getSpecification1(), bo.getSpecification2(),
                    bo.getSpecification3(), bo.getSpecification4(), bo.getSpecification5(), bo.getSpecification6(), bo.getSpecification7(),
                    bo.getSpecification8(), bo.getSpecification9(), bo.getSpecification10()});
        }
        // table.setTableHeader(tableHeader);
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

    @Override
    public final String getFunctionName() {
        return "New Item Category Add/View/Edit";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.WEST);
            upperPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return upperPane;
    }

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setLayout(new BorderLayout());
            dataModel = new EasyTableModel(header);

            table = new BetterJTable(dataModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            lowerPane.add(sp, BorderLayout.CENTER);
            table.getSelectionModel().addListSelectionListener(e -> {
                int selRow = table.getSelectedRow();
                if (selRow != -1) {
                    /*
                      if second column doesnot have primary id info, then
                     */
                    int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
                    populateSelectedRowInForm(selectedId);
                }
            });
        }
        return lowerPane;
    }

    private void populateSelectedRowInForm(int selectedId) {
        try {
            Category bro = (Category) DBUtils.getById(Category.class, selectedId);
            if (bro != null) {
                setModelIntoForm(bro);
                editingPrimaryId = bro.getId();
            }
        } catch (Exception e) {
            handleDBError(e);
        }

    }
}
