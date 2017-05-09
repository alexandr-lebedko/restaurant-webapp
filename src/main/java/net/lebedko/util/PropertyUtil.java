package net.lebedko.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * alexandr.lebedko : 27.04.2017.
 */

public class PropertyUtil {

    private PropertyUtil() {
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load properties from file" + fileName, e);
        }
        return properties;
    }


}
