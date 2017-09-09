package net.lebedko.web.command;

import net.lebedko.web.validator.Errors;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Locale;

/**
 * alexandr.lebedko : 12.06.2017
 */
public interface IContext {

    void addSessionAttribute(String key, Object value);

    void addRequestAttribute(String key, Object value);

    Locale getLocale();

    String getRequestAttribute(String key);

    String getRequestParameter(String key);

    <T> T getSessionAttribute(Class<T> clazz, String key);

    String getSessionAttribute(String key);

    void addErrors(Errors errors);

    Part getPart(String name) throws IOException, ServletException;

    void destroySession();
}
