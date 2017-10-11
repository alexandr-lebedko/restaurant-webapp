package net.lebedko.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            locale = getByCode(localeRequestAttribute);
        } else {

            Object localeSessionAttribute = request.getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME);

            if (nonNull(localeSessionAttribute)) {
                LOG.info("RETURNING LOCALE FROM SESSION ATTRIBUTE: " + localeSessionAttribute);
                return (Locale) localeSessionAttribute;
            }
        }

        if (isSupported(locale)) {
            return locale;
        }
        return getDefaultLocale();
    }

    //TODO: REFACTOR CLASS, REDUCE IF STATEMENTS
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
