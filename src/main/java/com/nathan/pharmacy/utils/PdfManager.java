package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.views.ViewFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.time.LocalDate;
import java.util.Objects;

public class PdfManager {
    public void print(int patientId, LocalDate purchaseDate){
        try {
            ConnectionDb connectionDb = new ConnectionDb();
            String outputPath = "src/main/resources/com/nathan/pharmacy/pdf/invoice.pdf";
            JasperDesign design = JRXmlLoader.load(Objects.requireNonNull(ViewFactory.class.getResourceAsStream("patient_invoice.jrxml")));
            JRDesignQuery designQuery = new JRDesignQuery();

            design.setQuery(designQuery);

            designQuery.setText("SELECT * FROM purchase p, medicament m, patient pa WHERE p.medId = m.medId AND p.patientId = pa.patientId AND pa.patientId = " + patientId + " AND p.purchaseDate = '" + purchaseDate + " '");

            JasperReport jasperReport =  JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,connectionDb.getConnection());
//            JasperViewer.viewReport(jasperPrint,false);
            JasperExportManager.exportReportToPdfFile(jasperPrint,outputPath);
            System.out.println("pdfExported");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
