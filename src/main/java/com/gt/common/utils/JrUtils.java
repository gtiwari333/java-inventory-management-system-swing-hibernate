package com.gt.common.utils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JrUtils {


    public static JRViewer getJrViewerReport(List objectList, String fileName, String title, Map parameters) throws Exception {
        InputStream is = JrUtils.class.getClassLoader().getResourceAsStream(fileName);
        JasperDesign jasperDesign = JRXmlLoader.load(is);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(objectList);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        return new JRViewer(jasperPrint);
    }

}
