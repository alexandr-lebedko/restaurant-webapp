package net.lebedko.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.lebedko.util.SupportedLocales.*;

@WebFilter("/*")
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        updateLocale(httpServletRequest);

        chain.doFilter(request, response);
    }

    private void updateLocale(HttpServletRequest request) {
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
                return (Locale) localeSessionAttribute;
            }
        }
        if (isSupported(locale)) {
            return locale;
        }
        return getDefaultLocale();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
