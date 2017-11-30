package net.lebedko.web.util.constant;

public final class WebConstant {
    private WebConstant() {
    }

    public static final class PAGE {
        public final static String PREFIX = "/WEB-INF/views/";
        public final static String SUFFIX = ".jsp";

        public static final String SIGN_IN = PREFIX + "signIn" + SUFFIX;
        public static final String SIGN_UP = PREFIX + "signUp" + SUFFIX;

        public static final String CLIENT_MENU = PREFIX + "client/menu" + SUFFIX;
        public static final String CLIENT_ORDER_FORM = PREFIX + "client/orderForm" + SUFFIX;
        public static final String CLIENT_ORDERS = PREFIX + "client/orders" + SUFFIX;
        public static final String CLIENT_ORDER = PREFIX + "client/order" + SUFFIX;
        public static final String CLIENT_INVOICES = PREFIX + "client/invoices" + SUFFIX;
        public static final String CLIENT_INVOICE = PREFIX + "client/invoice" + SUFFIX;

        public static final String ADMIN_CATEGORIES = PREFIX + "admin/categories" + SUFFIX;
        public static final String ADMIN_ORDERS = PREFIX + "admin/orders" + SUFFIX;
        public static final String ADMIN_INVOICES = PREFIX + "admin/invoices" + SUFFIX;
        public static final String ADMIN_INVOICE = PREFIX + "admin/invoice" + SUFFIX;
        public static final String ADMIN_ITEMS = PREFIX + "admin/items" + SUFFIX;
        public static final String ADMIN_ITEM_FORM = PREFIX + "admin/item" + SUFFIX;
        public static final String ADMIN_ORDER = PREFIX + "admin/order" + SUFFIX;
    }

    public static final class COMMAND {
        public static final String GET_PREFIX = "GET:";
        public static final String POST_PREFIX = "POST:";

        public static final String GET_SIGN_IN = GET_PREFIX + URL.SIGN_IN;
        public static final String GET_SIGN_UP = GET_PREFIX + URL.SIGN_UP;
        public static final String POST_SIGN_UP = POST_PREFIX + URL.SIGN_UP;
        public static final String POST_SIGN_IN = POST_PREFIX + URL.SIGN_IN;
        public static final String SIGN_OUT = GET_PREFIX + URL.SIGN_OUT;

        public static final String ADMIN_GET_ITEMS = GET_PREFIX + URL.ADMIN_ITEMS;
        public static final String ADMIN_GET_ORDER = GET_PREFIX + URL.ADMIN_ORDER;
        public static final String ADMIN_GET_ORDERS = GET_PREFIX + URL.ADMIN_ORDERS;
        public static final String ADMIN_GET_INVOICE = GET_PREFIX + URL.ADMIN_INVOICE;
        public static final String ADMIN_GET_INVOICES = GET_PREFIX + URL.ADMIN_INVOICES;
        public static final String ADMIN_GET_CATEGORIES = GET_PREFIX + URL.ADMIN_CATEGORIES;
        public static final String ADMIN_PROCESS_ORDER = POST_PREFIX + URL.ADMIN_PROCESS_ORDER;
        public static final String ADMIN_REJECT_ORDER = POST_PREFIX + URL.ADMIN_REJECT_ORDER;
        public static final String ADMIN_MODIFY_ORDER = POST_PREFIX + URL.ADMIN_MODIFY_ORDER;
        public static final String ADMIN_MODIFY_CATEGORY = POST_PREFIX + URL.ADMIN_MODIFY_CATEGORY;
        public static final String ADMIN_CREATE_CATEGORY = POST_PREFIX + URL.ADMIN_CREATE_CATEGORY;
        public static final String ADMIN_DELETE_CATEGORY = POST_PREFIX + URL.ADMIN_DELETE_CATEGORY;
        public static final String ADMIN_MODIFY_ITEM = POST_PREFIX + URL.ADMIN_MODIFY_ITEM;
        public static final String ADMIN_MODIFY_ITEM_IMAGE = POST_PREFIX + URL.ADMIN_MODIFY_ITEM_IMAGE;
        public static final String ADMIN_ITEM_FORM = GET_PREFIX + URL.ADMIN_CREATE_ITEM;
        public static final String ADMIN_CREATE_ITEM = POST_PREFIX + URL.ADMIN_CREATE_ITEM;

        public static final String CLIENT_GET_MENU = GET_PREFIX + URL.CLIENT_MENU;
        public static final String CLIENT_GET_INVOICE = GET_PREFIX + URL.CLIENT_INVOICE;
        public static final String CLIENT_PAY_INVOICE = POST_PREFIX + URL.CLIENT_PAY_INVOICE;
        public static final String CLIENT_GET_ORDERS = GET_PREFIX + URL.CLIENT_ORDERS;
        public static final String CLIENT_GET_INVOICES = GET_PREFIX + URL.CLIENT_INVOICES;
        public static final String CLIENT_GET_ORDER = GET_PREFIX + URL.CLIENT_ORDER;
        public static final String CLIENT_REJECT_ORDER = POST_PREFIX + URL.CLIENT_REJECT_ORDER;
        public static final String CLIENT_SUBMIT_MODIFIED_ORDER = POST_PREFIX + URL.CLIENT_SUBMIT_MODIFIED_ORDER;
        public static final String CLIENT_MODIFY_ORDER = POST_PREFIX + URL.CLIENT_MODIFY_ORDER;
        public static final String CLIENT_ADD_ITEM_TO_BUCKET = POST_PREFIX + URL.CLIENT_ITEM_ADD;
        public static final String CLIENT_GET_ORDER_FORM = GET_PREFIX + URL.CLIENT_ORDER_FORM;
        public static final String CLIENT_CREATE_ORDER = POST_PREFIX + URL.CLIENT_ORDER_FORM;
        public static final String CLIENT_CLEAR_ORDER_BUCKET = POST_PREFIX + URL.CLIENT_CLEAR_ORDER_BUCKET;
    }

}
