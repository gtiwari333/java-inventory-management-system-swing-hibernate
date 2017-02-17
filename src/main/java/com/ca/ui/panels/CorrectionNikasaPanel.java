package com.ca.ui.panels;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ca.db.model.Nikasa;
import com.ca.db.service.DBUtils;
import com.ca.db.service.NikasaServiceImpl;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.NumberTextField;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

public class CorrectionNikasaPanel extends AbstractFunctionPanel {
    JLabel txtItemnmaa;
    JLabel txtCategoryr;
    JLabel txtKhatapananumbbber;
    Validator v;
    private JTextField txtNikasapananum;
    private JTextField txtRequestnum;
    private NumberTextField txtQty;
    private int currentNikasaId;
    private ItemReceiverPanel itemReceiverPanel;
    private JDateChooser nikasaDateChooser;
    private JPanel hastantaranStatus;
    private JRadioButton rdbtnHastantaranreceived;
    private JRadioButton rdbtnHastanNotReceived;

    public CorrectionNikasaPanel(int id) {

        this.currentNikasaId = id;

        getEditPanel();
        init();
        setSize(400, 370);

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame jf = new JFrame();
                    CorrectionNikasaPanel panel = new CorrectionNikasaPanel(2);
                    jf.setBounds(panel.getBounds());
                    jf.getContentPane().add(panel);
                    jf.setVisible(true);
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void init() {
        /* never forget to call super.init() */
        super.init();
        try {
            Nikasa nik = (Nikasa) DBUtils.getById(Nikasa.class, currentNikasaId);
            txtItemnmaa.setText(nik.getItem().getName());
            txtCategoryr.setText(nik.getItem().getCategory().getCategoryName());
            txtKhatapananumbbber.setText(nik.getItem().getKhataNumber() + " / " + nik.getItem().getPanaNumber());
            txtNikasapananum.setText(nik.getNikasaPanaNumber());
            txtRequestnum.setText(nik.getNikasaRequestNumber());
            nikasaDateChooser.setDate(nik.getNikasaDate());
            itemReceiverPanel.setCurrentType(nik);
            txtQty.setText(nik.getQuantity() + "");

            if (nik.getHastantaranReceivedStatus() == Nikasa.HASTANTARAN_NOT_RECEIVED) {
                rdbtnHastanNotReceived.setSelected(true);
            } else {
                rdbtnHastantaranreceived.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }

    }

    private void getEditPanel() {
        setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(135dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(37dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("max(43dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblItemName = new JLabel("Item Name");
        add(lblItemName, "4, 2");

        txtItemnmaa = new JLabel("-");
        add(txtItemnmaa, "8, 2");

        JLabel lblCategory = new JLabel("Category");
        add(lblCategory, "4, 4");

        txtCategoryr = new JLabel("-");
        add(txtCategoryr, "8, 4");

        JLabel lblKhatapanaNumber = new JLabel("Khata/Pana Number");
        add(lblKhatapanaNumber, "4, 6");

        txtKhatapananumbbber = new JLabel("KhataPanaNumbbber");
        add(txtKhatapananumbbber, "8, 6");

        JLabel lblNikasaNumber = new JLabel("Nikasa Pana Number");
        add(lblNikasaNumber, "4, 8");

        txtNikasapananum = new JTextField();
        txtNikasapananum.setText("NikasaPanaNum");
        add(txtNikasapananum, "8, 8, fill, default");
        txtNikasapananum.setColumns(10);

        JLabel lblItemRequestNumber = new JLabel("Item Request Number");
        add(lblItemRequestNumber, "4, 10");

        txtRequestnum = new JTextField();
        txtRequestnum.setText("RequestNum");
        add(txtRequestnum, "8, 10, fill, default");
        txtRequestnum.setColumns(10);

        JLabel lblQuantity = new JLabel("Quantity");
        add(lblQuantity, "4, 12");

        txtQty = new NumberTextField(6, true);

        add(txtQty, "8, 12, fill, default");

        JLabel lblDate = new JLabel("Date");
        add(lblDate, "4, 14");

        nikasaDateChooser = new JDateChooser();
        // txtDate.setText("Date");
        add(nikasaDateChooser, "8, 14, fill, default");

        JLabel lblReceiver = new JLabel("Receiver");
        add(lblReceiver, "4, 16");

        itemReceiverPanel = new ItemReceiverPanel();
        add(itemReceiverPanel, "8, 16, fill, fill");

        JLabel lblHastantaranStatus = new JLabel("Hastantaran Status");
        add(lblHastantaranStatus, "4, 18");

        hastantaranStatus = new JPanel();

        hastantaranStatus.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

        rdbtnHastantaranreceived = new JRadioButton("Received");
        hastantaranStatus.add(rdbtnHastantaranreceived, "1, 2");

        rdbtnHastanNotReceived = new JRadioButton("Not Received");
        hastantaranStatus.add(rdbtnHastanNotReceived, "1, 4");
        ButtonGroup bgHastan = new ButtonGroup();
        bgHastan.add(rdbtnHastantaranreceived);
        bgHastan.add(rdbtnHastanNotReceived);

        add(hastantaranStatus, "8, 18, fill, fill");

        JPanel panel = new JPanel();
        add(panel, "4, 20, 5, 1, fill, fill");
        panel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JButton btnReset = new JButton("Reset");
        panel.add(btnReset, "2, 2");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteAction();
            }
        });

        JButton btnSave = new JButton("Save");
        panel.add(btnSave, "4, 2");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSaveAction();
            }
        });
        panel.add(btnDelete, "18, 2");

    }

    protected void handleDeleteAction() {
        if (!DataEntryUtils.confirmDBDelete()) {
            return;
        }
        try {
            NikasaServiceImpl ns = new NikasaServiceImpl();
            ns.deleteNikasa(currentNikasaId);

            handleDeleteSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }
    }

    @Override
    public String getFunctionName() {
        return "Nikasa Correction and Edit, Hastantaran Register";
    }

    private boolean isValidData() {
        int qty = Integer.parseInt(txtQty.getText());

        if (qty > 0 && itemReceiverPanel.isSelected() && nikasaDateChooser.getDate() != null) {
            return true;
        }
        return false;

    }

    @Override
    public void handleSaveAction() {
        if (!isValidData()) {
            JOptionPane.showMessageDialog(null, "Please enter the required values before saving");
            return;
        }
        if (!DataEntryUtils.confirmDBUpdate()) {
            return;
        }
        try {

            int hastan = -1;
            if (rdbtnHastanNotReceived.isSelected()) {
                hastan = Nikasa.HASTANTARAN_NOT_RECEIVED;
            } else {
                hastan = Nikasa.HASTANTARAN_RECEIVED;
            }
            NikasaServiceImpl ns = new NikasaServiceImpl();
            ns.updateNikasa(currentNikasaId, Integer.parseInt(txtQty.getText()), nikasaDateChooser.getDate(), itemReceiverPanel.getCurrentType(),
                    itemReceiverPanel.getSelectedId(), txtNikasapananum.getText().trim(), txtRequestnum.getText().trim(), hastan);
            handleSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            handleDBError(e);
        }
    }

    public void handleSuccess() {
        JOptionPane.showMessageDialog(null, "Saved Successfully");
        Window w = SwingUtilities.getWindowAncestor(CorrectionNikasaPanel.this);
        w.setVisible(false);
    }

    public void handleDeleteSuccess() {
        JOptionPane.showMessageDialog(null, "Deleted Successfully");
        Window w = SwingUtilities.getWindowAncestor(CorrectionNikasaPanel.this);
        w.setVisible(false);
    }

    @Override
    public void enableDisableComponents() {

    }

}
