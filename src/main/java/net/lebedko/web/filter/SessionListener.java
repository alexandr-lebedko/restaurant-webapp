package net.lebedko.web.filter;

import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import static org.apache.logging.log4j.LogManager.*;

@WebListener()
public class SessionListener implements HttpSessionAttributeListener {
    private static final Logger LOG = getLogger();


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (!"javax.servlet.jsp.jstl.fmt.request.charset".equals(event.getName()) && !"lang".equals(event.getName()))
            LOG.debug("===========>ATTRIBUTE ADDED TO SESSION: NAME=" + event.getName() + ", VALUE=" + event.getValue());

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (!"javax.servlet.jsp.jstl.fmt.request.charset".equals(event.getName()) && !"lang".equals(event.getName()))
            LOG.debug("===========>ATTRIBUTE REMOVED FROM SESSION: NAME=" + event.getName() + ", VALUE=" + event.getValue());

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (!"javax.servlet.jsp.jstl.fmt.request.charset".equals(event.getName()) && !"lang".equals(event.getName()))
            LOG.debug("===========>ATTRIBUTE REPLACED IN SESSION: NAME=" + event.getName() + ", VALUE=" + event.getValue());

    }
}
