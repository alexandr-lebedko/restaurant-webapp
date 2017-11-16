package net.lebedko.web.util.constant;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public final class WebConstant {
    private WebConstant() {
    }

    public static final class PAGE {
        public final static String PREFIX = "/WEB-INF/views/";
        public final static String SUFFIX = ".jsp";

        public static final String SIGN_IN = PREFIX + "signIn" + SUFFIX;
        public static final String SIGN_UP = PREFIX + "signUp" + SUFFIX;
        public static final String ADMIN_MENU = PREFIX + "admin/menu" + SUFFIX;
        public static final String ADMIN_MAIN = PREFIX + "admin/main" + SUFFIX;
        public static final String NEW_CATEGORY = PREFIX + "admin/newCategory" + SUFFIX;
        public static final String NEW_ITEM_PAGE = PREFIX + "admin/newItem" + SUFFIX;
        public static final String CLIENT_MAIN = PREFIX + "client/categories" + SUFFIX;
        public static final String CLIENT_MENU_ITEMS = PREFIX + "client/items" + SUFFIX;
        public static final String CLIENT_ORDER_FORM = PREFIX + "client/orderForm" + SUFFIX;
        public static final String CLIENT_ORDERS = PREFIX + "client/orders" + SUFFIX;
        public static final String CLIENT_ORDER = PREFIX + "client/order" + SUFFIX;
        public static final String CLIENT_INVOICES = PREFIX + "client/invoices" + SUFFIX;
        public static final String CLIENT_INVOICE = PREFIX + "client/invoice" + SUFFIX;

        public static final String ADMIN_ORDERS = PREFIX + "admin/newOrders" + SUFFIX;
        public static final String ADMIN_ORDER_DETAILS = PREFIX + "admin/orderDetails" + SUFFIX;
        public static final String ADMIN_ORDERS_PAGE = PREFIX + "admin/orders" + SUFFIX;
        public static final String ADMIN_INVOICES = PREFIX + "admin/invoices" + SUFFIX;
        public static final String ADMIN_INVOICE = PREFIX + "admin/invoice" + SUFFIX;
    }

    public static final class COMMAND {
        public static final String GET_PREFIX = "GET:";
        public static final String POST_PREFIX = "POST:";

        public static final String GET_SIGN_UP = GET_PREFIX + URL.SIGN_UP;
        public static final String POST_SIGN_UP = POST_PREFIX + URL.SIGN_UP;
        public static final String SIGN_OUT = GET_PREFIX + URL.SIGN_OUT;

        public static final String GET_CLIENT_MAIN = GET_PREFIX + URL.CLIENT_CATEGORIES;
        public static final String CLIENT_GET_ITEMS = GET_PREFIX + URL.CLIENT_MENU_ITEMS;
        public static final String ADD_ITEM_TO_BUCKET = POST_PREFIX + URL.CLIENT_MENU_ITEMS;
        public static final String GET_CLIENT_ORDER = GET_PREFIX + URL.CLIENT_ORDER_FORM;
        public static final String POST_CLIENT_ORDER = POST_PREFIX + URL.CLIENT_ORDER_FORM;

        public static final String GET_ADMIN_MENU = GET_PREFIX + URL.ADMIN_MENU;
        public static final String GET_ADMIN_NEW_CATEGORY = GET_PREFIX + URL.ADMIN_NEW_CATEGORY;

        public static final String GET_ADMIN_NEW_ORDERS = GET_PREFIX + URL.ADMIN_NEW_ORDERS;
        public static final String GET_ADMIN_ORDER_DETAILS = GET_PREFIX + URL.ADMIN_ORDER_DETAILS;
        public static final String ADMIN_PROCESS_ORDER = POST_PREFIX + URL.ADMIN_PROCESS_ORDER;
        public static final String ADMIN_REJECT_ORDER = POST_PREFIX + URL.ADMIN_REJECT_ORDER;
        public static final String ADMIN_MODIFY_ORDER = POST_PREFIX + URL.ADMIN_MODIFY_ORDER;
        public static final String ADMIN_GET_MODIFIED_ORDERS = GET_PREFIX + URL.ADMIN_MODIFIED_ORDERS;
        public static final String ADMIN_GET_REJECTED_ORDERS = GET_PREFIX + URL.ADMIN_REJECTED_ORDERS;
        public static final String ADMIN_GET_PROCESSED_ORDERS = GET_PREFIX + URL.ADMIN_PROCESSED_ORDERS;
        public static final String ADMIN_GET_CLOSED_INVOICES = GET_PREFIX + URL.ADMIN_CLOSED_INVOICES;
        public static final String ADMIN_GET_ACTIVE_INVOICES = GET_PREFIX + URL.ADMIN_ACTIVE_INVOICES;
        public static final String ADMIN_GET_PAID_INVOICES = GET_PREFIX + URL.ADMIN_PAID_INVOICES;
        public static final String ADMIN_GET_UNPAID_INVOICES = GET_PREFIX + URL.ADMIN_UNPAID_INVOICES;
        public static final String ADMIN_GET_INVOICE = GET_PREFIX + URL.ADMIN_INVOICE;

        public static final String GET_ADMIN_NEW_ITEM = GET_PREFIX + URL.NEW_ITEM_URL;
        public static final String POST_ADMIN_NEW_ITEM = POST_PREFIX + URL.NEW_ITEM_URL;

        public static final String CLIENT_GET_INVOICE = GET_PREFIX + URL.CLIENT_INVOICE;
        public static final String GET_CLIENT_ORDERS = GET_PREFIX + URL.CLIENT_ORDERS;
        public static final String CLIENT_GET_INVOICES = GET_PREFIX + URL.CLIENT_INVOICES;
        public static final String CLIENT_CLOSE_INVOICE = POST_PREFIX + URL.CLIENT_CLOSE_INVOICE;
        public static final String CLIENT_GET_ORDER = GET_PREFIX + URL.CLIENT_ORDER;
        public static final String CLIENT_REJECT_ORDER = POST_PREFIX + URL.CLIENT_REJECT_ORDER;
        public static final String CLIENT_SUBMIT_MODIFIED_ORDER = POST_PREFIX + URL.CLIENT_SUBMIT_MODIFIED_ORDER;
        public static final String CLIENT_MODIFY_ORDER = POST_PREFIX + URL.CLIENT_MODIFY_ORDER;

    }

}
