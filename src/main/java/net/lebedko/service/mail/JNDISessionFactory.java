package net.lebedko.service.mail;


import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * alexandr.lebedko : 25.05.2017.
 */

public class JNDISessionFactory implements SessionFactory {

    private Context envCtx;

    private JNDISessionFactory() {
        try {
            Context initCtx = new InitialContext();
            envCtx = (Context) initCtx.lookup("java:comp/env");
        } catch (NamingException e) {
            throw new RuntimeException("Failed to look up context!",e);
        }
    }

    private static class InstanceHolder {
        private static JNDISessionFactory instance = new JNDISessionFactory();
    }

    public static SessionFactory getInstance() {
        return InstanceHolder.instance;
    }

    public Session getSession() {
        try {
            return (Session) envCtx.lookup("mail/Session");
        } catch (NamingException e) {
            throw new RuntimeException("Failed to look up mail/Session",e);
        }
    }
}



