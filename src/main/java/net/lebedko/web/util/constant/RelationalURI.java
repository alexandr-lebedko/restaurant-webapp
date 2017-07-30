package net.lebedko.web.util.constant;

/**
 * alexandr.lebedko : 24.07.2017.
 */
public class RelationalURI {
    private RelationalURI() {
    }

    private static final String CONTROLLER_NAME = "app";
    private static final String PREFIX = CONTROLLER_NAME + "/";

    public static final String CONTROLLER_URL_PATTERN = "/" + CONTROLLER_NAME + "/*";

    public static final String SIGN_IN = PREFIX + Views.SIGN_IN;
    public static final String SIGN_UP = PREFIX + Views.SIGN_UP;
    public static final String ADMIN_MAIN = PREFIX + Views.ADMIN_MAIN;
    public static final String CLIENT_MAIN = PREFIX + Views.CLIENT_MAIN;

    public static final String NEW_DISH = PREFIX + Views.NEW_DISH;

}
