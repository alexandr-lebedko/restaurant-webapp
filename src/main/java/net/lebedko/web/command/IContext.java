package net.lebedko.web.command;

import java.util.Locale;

/**
 * alexandr.lebedko : 12.06.2017
 */
public interface IContext {

    void addSessionAttribute(String key, Object value);

    void addRequestAttribute(String key, Object value);

    Locale getLocale();

    String getRequestAttribute(String key);

    <T> T getSessionAttribute(T t, String key);
}
