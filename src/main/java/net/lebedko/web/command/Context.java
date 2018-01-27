package net.lebedko.web.command;

import net.lebedko.web.validator.Errors;

import java.io.InputStream;
import java.util.List;

public interface Context {

    void addSessionAttribute(String key, Object value);

    void addRequestAttribute(String key, Object value);

    String getRequestParameter(String key);

    List<String> getRequestParameters(String key);

    <T> T getSessionAttribute(Class<T> clazz, String key);

    void removeSessionAttribute(String key);

    String getSessionAttribute(String key);

    void addErrors(Errors errors);

    void destroySession();

    InputStream getInputStream(String name);
}
