package com.gt.common.utils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;

public class ExcelUtils {

    public static void writeExcelFrom2DList(String[][] data, String[] header, String fileName, String sheetName, int sheetPos) throws Exception {

        WritableWorkbook wb = Workbook.createWorkbook(new File(fileName));
        WritableSheet curSheet = wb.createSheet(sheetName, sheetPos);

        for (int i = 0; i < header.length; i++) {
            Label column = new Label(i, 0, header[i]);
            curSheet.addCell(column);
        }

        int j;
        for (int i = 0; i < data[0].length; i++) {//rows
            for (j = 0; j < data[i][0].length(); j++) {//column
                Label row = new Label(j, i + 1, data[i][j]);
                curSheet.addCell(row);
            }
        }

        wb.write();
        wb.close();

    }

    public static void writeExcelFromJTable(JTable table, String fileName, String sheetName, int sheetPos) throws Exception {

        WritableWorkbook wb = Workbook.createWorkbook(new File(fileName));
        WritableSheet curSheet = wb.createSheet(sheetName, sheetPos);
        TableModel model = table.getModel();

        for (int i = 0; i < model.getColumnCount(); i++) {
            Label column = new Label(i, 0, model.getColumnName(i));
            curSheet.addCell(column);
        }

        int j;
        for (int i = 0; i < model.getRowCount(); i++) {
            for (j = 0; j < model.getColumnCount(); j++) {
                Object tmp = model.getValueAt(i, j);
                Label row;

                if (tmp == null) {
                    row = new Label(j, i + 1, "");
                } else {
                    row = new Label(j, i + 1, model.getValueAt(i, j).toString());
                }

                curSheet.addCell(row);
            }
        }

        wb.write();
        wb.close();

    }

    public static void writeExcelFromJTable(JTable table, String String, String sheetName) throws Exception {
        writeExcelFromJTable(table, String, sheetName, 0);
    }

    public static void writeExcelFromJTable(JTable table, String String) throws Exception {
        writeExcelFromJTable(table, String, "Sheet " + 1, 0);
    }

    public static void main(String[] args) throws Exception {
        String[] header = new String[]{"First", "sec", "third"};
        String[][] arr = new String[][]{{"aa", "bb", "cc"}, {"aa1", "bb2", "cc1"}, {"aa2", "bb3", "cc4"}, {"aasdf", "bbdsf", "ccsdf"}};

        writeExcelFrom2DList(arr, header, "dat.xls", "first", 2);
    }
}
