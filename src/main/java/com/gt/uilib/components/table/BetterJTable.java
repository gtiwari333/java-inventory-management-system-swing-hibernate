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

public class BetterJTable extends JTable {

    private static final Color EVEN_ROW_COLOR = new Color(240, 255, 250);
    private static final Color TABLE_GRID_COLOR = new Color(0xd9d9d9);

    public BetterJTable(TableModel dm) {
        super(dm);
        init();
        setFillsViewportHeight(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		setEditable(false);
        hideCol();
    }

    public final void hideCol() {
        String columnName = "ID";
        TableColumnModel tcm = getColumnModel();
        int index = tcm.getColumnIndex(columnName);
        TableColumn column = tcm.getColumn(index);
        Map<String, Object> hiddenColumns = new HashMap<>();
        hiddenColumns.put(columnName, column);
        hiddenColumns.put(":" + columnName, index);
        tcm.removeColumn(column);
    }

    public void adjustColumns() {
//		packAll();
    }

    @Override
    public final Class<?> getColumnClass(int column) {
        return (column == 0) ? Integer.class : Object.class;
    }

    public final String getToolTipText(MouseEvent e) {
        int row = rowAtPoint(e.getPoint());
        int column = columnAtPoint(e.getPoint());
        try {
            Object value = getValueAt(row, column);
            return value == null ? "" : value.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private void init() {
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        getTableHeader().setReorderingAllowed(false);
        setOpaque(false);
        setGridColor(TABLE_GRID_COLOR);
        setShowGrid(true);
    }

    @Override
    public final Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
        Component c = super.prepareRenderer(tcr, row, column);
        if (isRowSelected(row)) {
            c.setForeground(getSelectionForeground());
            c.setBackground(getSelectionBackground());
        } else {
            c.setForeground(getForeground());
            c.setBackground((row % 2 == 0) ? EVEN_ROW_COLOR : getBackground());
        }
        return c;
    }

}