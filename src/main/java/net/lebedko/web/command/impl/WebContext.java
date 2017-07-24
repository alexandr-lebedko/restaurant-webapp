package net.lebedko.web.command.impl;

import net.lebedko.web.command.IContext;
import net.lebedko.web.validator.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class WebContext implements IContext {
    private HttpServletResponse response;
    private HttpServletRequest request;

    public WebContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void addSessionAttribute(String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    @Override
    public void addRequestAttribute(String key, Object value) {
        request.setAttribute(key, value);
    }

    @Override
    public String getRequestAttribute(String key) {
        return (String) request.getAttribute(key);
    }

    @Override
    public String getRequestParameter(String key) {
        return request.getParameter(key);
    }

    @Override
    public <T> T getSessionAttribute(T t, String key) {
        return (T) request.getSession().getAttribute(key);
    }

    @Override
    public void addErrors(Errors errors) {
        request.setAttribute("errors", errors);
    }

    @Override
    public Locale getLocale() {
        return request.getLocale();
    }
}
