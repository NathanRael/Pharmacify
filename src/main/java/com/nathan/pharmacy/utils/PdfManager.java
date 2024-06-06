package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.views.ViewFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class PdfManager {
    public void print(int patientId, LocalDate purchaseDate){
        try {
            ConnectionDb connectionDb = ConnectionDb.getInstance();
            String outputPath = "src/main/resources/com/nathan/pharmacy/pdf/invoice.pdf";
            JasperDesign design = JRXmlLoader.load(Objects.requireNonNull(ViewFactory.class.getResourceAsStream("patient_invoice.jrxml")));
            JRDesignQuery designQuery = new JRDesignQuery();

            design.setQuery(designQuery);

/*            designQuery.setText("SELECT (count(*) * p.purchaseQuantity ) as totalAmount, pa.patientFName, pa.patientLName, pa.patientPhone, pa.patientAddress, pa.patientEmail, m.medName, m.medPrice , p.totalPrice, p.purchaseQuantity, p.purchaseDate FROM purchase p, medicament m, patient pa WHERE p.medId = m.medId AND p.patientId = pa.patientId; AND pa.patientId = " + patientId + " AND p.purchaseDate = '" + purchaseDate + " '");*/
            designQuery.setText("SELECT * FROM purchase p, medicament m, patient pa WHERE p.medId = m.medId AND p.patientId = pa.patientId AND pa.patientId = " + patientId + " AND p.purchaseDate = '" + purchaseDate + " '");

            JasperReport jasperReport =  JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,connectionDb.getConnection());
//            JasperViewer.viewReport(jasperPrint,false);
            JasperExportManager.exportReportToPdfFile(jasperPrint,outputPath);
            open(new File(outputPath).getAbsolutePath());
            System.out.println("pdfExported");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void open(String filePath){
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")){
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", filePath});
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"open", filePath});
            }else if (os.contains("nix")){
                Runtime.getRuntime().exec(new String[]{"xdg-open" , filePath});
            }else {
                System.out.println("Unsupported OS : " + os);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
