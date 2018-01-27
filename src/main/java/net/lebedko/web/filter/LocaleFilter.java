package net.lebedko.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.lebedko.util.SupportedLocales.LOCALE_SESSION_ATTRIBUTE_NAME;
import static net.lebedko.util.SupportedLocales.getByCode;
import static net.lebedko.util.SupportedLocales.getDefaultLocale;
import static net.lebedko.util.SupportedLocales.getLocaleRequestAttributeName;
import static net.lebedko.util.SupportedLocales.getLocaleSessionAttributeName;
import static net.lebedko.util.SupportedLocales.isSupported;

@WebFilter("/*")
public class LocaleFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        updateLocale(request);
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
}