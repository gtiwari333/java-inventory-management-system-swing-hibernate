package com.ca.ui.panels;

import com.ca.db.model.BranchOffice;
import com.ca.db.service.DBUtils;
import com.gt.uilib.components.input.DataComboBox;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.util.List;

public class ItemReceiverPanel extends JPanel {

    private final DataComboBox dataCombo;
    private ReceiverType currentType = ReceiverType.OFFICIAL; //one and only type

    public ItemReceiverPanel() {
        setLayout(new FormLayout(new ColumnSpec[]{ColumnSpec.decode("max(38dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(55dlu;default)"),}, new RowSpec[]{
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

        dataCombo = new DataComboBox();
        add(dataCombo, "1, 4, 5, 1, fill, default");
        handleSelection();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        JFrame jf = new JFrame();
        jf.getContentPane().add(new ItemReceiverPanel());
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public final ReceiverType getCurrentType() {
        return currentType;
    }

    public final void clearAll() {
        dataCombo.removeAllItems();
        currentType = ReceiverType.OFFICIAL;
    }

    public final boolean isSelected() {
        return getSelectedId() > 0;
    }

    public final Integer getSelectedId() {
        if (currentType == ReceiverType.OFFICIAL) {
            return dataCombo.getSelectedId();
        }
        return -1;
    }

    private void handleSelection() {
        SwingUtilities.invokeLater(() -> {
            switch (currentType) {
                case OFFICIAL:
                    dataCombo.init();
                    dataCombo.setEnabled(true);
                    try {
                        List<BranchOffice> cl = DBUtils.readAll(BranchOffice.class);
                        for (BranchOffice c : cl) {
                            dataCombo.addRow(new Object[]{c.getId(), c.getName(), c.getAddress()});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    dataCombo.init();
                    dataCombo.setEnabled(false);
                    break;
            }

        });

    }

    public enum ReceiverType {
        OFFICIAL
    }

}
