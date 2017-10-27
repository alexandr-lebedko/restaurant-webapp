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
        public static final String CLIENT_MAIN = PREFIX + "client/categories" + SUFFIX;
        public static final String CLIENT_MENU_ITEMS = PREFIX + "client/items" + SUFFIX;
        public static final String CLIENT_ORDER_FORM = PREFIX + "client/orderForm" + SUFFIX;
        public static final String CLIENT_ORDERS = PREFIX + "client/orders" + SUFFIX;
    }

    public static final class COMMAND {
        public static final String GET_PREFIX = "GET:";
        public static final String POST_PREFIX = "POST:";

        public static final String GET_SIGN_UP = GET_PREFIX + URL.SIGN_UP;
        public static final String POST_SIGN_UP = POST_PREFIX + URL.SIGN_UP;
        public static final String SIGN_OUT = GET_PREFIX + URL.SIGN_OUT;

        public static final String GET_CLIENT_MAIN = GET_PREFIX + URL.CLIENT_CATEGORIES;
        public static final String GET_CLIENT_MENU_ITEMS = GET_PREFIX + URL.CLIENT_MENU_ITEMS;
        public static final String POST_CLIENT_MENU_ITEMS = POST_PREFIX + URL.CLIENT_MENU_ITEMS;
        public static final String GET_CLIENT_ORDER = GET_PREFIX + URL.CLIENT_ORDER_FORM;
        public static final String POST_CLIENT_ORDER = POST_PREFIX + URL.CLIENT_ORDER_FORM;

        public static final String GET_ADMIN_MENU = GET_PREFIX + URL.ADMIN_MENU;
        public static final String GET_ADMIN_NEW_CATEGORY = GET_PREFIX + URL.ADMIN_NEW_CATEGORY;

        public static final String GET_ADMIN_NEW_ITEM = GET_PREFIX + URL.NEW_ITEM_URL;
        public static final String POST_ADMIN_NEW_ITEM = POST_PREFIX + URL.NEW_ITEM_URL;

        public static final String GET_CLIENT_ORDERS = GET_PREFIX + URL.CLIENT_ORDERS;
    }


}
