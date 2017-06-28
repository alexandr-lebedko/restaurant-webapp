package net.lebedko.service.mail;

import net.lebedko.service.exception.ServiceException;

import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * alexandr.lebedko : 25.05.2017.
 */
public interface SessionProvider {

    Session getSession() throws MessagingException;

}
