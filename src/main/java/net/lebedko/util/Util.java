package net.lebedko.util;


import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.StringI18N;

import java.util.stream.Collectors;

/**
 * alexandr.lebedko : 27.04.2017.
 */

public class Util {

    public static void checkValidity(Validatable entity) {
        if (!entity.isValid()) {
            throw new IllegalArgumentException("Invalid entity : " + entity);
        }
    }

    public static String removeExtraSpaces(String value) {
        return value.replaceAll("[\\s]{2,}", " ");
    }

    public static StringI18N removeExtraSpaces(StringI18N value) {
        return new StringI18N(
                value.getMap().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> removeExtraSpaces(entry.getValue()))));
    }

    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

}
