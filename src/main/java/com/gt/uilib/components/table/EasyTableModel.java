package com.gt.uilib.components.table;

import javax.swing.table.DefaultTableModel;

/**
 * IMP: by default- second column contains key, but it is adjustable by setKeyColumn()
 *
 * @author gtiwari333@gmail.com
 */
public class EasyTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 8952987986047661236L;
    protected String[] header;
    private int KEY_ID_COLUMN = 1;

    protected EasyTableModel() {
        setEditable(false);
    }

    public EasyTableModel(String[] header) {
        this.header = header;
        for (int i = 0; i < header.length; i++) {
            addColumn(header[i]);
        }
    }

    public Integer getKeyAtRow(int row) {
        return (Integer) getValueAt(row, KEY_ID_COLUMN);
    }

    public void setKeyColumn(int keyCol) {
        this.KEY_ID_COLUMN = keyCol;
    }

    /**
     * on the assumption that second column contains key
     *
     * @param key
     * @return
     */
    public boolean containsKey(Integer key) {

        int rowC = getRowCount();
        for (int i = 0; i < rowC; i++) {
            Integer keyAtRowI = getKeyAtRow(i);
            if (keyAtRowI == key) {
                return true;
            }
        }
        return false;
    }

    public void setEditable(boolean isEditable) {
        setEditable(isEditable);
    }

    public void removeRowWithKey(Integer key) {
        if (key == null || key < 0) {
            return;
        }
        int index = 0;

        int rowC = getRowCount();
//		System.out.println("removing key "+key +"   count "+rowC);
        for (int i = 0; i < rowC; i++) {
            Integer keyAtRowI = getKeyAtRow(i);
            if (keyAtRowI == key) {
                removeRow(index);
//				System.out.println("Row count after removal  "+getRowCount());
                return;
            }
            index++;
        }

    }

    public void addRow(Object[] values) {
        super.addRow(values);
//		System.out.println("EasyTableModel.addRow() >>  "+getRowCount());
    }

    public void resetModel() {
        super.setRowCount(0);
    }


}
