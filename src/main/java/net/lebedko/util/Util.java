package net.lebedko.util;


import net.lebedko.entity.Validatable;

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

    public static void main(String[] args) {
        boolean b = !true;
        System.out.println(b);
    }

}
