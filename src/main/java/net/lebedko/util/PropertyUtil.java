package net.lebedko.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger LOG = LogManager.getLogger();

    private PropertyUtil() {
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e);
        }
        return properties;
    }
}
