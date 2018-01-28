package net.lebedko.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

public final class SupportedLocales {
    public static final String UA_CODE = "ua";
    public static final String EN_CODE = "en";
    public static final String RU_CODE = "ru";

    public static final String LOCALE_SESSION_ATTRIBUTE_NAME = "lang";
    public static final String LOCALE_REQUEST_ATTRIBUTE_NAME = "lang";
    private static final Map<String, Locale> LOCALES = new HashMap<>();
    private static final Locale DEFAULT_LOCALE = new Locale(EN_CODE);

    static {
        LOCALES.put(EN_CODE, DEFAULT_LOCALE);
        LOCALES.put(UA_CODE, new Locale(UA_CODE));
        LOCALES.put(RU_CODE, new Locale(RU_CODE));
    }

    private SupportedLocales() {
    }

    public static Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    public static boolean isSupported(Locale locale) {
        return LOCALES.containsValue(locale);
    }

    public static Locale getByCode(String code) {
        return LOCALES.get(code);
    }

    public static String getLocaleSessionAttributeName() {
        return LOCALE_SESSION_ATTRIBUTE_NAME;
    }

    public static String getLocaleRequestAttributeName() {
        return LOCALE_REQUEST_ATTRIBUTE_NAME;
    }

}