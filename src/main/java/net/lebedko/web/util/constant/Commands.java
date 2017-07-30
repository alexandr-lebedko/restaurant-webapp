package net.lebedko.web.util.constant;


/**
 * alexandr.lebedko : 12.06.2017
 */

public class Commands {
    private Commands() {
    }

    private static final String GET_PREFIX = "GET:/";
    private static final String POST_PREFIX = "POST:/";

    public static final String GET_SIGN_IN = GET_PREFIX + RelationalURI.SIGN_IN;
    public static final String POST_SIGN_IN = POST_PREFIX + RelationalURI.SIGN_IN;

    public static final String GET_SIGN_UP = GET_PREFIX + RelationalURI.SIGN_UP;
    public static final String POST_SIGN_UP = POST_PREFIX + RelationalURI.SIGN_UP;

    public static final String GET_ADMIN_MAIN = GET_PREFIX + RelationalURI.ADMIN_MAIN;
    public static final String GET_NEW_DISH = GET_PREFIX + RelationalURI.NEW_DISH;

}
