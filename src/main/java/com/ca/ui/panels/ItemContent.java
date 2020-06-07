package com.ca.ui.panels;

import java.awt.FlowLayout;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.input.NumberTextField;
import com.toedter.calendar.JDateChooser;

public class ItemContent implements FormPanelStrategy{

	private JLabel lblPurchaseOrderNumber;
	private JTextField txtPurchaseordernumber;
	private JLabel lblKhataNumber;
	private JTextField txtKhatanumber;
	private JLabel lblDakhilaNumber;
	private JTextField txtDakhilanumber;
	private JTextField txtName;
	private JLabel lblPanaNumber;
	private JTextField txtPananumber;
	private DataComboBox cmbCategory;
	private JButton btnNewCategory;
	private JPanel specPanelHolder;
	private JLabel lblPartsNumber;
	private JTextField txtPartsnumber;
	private JLabel lblQuantity;
	private NumberTextField txtQuantity;
	private JLabel lblSerialNumber;
	private JTextField txtSerialnumber;
	private JLabel lblUnit;
	private DataComboBox cmbUnitcombo;
	private JLabel lblRacknumber;
	private JTextField txtRacknumber;
	private JLabel lblRate;
	private NumberTextField txtRate;
	private JLabel lblPurchaseDate;
	private JDateChooser txtPurDate;
	private JLabel lblTotal;
	private JTextField txtTotal;
	private DataComboBox cmbVendor;
	private KeyListener priceCalcListener;

	public void content(JPanel formPanel){
		lblPurchaseOrderNumber = new JLabel("Purchase Order Number");
        formPanel.add(lblPurchaseOrderNumber, "4, 2");

        txtPurchaseordernumber = new JTextField();
        formPanel.add(txtPurchaseordernumber, "8, 2, fill, default");
        txtPurchaseordernumber.setColumns(10);

        lblKhataNumber = new JLabel("Khata");
        formPanel.add(lblKhataNumber, "4, 4");

        txtKhatanumber = new JTextField();
        formPanel.add(txtKhatanumber, "8, 4, fill, default");
        txtKhatanumber.setColumns(10);

        lblDakhilaNumber = new JLabel("Dakhila Number");
        formPanel.add(lblDakhilaNumber, "4, 6");

        txtDakhilanumber = new JTextField();
        formPanel.add(txtDakhilanumber, "8, 6, fill, default");
        txtDakhilanumber.setColumns(10);

        JLabel lblN = new JLabel("Name");
        formPanel.add(lblN, "4, 8");

        txtName = new JTextField();
        formPanel.add(txtName, "8, 8, fill, default");
        txtName.setColumns(10);

        lblPanaNumber = new JLabel("Pana Number");
        formPanel.add(lblPanaNumber, "4, 10");

        txtPananumber = new JTextField();
        formPanel.add(txtPananumber, "8, 10, fill, default");
        txtPananumber.setColumns(10);

        JLabel lblCategory = new JLabel("Category");
        formPanel.add(lblCategory, "4, 12, default, top");

        cmbCategory = new DataComboBox();
        formPanel.add(cmbCategory, "8, 12, fill, default");

        
        formPanel.add(btnNewCategory, "10, 12");

        specPanelHolder = new JPanel();
        specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        formPanel.add(specPanelHolder, "4, 14, 17, 1, fill, fill");

        lblPartsNumber = new JLabel("Parts Number");
        formPanel.add(lblPartsNumber, "4, 16");

        txtPartsnumber = new JTextField();
        formPanel.add(txtPartsnumber, "8, 16, fill, default");
        txtPartsnumber.setColumns(10);

        lblQuantity = new JLabel("Quantity");
        formPanel.add(lblQuantity, "12, 16");

        txtQuantity = new NumberTextField(6, true);

        txtQuantity.addKeyListener(priceCalcListener);
        formPanel.add(txtQuantity, "16, 16, fill, default");

        lblSerialNumber = new JLabel("Serial Number");
        formPanel.add(lblSerialNumber, "4, 18");

        txtSerialnumber = new JTextField();
        formPanel.add(txtSerialnumber, "8, 18, fill, default");
        txtSerialnumber.setColumns(10);

        lblUnit = new JLabel("Unit");
        formPanel.add(lblUnit, "12, 18");

        cmbUnitcombo = new DataComboBox();
        formPanel.add(cmbUnitcombo, "16, 18, fill, default");

        lblRacknumber = new JLabel("Rack Number");
        formPanel.add(lblRacknumber, "4, 20");

        txtRacknumber = new JTextField();
        formPanel.add(txtRacknumber, "8, 20, fill, default");
        txtRacknumber.setColumns(10);

        lblRate = new JLabel("Rate");
        formPanel.add(lblRate, "12, 20");

        txtRate = new NumberTextField(true);
        txtRate.setDecimalPlace(2);
        txtRate.addKeyListener(priceCalcListener);
        formPanel.add(txtRate, "16, 20, fill, default");
        txtRate.setDecimalPlace(2);

        lblPurchaseDate = new JLabel("Purchase Date");
        formPanel.add(lblPurchaseDate, "4, 22");

        txtPurDate = new JDateChooser();
        // txtPurDate.setDate(new Date());
        txtPurDate.setEnabled(false);
        formPanel.add(txtPurDate, "8, 22, fill, default");

        lblTotal = new JLabel("Total");
        formPanel.add(lblTotal, "12, 22");

        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setFocusable(false);
        // txtTotal.setEnabled(false);
        formPanel.add(txtTotal, "16, 22, fill, default");
        txtTotal.setColumns(10);

        JLabel lblPhoneNumber = new JLabel("Vendor");
        formPanel.add(lblPhoneNumber, "4, 24");

        cmbVendor = new DataComboBox();
        formPanel.add(cmbVendor, "8, 24, fill, default");
        
	}

}
