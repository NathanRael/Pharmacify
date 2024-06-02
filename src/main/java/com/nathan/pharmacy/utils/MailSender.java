package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.contstants.MailType;

import java.util.Properties;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public static void sendMailTo(String host, String subject, String body, MailType contentType){
        if (host.matches("^[a-z]+[\\w-_\\.]+@[\\w-_]+\\.[\\w-_]{2,4}$")){
            final String user="natanael.ralaivoavy@gmail.com";
            final String password=System.getenv("PHARMACIFY_MAIL_PWD");

            String to=host;

            // session object
            Properties props = new Properties();
            props.put("mail.smtp.ssl.trust", "*");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");

           Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user,password);
                        }
                    });

           try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

                message.setSubject(subject);

                switch (contentType){
                    case TEXT -> message.setText(body);
                    case TEXT_HTML -> message.setContent(body, "text/html");
                }

                Transport.send(message);

                System.out.println("message sent successfully via mail ... !!! ");

           } catch (MessagingException e) {e.printStackTrace();}

        } else {
            System.out.println("Cannot send email");
        }
    }

}
