package net.lebedko.service.mail;

import net.lebedko.util.PropertyUtil;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 26.06.2017
 */
public class PropertiesSessionProvider implements SessionProvider {
    private Session session;

    public PropertiesSessionProvider(String filName) {
        this.session = Session.getInstance(loadProperties(filName));
    }

    public PropertiesSessionProvider() {
        this("mail.properties");
    }

    @Override
    public Session getSession() throws MessagingException {
        return session;
    }
}
