package com.gt.uilib.components.input;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.gt.uilib.components.combo-DataComboBox.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public class DataComboBox extends JComboBox {

    private static final long serialVersionUID = -615634209230481880L;
    private List<Item> itemList;

    public DataComboBox() {
        super();
        setBorder(BorderFactory.createEmptyBorder());
        AutoCompleteDecorator.decorate(this);
    }

    protected static String getStringRepresentation(Object[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < values.length; i++) {
            sb.append(values[i]);
            if (i != values.length - 1) sb.append("  -  ");
        }
        return sb.toString();
    }

    /**
     * clears the all data in combo
     */
    public final void init() {
        if (itemList != null) {
            this.removeAllItems();
            itemList.clear();
            itemList = null;

        }
    }

    // decorate

    public final boolean isValidDataChoosen() {
        return (getSelectedId() != -1);
    }

    /**
     * @param values first index must contain ID field
     */

    public final void addRow(Object[] values) {
        if (itemList == null) {
            itemList = new ArrayList<>();
            Item blank = new Item(0, "");
            itemList.add(blank);
            this.addItem(blank);
        }
        Item item = new Item((Integer) values[0], getStringRepresentation(values));
        itemList.add(item);
        this.addItem(item);

    }

    public final int getSelectedId() {
        int index = this.getSelectedIndex();
        if (index > 0 && itemList != null && itemList.size() > 0) {
            Item item = itemList.get(index);
            return item.getId();
        }
        return -1;
    }

    public final void selectItem(int id) {
        for (Item item : itemList) {
            if (item.getId() == id) {
                this.setSelectedItem(item);
            }
        }
    }


    public final void selectLastItem() {
        if (itemList != null) this.setSelectedItem(itemList.size());
        else
            setSelectedIndex(getItemCount() - 1);
    }

    public final void selectDefaultItem() {
        if (itemList != null && itemList.size() > 0) this.setSelectedItem(itemList.get(0));
    }

    public final boolean contains(String s) {
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

    public final boolean matches(String s) {
        if (s == null || s.trim().isEmpty()) return true;
        s = s.toLowerCase();
        for (Item i : itemList) {
            if (i.toString().toLowerCase().contains(s)) {
                return true;
            }
        }
        return false;

    }

    static class Item implements Comparable<Item> {
        int id;
        String text;

        public Item(int id, String text) {
            super();
            this.id = id;
            this.text = text;
        }

        public final int getId() {
            return id;
        }

        public final void setId(int id) {
            this.id = id;
        }

        public final String getText() {
            return text;
        }

        public final void setText(String text) {
            this.text = text;
        }

        @Override
        public final String toString() {
            return String.format("%s", text);
        }

        public final int compareTo(Item o) {
            Item it2 = o;
            return this.text.compareToIgnoreCase(it2.text);

        }
    }
}
