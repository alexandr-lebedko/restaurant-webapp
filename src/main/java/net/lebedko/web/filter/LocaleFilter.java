package net.lebedko.web.filter;

import net.lebedko.i18n.SupportedLocales;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.lebedko.i18n.SupportedLocales.*;

/**
 * alexandr.lebedko : 22.08.2017.
 */
@WebFilter("/*")
public class LocaleFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        updateLocale(httpServletRequest, httpServletResponse);

        chain.doFilter(request, response);
    }

    private void updateLocale(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(getLocaleSessionAttributeName(), resolveLocale(request));
    }

    private Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;

        String localeRequestAttribute = request.getParameter(getLocaleRequestAttributeName());

        if (nonNull(localeRequestAttribute)) {
            LOG.debug("LOCALE REQUEST ATTRIBUTE VALUE: " + localeRequestAttribute);
            locale = new Locale(localeRequestAttribute);
        } else {

            Object localeSessionAttribute = request.getSession().getAttribute(getLocaleSessionAttributeName());

            if (nonNull(localeSessionAttribute)) {
                LOG.debug("LOCALE SESSION ATTRIBUTE: " + localeSessionAttribute);
                locale = new Locale(localeSessionAttribute.toString());
            }
        }

        if (isSupported(locale)) {
            LOG.debug("LOCALE: " + locale + ", IS SUPPORTED AND WILL BE RETURNED");

            return locale;
        }

        LOG.debug("LOCALE: " + locale + ", IS NOT SUPPORTED. DEFAULT LOCALE: " + getDefaultLocale() + " WILL BE RETURNED");

        return getDefaultLocale();
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
