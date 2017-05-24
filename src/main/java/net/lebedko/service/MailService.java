package net.lebedko.service;

import net.lebedko.entity.general.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * alexandr.lebedko : 24.05.2017.
 */
public interface MailService {

    void sendMessage(MailMessage mailMessage) throws MessagingException;

}
