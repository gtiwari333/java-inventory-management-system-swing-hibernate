package com.gt.uilib.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BetterJTableNoSorting extends AbstractBetterJTable {


	public BetterJTableNoSorting(TableModel dm) {
		super(dm);
		init();
		setFillsViewportHeight(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// setEditable(false);
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}

	@Override
	public void adjustColumns() {
		// TODO Auto-generated method stub
		// packAll();
	}
	@Override
	public final Class<?> getColumnClass(int column) {
		return (column == 0 || column == 1) ? Integer.class : Object.class;
	}




}