package net.lebedko.web.util.constant;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public final class WebConstant {
    public WebConstant() {
    }

    public static final class PAGE {
        public final static String PREFIX = "/WEB-INF/views/";
        public final static String SUFFIX = ".jsp";

        public static final String SIGN_IN = PREFIX + "signIn" + SUFFIX;
        public static final String SIGN_UP = PREFIX + "signUp" + SUFFIX;
        public static final String ADMIN_MENU = PREFIX + "admin/menu" + SUFFIX;
        public static final String NEW_CATEGORY = PREFIX + "admin/newCategory" + SUFFIX;
        public static final String NEW_ITEM_PAGE = PREFIX + "admin/newItem" + SUFFIX;
        public static final String CLIENT_MAIN =PREFIX +  "client/main" + SUFFIX;
    }

    public static final class URL {

        public static final String CONTROLLER_NAME = "app";
        public static final String CONTROLLER_PATTERN = CONTROLLER_NAME + "/*";

        public static final String PREFIX = "/" + CONTROLLER_NAME + "/";

        public static final String SIGN_IN = PREFIX + "signIn";
        public static final String SIGN_OUT = PREFIX + "signOut";

        public static final String ADMIN_MENU = PREFIX + "admin/menu";
        public static final String ADMIN_NEW_CATEGORY = PREFIX + "admin/menu/category/new";

        public static final String NEW_ITEM_URL = PREFIX + "admin/menu/item/new";

        public static final String ADMIN_MAIN = PREFIX + "admin/main";
        public static final String CLIENT_MAIN = PREFIX + "client/main";
    }

    public static final class COMMAND {
        public static final String GET_PREFIX = "GET:";
        public static final String POST_PREFIX = "POST:";

        public static final String SIGN_OUT=GET_PREFIX + URL.SIGN_OUT;

        public static final String GET_CLIENT_MAIN=GET_PREFIX+URL.CLIENT_MAIN;

        public static final String GET_ADMIN_MENU = GET_PREFIX + URL.ADMIN_MENU;
        public static final String GET_ADMIN_NEW_CATEGORY = GET_PREFIX + URL.ADMIN_NEW_CATEGORY;

        public static final String GET_ADMIN_NEW_ITEM = GET_PREFIX + URL.NEW_ITEM_URL;
        public static final String POST_ADMIN_NEW_ITEM = POST_PREFIX + URL.NEW_ITEM_URL;
    }


}
