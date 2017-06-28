package net.lebedko.web.util.constant;


/**
 * alexandr.lebedko : 12.06.2017
 */

public class Commands {
    private Commands() {
    }

    public static final String CONTROLLER = "/app";
    public static final String GET_PREFIX = "GET:" + CONTROLLER + "/";
    public static final String POST_PREFIX = "POST:" + CONTROLLER + "/";
    public static final String GET_LOGIN = GET_PREFIX + Views.LOGIN;
    public static final String POST_LOGIN = POST_PREFIX + Views.LOGIN;
    public static final String GET_REGISTRATION = GET_PREFIX + Views.REGISTRATION;
    public static final String POST_REGISTRATION = POST_PREFIX + Views.REGISTRATION;

}
