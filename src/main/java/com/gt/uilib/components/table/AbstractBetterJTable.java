package com.gt.uilib.components.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

//template method pattern
public abstract class AbstractBetterJTable extends JTable{
	private static final Color EVEN_ROW_COLOR = new Color(240, 255, 250);
    private static final Color TABLE_GRID_COLOR = new Color(0xd9d9d9);
    
    public AbstractBetterJTable(TableModel dm) {
		// TODO Auto-generated constructor stub
    	super(dm);
	}

	abstract void adjustColumns();
    
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
    
    protected void init() {
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
