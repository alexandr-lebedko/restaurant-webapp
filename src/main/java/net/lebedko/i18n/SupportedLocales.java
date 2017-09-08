package net.lebedko.i18n;

import java.util.*;

/**
 * alexandr.lebedko : 22.08.2017.
 */
public final class SupportedLocales {
    public static final String UA_CODE = "uk_UA";
    public static final String EN_CODE = "en";
    public static final String RU_CODE = "ru_RU";

    private static final Map<String, Locale> LOCALES = new HashMap<>();
    private static final Locale DEFAULT_LOCALE = new Locale(RU_CODE);
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "lang";
    private static final String LOCALE_REQUEST_ATTRIBUTE_NAME = "lang";

    static {
        LOCALES.put(EN_CODE, Locale.ENGLISH);
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

    public static Map<String, Locale> getLocales() {
        return LOCALES;
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

    public static boolean containsSupportedLocales(Collection<Locale> locales) {
        return locales.containsAll(LOCALES.values());
    }
}