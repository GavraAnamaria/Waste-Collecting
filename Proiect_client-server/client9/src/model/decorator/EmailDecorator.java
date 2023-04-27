package model.decorator;
import model.Utilizator;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailDecorator  extends Decorator {

    public EmailDecorator(Notifier n) {
        super(n);
    }
    public void sendMessage(String message, Utilizator u) {
        super.sendMessage(message, u);
        // Recipient's email ID needs to be mentioned.
        final String to = u.getEmail();

        // Sender's email ID needs to be mentioned
        final String from = "gavra.anamaria@yahoo.com";
        String host = "smtp.mail.yahoo.com";
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gavra.anamaria", "nzywvtekjgqwjhsg");
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message1 = new javax.mail.internet.MimeMessage(session);
            message1.setFrom(new javax.mail.internet.InternetAddress(from));
            message1.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message1.setSubject("Cont firma colectare deseuri.");
            message1.setText(message);
            javax.mail.Transport.send(message1);
            System.out.println("Sent message successfully....");
        } catch (javax.mail.MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
