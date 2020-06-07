package com.gt.uilib.components.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class BetterJTable extends AbstractBetterJTable {


	public BetterJTable(TableModel dm) {
		super(dm);
		init();
		setFillsViewportHeight(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// setEditable(false);
		hideCol();
	}

	public final void hideCol() {
		String columnName = "ID";
		TableColumnModel tcm = getColumnModel();
		int index = tcm.getColumnIndex(columnName);
		TableColumn column = tcm.getColumn(index);
		Map<Object, Object> hiddenColumns = new HashMap<>();
		hiddenColumns.put(columnName, column);
		hiddenColumns.put(":" + columnName, index);
		tcm.removeColumn(column);
	}

	@Override
	public void adjustColumns() {
		// TODO Auto-generated method stub
		// packAll();
	}
	@Override
	public final Class<?> getColumnClass(int column) {
		return (column == 0) ? Integer.class : Object.class;
	}



}