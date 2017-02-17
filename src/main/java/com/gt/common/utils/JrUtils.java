package com.gt.common.utils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JrUtils {


    public static JRViewer getJrViewerReport(List objectList, String fileName, String title, Map parameters) {
        try {
            InputStream is = JrUtils.class.getClassLoader().getResourceAsStream(fileName);
            JasperDesign jasperDesign = JRXmlLoader.load(is);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(objectList);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperReport.setWhenNoDataType(jasperReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            return new JRViewer(jasperPrint);

            // JasperViewer jv = new JasperViewer(jasperPrint, false);
            // jv.setTitle(title);
            // jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}
