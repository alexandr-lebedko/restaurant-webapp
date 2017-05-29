package net.lebedko.service.mail;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.mail.MailMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public class Mailer {

    private SessionFactory sessionFactory;

    public Mailer(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void sendMessage(MailMessage mailMessage) throws MessagingException{
            Message message = new MimeMessage(sessionFactory.getSession());
            message.setFrom(new InternetAddress(mailMessage.getFrom().toString()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getTo().toString()));
            message.setSubject(mailMessage.getSubject().toString());
            message.setText(mailMessage.getText().toString());
            Transport.send(message);
    }
}
