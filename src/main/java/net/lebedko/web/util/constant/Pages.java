package net.lebedko.web.util.constant;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class Pages {
    private Pages() {
    }

    public static final String PREFIX = "/WEB-INF/views/";
    public static final String SUFFIX = ".jsp";
    public static final String REGISTRATION = PREFIX + Views.REGISTRATION + SUFFIX;
    public static final String LOGIN = PREFIX + Views.LOGIN + SUFFIX;
    public static final String ERROR_500 = PREFIX + Views.ERROR_500 + SUFFIX;
    public static final String MAIN_GUEST = PREFIX + Views.MAIN_GUEST + SUFFIX;
}
