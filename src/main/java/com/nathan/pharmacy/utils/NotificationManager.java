package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.contstants.MailType;
import com.nathan.pharmacy.models.MedUsage;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Patient;
import com.nathan.pharmacy.models.PrescMedInfo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NotificationManager {
    public static void checkMedQuantityAndNotify(String host) {
        Runnable task = () ->{
            List<Medicament> medicaments = MedicamentUtil.getMedWithLowStock(20);
            String mailSubject = "Rupture de stock imminente";
            StringBuilder mailContent = new StringBuilder();

            mailContent
                    .append("<html><body>")
                    .append("<strong>Ce(s) medicament(s) devrai(ent) être réapprovisionner :</strong> <br><br>")
                    .append(
                            "<table border='1' cellpadding='5' cellspacing='0' style='border-collapse :" +
                                    "collapse;' >")
                    .append(
                            "<tr><th>Nom du medicament</th><th>Quantité disponible</th></tr>"
                    )
                    .append("<tr>");

            if (!medicaments.isEmpty()) {
                for (Medicament medicament : medicaments) {
                    mailContent
                            .append("<tr>")
                            .append("<td>").append(medicament.getName()).append("</td>")
                            .append("<td>").append(medicament.getQuantity()).append("</td>")
                            .append("</tr>");
                }
            }
            mailContent
                    .append("</table>")
                    .append("</body></html>")
            ;

            MailSender.sendMailTo(host, mailSubject, mailContent.toString(), MailType.TEXT_HTML);
            System.out.println(host + " notified via email");
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    public static void sendPatientPrescription(String patientFName, String userEmail, List<PrescMedInfo> prescMedInfos) {
        Runnable task = () -> {
            try {
                String mailSubject = "Prescription";
                StringBuilder mailContent = new StringBuilder();

                mailContent
                        .append("<html><body>")
                        .append("Cher/Chère ").append(patientFName)
                        .append("</br>")
                        .append("Voici le mode d'emploi de vos/votre medicament(s)")
                        .append("</br>")
                        .append(
                                "<table border='1' cellpadding='5' cellspacing='0' style='border-collapse :" +
                                        "collapse;' >")
                        .append("<tr>")
                        .append("<th>Nom du medicament</th>")
                        .append("<th>Matin</th>")
                        .append("<th>Midi</th>")
                        .append("<th>Soir</th>")
                        .append("</tr>");

                if (!prescMedInfos.isEmpty()) {
                    for (PrescMedInfo prescMedInfo : prescMedInfos) {
                        mailContent
                                .append("<tr>")
                                .append("<th>")
                                .append(prescMedInfo.getMedName())
                                .append("</th>")
                                .append("<th>")
                                .append(prescMedInfo.getMorningQuantity())
                                .append("</th>")
                                .append("<th>")
                                .append(prescMedInfo.getNoonQuantity())
                                .append("</th>")
                                .append("<th>")
                                .append(prescMedInfo.getAfternoonQuantity())
                                .append("</th>")
                                .append("</tr>");
                    }
                }
                mailContent
                        .append("</table>")
                        .append("</body></html>")
                ;

                MailSender.sendMailTo(userEmail, mailSubject, mailContent.toString(), MailType.TEXT_HTML);
//                System.out.println(patientFName + " notified via email");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    public static void sleepTest() {
        Runnable task = () -> {
            try {
                Thread.sleep(8000);
//                NotificationManager.checkMedQuantityAndNotify("ralaivoavy.natanael@gmail.com");
                System.out.println("sleep");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }


}
