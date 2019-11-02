package com.ca.ui.panels;

import com.ca.db.model.BranchOffice;
import com.ca.db.model.Person;
import com.ca.db.model.Transfer;
import com.ca.db.service.DBUtils;
import com.gt.uilib.components.input.DataComboBox;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemReceiverPanel extends JPanel {

    private final DataComboBox dataCombo;
    private final ButtonGroup bg;
    private ReceiverType currentType;
    private final JRadioButton rdbtnPersonnal;
    private final JRadioButton rdbtnOfficial;
    private final JRadioButton rdbtnLilam;
    private final ActionListener radioButtonAL = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
//			System.out.println("Selected: " + aButton.getText());
            if (aButton == rdbtnOfficial) {
                currentType = ReceiverType.OFFICIAL;
            } else if (aButton == rdbtnPersonnal) {
                currentType = ReceiverType.PERSONNAL;
            } else if (aButton == rdbtnLilam) {
                currentType = ReceiverType.LILAM;
            }
//			System.out.println(" current type >> " + currentType);
            handleSelection();

        }
    };

    public ItemReceiverPanel() {
        setLayout(new FormLayout(new ColumnSpec[]{ColumnSpec.decode("max(38dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(55dlu;default)"),}, new RowSpec[]{
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

        rdbtnPersonnal = new JRadioButton("Personnal");
        rdbtnPersonnal.addActionListener(radioButtonAL);
        add(rdbtnPersonnal, "1, 2");

        rdbtnOfficial = new JRadioButton("Official");
        rdbtnOfficial.addActionListener(radioButtonAL);
        add(rdbtnOfficial, "3, 2");

        rdbtnLilam = new JRadioButton("Lilam");
        add(rdbtnLilam, "5, 2");
        rdbtnLilam.addActionListener(radioButtonAL);
        bg = new ButtonGroup();
        bg.add(rdbtnPersonnal);

        bg.add(rdbtnOfficial);
        bg.add(rdbtnLilam);

        dataCombo = new DataComboBox();
        add(dataCombo, "1, 4, 5, 1, fill, default");

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame jf = new JFrame();
        jf.getContentPane().add(new ItemReceiverPanel());
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public final ReceiverType getCurrentType() {
//		System.out.println("ItemReceiverPanel.getCurrentType() " + currentType);
        return currentType;
    }

    public final void setCurrentType(Transfer nik) {
        currentType = getType(nik.getTransferType());
        handleSelection();

    }

    public final int getCurrentReceiverConstant() {
        if (getCurrentType() == ReceiverType.PERSONNAL) {
            return Transfer.PERSONNAL;
        }
        if (getCurrentType() == ReceiverType.OFFICIAL) {
            return Transfer.OFFICIAL;
        }
        if (getCurrentType() == ReceiverType.LILAM) {
            return Transfer.LILAM;
        }
        return -1;
    }

    public final void disableAll() {
        dataCombo.setEnabled(false);
        rdbtnPersonnal.setEnabled(false);
        rdbtnOfficial.setEnabled(false);
        rdbtnLilam.setEnabled(false);
    }

    private static ReceiverType getType(int type) {
        switch (type) {
            case Transfer.OFFICIAL:
                return ReceiverType.OFFICIAL;

            case Transfer.PERSONNAL:
                return ReceiverType.PERSONNAL;

            case Transfer.LILAM:
                return ReceiverType.LILAM;

            default:
                return ReceiverType.NONE;

        }
    }

    public final void hideLilam() {
        rdbtnLilam.setVisible(false);
    }

    public final void clearAll() {
        bg.clearSelection();
        dataCombo.removeAllItems();
        currentType = ReceiverType.NONE;
    }

    public final boolean isSelected() {
        return getSelectedId() > 0;
    }

    public final Integer getSelectedId() {
        if (currentType == ReceiverType.OFFICIAL) {
            return dataCombo.getSelectedId();
        }
        if (currentType == ReceiverType.PERSONNAL) {
            return dataCombo.getSelectedId();
        }
        if (currentType == ReceiverType.LILAM) {
            return 999;
        }
        return -1;
    }

    private void handleSelection() {
        SwingUtilities.invokeLater(() -> {
            switch (currentType) {
                case OFFICIAL:
                    dataCombo.init();
                    dataCombo.setEnabled(true);
                    rdbtnOfficial.setSelected(true);
                    try {
                        List<BranchOffice> cl = DBUtils.readAll(BranchOffice.class);
                        for (BranchOffice c : cl) {
                            dataCombo.addRow(new Object[]{c.getId(), c.getName(), c.getAddress()});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // dataCombo.setEditable(false);
                    break;
                case PERSONNAL:

                    dataCombo.init();
                    // FIXME: filter fix
                    // dataCombo.setEditable(true);
                    rdbtnPersonnal.setSelected(true);
                    dataCombo.setEnabled(true);
                    try {
                        List<Person> cl = DBUtils.readAll(Person.class);
                        for (Person c : cl) {
                            dataCombo.addRow(new Object[]{c.getId(), c.getFirstName(), c.getLastName()});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // dataCombo.initArray();
                    break;
                default:
                    rdbtnLilam.setSelected(true);
                    dataCombo.init();
                    dataCombo.setEnabled(false);
                    break;
            }

        });

    }

    public enum ReceiverType {
        PERSONNAL, OFFICIAL, LILAM, NONE
    }

}
