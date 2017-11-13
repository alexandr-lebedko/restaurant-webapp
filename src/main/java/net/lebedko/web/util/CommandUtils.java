package net.lebedko.web.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;


public class CommandUtils {
    private static final Logger LOG = LogManager.getLogger();

    private CommandUtils() {
    }

    public static Long parseToLong(String value, Long defaultValue) {
        Long result = defaultValue;
        try {
            result = Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return result;
    }

    public static List<Long> parseToLongList(List<String> values, Long defaultValue) {
        return values.stream()
                .map(value -> parseToLong(value, defaultValue))
                .collect(Collectors.toList());
    }

}
