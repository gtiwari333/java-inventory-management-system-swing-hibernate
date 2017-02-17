package com.gt.uilib.components.input;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.gt.uilib.components.combo-DataComboBox.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 *         Created on : Mar 19, 2012<br/>
 *         Copyright : <a
 *         href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public class DataComboBox extends JComboBox {

    private static final long serialVersionUID = -615634209230481880L;
    List<Item> itemList;

    public DataComboBox() {
        super();
        setBorder(BorderFactory.createEmptyBorder());
//		setModel(new SortedComboBoxModel());
        AutoCompleteDecorator.decorate(this);
    }

    /**
     * clears the all data in combo
     */
    public void init() {
        if (itemList != null) {
            this.removeAllItems();
            itemList.clear();
            itemList = null;

        }
    }

    public boolean isValidDataChoosen() {
        return (getSelectedId() != -1);
    }

    // decorate

    /**
     * @param values first index must contain ID field
     */

    public void addRow(Object[] values) {
        if (itemList == null) {
            itemList = new ArrayList<DataComboBox.Item>();
            Item blank = new Item(0, "");
            itemList.add(blank);
            this.addItem(blank);
        }
        Item item = new Item((Integer) values[0], getStringRepresentation(values));
        itemList.add(item);
        this.addItem(item);

    }

    protected String getStringRepresentation(Object[] values) {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < values.length; i++) {
            sb.append(values[i]);
            if (i != values.length - 1) sb.append("  -  ");
        }
        return sb.toString();
    }

    public int getSelectedId() {
        int index = this.getSelectedIndex();
        if (index > 0 && itemList != null && itemList.size() > 0) {
            Item item = itemList.get(index);
            return item.getId();
        }
        return -1;
    }

    public void selectItem(int id) {
        for (Item item : itemList) {
            if (item.getId() == id) {
                this.setSelectedItem(item);
            }
        }
    }

    private int getMaxIdIndex() {
        int index = 0;
        int max = 0;
        for (int i = 0; i < itemList.size(); i++) {

            if (itemList.get(i).id >= max) {
                max = itemList.get(i).id;
                index = i;
            }
        }
        System.out.println("Max IDDDDDDd index >> " + index + " id " + max);
        return index;
    }

    /**
     * select max ID value
     */
//	public void selectLastInsertedItem() {
//		if (itemList != null) {
//
//			this.setSelectedIndex(getMaxIdIndex());
//		}
//		else
//			setSelectedIndex(getItemCount() - 1);
//	}
    public void selectLastItem() {
        if (itemList != null) this.setSelectedItem(itemList.size());
        else
            setSelectedIndex(getItemCount() - 1);
    }

    public void selectDefaultItem() {
        if (itemList != null && itemList.size() > 0) this.setSelectedItem(itemList.get(0));
    }

    public boolean contains(String s) {
        if (s == null || s.trim().isEmpty()) return true;
        if (itemList == null) return false;
        s = s.toLowerCase();
        for (Item i : itemList) {
            if (i.toString().toLowerCase().equals(s)) {
                return true;
            }
        }
        return false;

    }

    public boolean matches(String s) {
        if (s == null || s.trim().isEmpty()) return true;
        s = s.toLowerCase();
        for (Item i : itemList) {
            if (i.toString().toLowerCase().contains(s)) {
                return true;
            }
        }
        return false;

    }

    class Item implements Comparable<Item> {
        int id;
        String text;

        public Item(int id, String text) {
            super();
            this.id = id;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            // return String.format("%s : %s", id, text);
            return String.format("%s", text);
        }

        public int compareTo(Item o) {
            Item it2 = (Item) o;
            return this.text.compareToIgnoreCase(it2.text);

        }

        // @Override
        // public boolean equals(Object obj) {
        // // TODO Auto-generated method stub
        // Item it2 = (Item) obj;
        // return it2.text.compareToIgnoreCase(this.text) > 0 ? true : false;
        // }
    }
}
