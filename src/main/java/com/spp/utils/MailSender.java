package com.spp.utils;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSender {
    private static final String smtp_server = "smtp.gmail.com";
    private static final String username = "spp.standard11";
    private static final String password = "11!?Standard";

    public static boolean sendEmail(String email_to, String email_subject, String email_message) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtp_server);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", username);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, null);
        Message message = new MimeMessage(session);
        InternetAddress internetAddress;

        try {
            internetAddress= new InternetAddress(username);
        } catch (AddressException addressException) {
            Logger.getLogger(MailSender.class.getName())
                    .log(Level.SEVERE, addressException.getMessage(), addressException);
            return false;
        }

        try {
            message.setFrom(internetAddress);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_to, false));
            message.setSubject(email_subject);
            message.setText(email_message);
            message.setSentDate(new Date());
        } catch (MessagingException messagingException) {
            Logger.getLogger(MailSender.class.getName())
                    .log(Level.SEVERE, messagingException.getMessage(), messagingException);
            return false;
        }

        SMTPTransport transport;

        try {
            transport = (SMTPTransport) session.getTransport();
        } catch (NoSuchProviderException noSuchProviderException) {
            noSuchProviderException.printStackTrace();
            return false;
        }

        try {
            transport.connect(smtp_server, username, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException messagingException) {
            Logger.getLogger(MailSender.class.getName())
                    .log(Level.SEVERE, messagingException.getMessage(), messagingException);
            return false;
        }
        return true;
    }
}
