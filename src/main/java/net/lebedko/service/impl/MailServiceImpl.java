package net.lebedko.service.impl;

import net.lebedko.entity.general.MailMessage;
import net.lebedko.service.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public class MailServiceImpl implements MailService {

    private Session session;

    public MailServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void sendMessage(MailMessage mailMessage) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailMessage.getFrom().toString()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getTo().toString()));
        message.setSubject(mailMessage.getSubject().toString());
        message.setText(mailMessage.getText().toString());
        Transport.send(message);
    }
}
