package net.lebedko.web.command.impl;

import net.lebedko.web.command.IContext;
import net.lebedko.web.validator.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

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
    public String getRequestParameter(String key) {
        return request.getParameter(key);
    }

    @Override
    public List<String> getRequestParameters(String key) {
        return Arrays.asList(ofNullable(request.getParameterValues(key)).orElse(new String[]{}));
    }

    @Override
    public <T> T getSessionAttribute(Class<T> t, String key) {
        return (T) request.getSession().getAttribute(key);
    }

    @Override
    public void removeSessionAttribute(String key) {
        request.getSession().removeAttribute(key);
    }

    @Override
    public String getSessionAttribute(String key) {
        return getSessionAttribute(String.class, key);
    }

    @Override
    public void addErrors(Errors errors) {
        request.setAttribute("errors", errors);
    }

    @Override
    public void destroySession() {
        request.getSession().invalidate();
    }

    @Override
    public InputStream getInputStream(String name) {
        try {
            return request.getPart(name).getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
