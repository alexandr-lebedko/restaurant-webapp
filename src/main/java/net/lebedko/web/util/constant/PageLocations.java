package net.lebedko.web.util.constant;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class PageLocations {
    private PageLocations() {
    }


    public static final String PREFIX = "/WEB-INF/views/";
    public static final String SUFFIX = ".jsp";


    public static final String SIGN_IN = PREFIX + Views.SIGN_IN + SUFFIX;
    public static final String SIGN_UP = PREFIX + Views.SIGN_UP + SUFFIX;

    public static final String MAIN_ADMIN = PREFIX + Views.ADMIN_MAIN + SUFFIX;
    public static final String MAIN_CLIENT = PREFIX + Views.CLIENT_MAIN + SUFFIX;

    public static final String MENU_NEW_DISH = PREFIX + Views.NEW_DISH + SUFFIX;

    public static final String ERROR_500 = PREFIX + Views.ERROR_500 + SUFFIX;
}
