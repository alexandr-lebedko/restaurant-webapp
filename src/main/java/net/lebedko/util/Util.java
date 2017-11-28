package net.lebedko.util;

import net.lebedko.entity.general.StringI18N;

import java.util.Map;
import java.util.stream.Collectors;

public class Util {
    public static String removeExtraSpaces(String value) {
        return value.replaceAll("[\\s]{2,}", " ");
    }

    public static StringI18N removeExtraSpaces(StringI18N value) {
        return new StringI18N(value.getMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> removeExtraSpaces(entry.getValue())
                )));
    }
}
