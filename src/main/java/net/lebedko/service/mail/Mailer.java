package net.lebedko.service.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public class Mailer {

    private SessionProvider sessionProvider;

    public Mailer(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void sendMessage(MailMessage mailMessage) throws MessagingException {
        Session session = sessionProvider.getSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperty("mail.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getTo().toString()));
        message.setSubject(mailMessage.getSubject().toString());
        message.setText(mailMessage.getText().toString());
        Transport.send(message);
    }
}