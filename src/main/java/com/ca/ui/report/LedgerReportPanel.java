package com.ca.ui.report;

import com.ca.db.model.Person;
import com.ca.db.service.DBUtils;
import com.ca.db.service.LedgerReportServiceImpl;
import com.ca.ui.panels.SpecificationPanel;
import com.gt.common.constants.Status;
import com.gt.common.utils.JrUtils;
import com.gt.common.utils.StringUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerReportPanel extends AbstractFunctionPanel {
    private static final String LEDGER_FILE_NAME_MULTIPLE_KHATA_PANA = "ledger-multiple-khata-pana.jrxml";
    private static final String LEDGER_FILE_NAME_SINGLE_PANA = "ledger-single-pana.jrxml";
    JPanel formPanel = null;
    Validator v;
    SpecificationPanel currentSpecificationPanel;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private JLabel lblPanaNumber;
    private JTextField txtPanaNumber;
    private JLabel lblItemName;
    private JTextField txtKhataNumber;
    private JButton btnReset;
    private JPanel viewerPanel;

    public LedgerReportPanel() {
        /**
         * all gui components added from here;
         */
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.01);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        splitPane.setDividerSize(0);
        /**
         * never forget to call after setting up UI
         */
        init();
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
                LedgerReportPanel panel = new LedgerReportPanel();
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
    public final void init() {
        /* never forget to call super.init() */
        super.init();
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.clearAllFields(formPanel);
                btnSave.setEnabled(true);
                break;

            case READ:
                UIUtils.clearAllFields(formPanel);
                break;

            default:
                break;
        }
    }

    @Override
    public void handleSaveAction() {

    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Inventory Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(115dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(125dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,}));

            lblItemName = new JLabel("Khata Number");
            formPanel.add(lblItemName, "4, 2");

            txtKhataNumber = new JTextField();
            formPanel.add(txtKhataNumber, "8, 2, fill, default");
            txtKhataNumber.setColumns(10);

            lblPanaNumber = new JLabel("Pana Number");
            formPanel.add(lblPanaNumber, "12, 2");

            txtPanaNumber = new JTextField();
            formPanel.add(txtPanaNumber, "16, 2, fill, default");
            txtPanaNumber.setColumns(10);

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> showLedger());

            formPanel.add(btnSave, "18, 2");

            btnReset = new JButton("Reset");
            formPanel.add(btnReset, "20, 2");
        }
        return formPanel;
    }

    private void showLedger() {

        String khataNum = txtKhataNumber.getText().trim();
        String panaNum = txtPanaNumber.getText().trim();

        if (StringUtils.isEmpty(khataNum) && !StringUtils.isEmpty(panaNum)) {
            JOptionPane.showMessageDialog(null, "If pana number is specified, khata number must be given ", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // either value entered at khatanumber only or both or blank is valid
        // if khata number is entered, it is not single pana
        // if khata number and pana number both entered, then single pana number

        boolean isSinglePanaLedger = false;
        if (!StringUtils.isEmpty(panaNum)) {
            // show pana number and item name at header
            isSinglePanaLedger = true;
        } else {
            // pana/khata num at separate column, remove item name .. from
            // header
            isSinglePanaLedger = false;
        }

        try {
            LedgerReportServiceImpl lrs = new LedgerReportServiceImpl();
            List<ReportBean> rpbl = lrs.getLedger(txtKhataNumber.getText().trim(), txtPanaNumber.getText().trim(), -1, -1, null, null);

            if (rpbl == null || rpbl.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nothing to display", "No records", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Map<String, String> parameters = new HashMap<>();
            viewerPanel.removeAll();

            if (isSinglePanaLedger) {
                //show khata pana + item name at header
                parameters.put("khataPanaNum", rpbl.get(0).getKhataPanaNumber());
                System.out.println("LedgerReportPanel.showLedger()   rpbl.get(0).getGoodsName() " + rpbl.get(0).getGoodsName());
                parameters.put("itemName", rpbl.get(0).getGoodsName());
                viewerPanel.add(JrUtils.getJrViewerReport(rpbl, LEDGER_FILE_NAME_SINGLE_PANA, "Ledger Report - " + rpbl.get(0).getGoodsName()
                        + rpbl.get(0).getKhataPanaNumber(), parameters));

            } else {
                viewerPanel.add(JrUtils.getJrViewerReport(rpbl, LEDGER_FILE_NAME_MULTIPLE_KHATA_PANA, "Ledger Report (Multiple Pana)", parameters));

            }

            viewerPanel.revalidate();
            viewerPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Could not show Leger", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static List<ReportBean> getRandom() {
        List<ReportBean> rpbl = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ReportBean rp = new ReportBean();
            rp.setDate("Date " + i);
            rp.setEntryFormId("entryFormId " + i);
            rp.setGoodsName("goodsName " + i);
            rp.setInQty("Qty " + i);
            rp.setInRate("inRate " + i);
            rp.setInRate("inRate " + i);
            rp.setInTotal("inTotal " + i);
            rp.setTransferBranch("transferBranch xxxx xxxxx xxxx xxx" + i);
            rp.setNikQty("nikQty " + i);
            rp.setNikRate("nikRate " + i);
            rp.setNikTotal("nikTotal " + i);
            rp.setNotes("notes " + i);
            rp.setRemQty("remQty " + i);
            rp.setReqFormId("reqFormId " + i);
            rp.setSpecification("specification specification specification specification" + i);
            rp.setSupplier("supplier " + i);
            rp.setRemTot("Remaining Total" + i);
            rpbl.add(rp);
        }
        return rpbl;
    }

    private void readAndShowAl00l(boolean showSize0Error) {
        try {
            List cats = DBUtils.readAll(Person.class);
            Map parameters = new HashMap();
            parameters.put("Report Title", "test title");
            parameters.put("Page Header1", "header1");
            parameters.put("Page Header2", "header2");
            viewerPanel.removeAll();
            viewerPanel.add(JrUtils.getJrViewerReport(cats, "testReport.jrxml", "Ledger Report", parameters));
            viewerPanel.revalidate();
            viewerPanel.repaint();

        } catch (Exception e) {
        }
    }

    @Override
    public final String getFunctionName() {
        return "Ledger Report Viewer";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.NORTH);
        }
        return upperPane;
    }

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setLayout(new BorderLayout());

            viewerPanel = new JPanel();
            lowerPane.add(viewerPanel, BorderLayout.CENTER);
            viewerPanel.setLayout(new BoxLayout(viewerPanel, BoxLayout.X_AXIS));
        }
        return lowerPane;
    }

}
