package net.lebedko.web.util.constant;

public class URL {

    public static final String CONTROLLER_NAME = "app";
    public static final String IMAGE_CONTROLLER = "images";
    public static final String CONTROLLER_PATTERN = CONTROLLER_NAME + "/*";

    public static final String IMAGE_PREFIX = "/" + IMAGE_CONTROLLER + "/";
    public static final String PREFIX = "/" + CONTROLLER_NAME + "/";

    public static final String SIGN_IN = PREFIX + "signIn";
    public static final String SIGN_UP = PREFIX + "signUp";
    public static final String SIGN_OUT = PREFIX + "signOut";

    public static final String ADMIN_NEW_CATEGORY = PREFIX + "admin/menu/category/new";
    public static final String NEW_ITEM_URL = PREFIX + "admin/menu/item/new";

    public static final String ADMIN_MAIN = PREFIX + "admin/main";
    public static final String ADMIN_MENU = PREFIX + "admin/menu";
    public static final String ADMIN_NEW_ORDERS = PREFIX + "admin/orders/new";
    public static final String ADMIN_PROCESSED_ORDERS = PREFIX + "admin/orders/processed";
    public static final String ADMIN_MODIFIED_ORDERS = PREFIX + "admin/orders/modified";
    public static final String ADMIN_REJECTED_ORDERS = PREFIX + "admin/orders/rejected";
    public static final String ADMIN_INVOICES = PREFIX + "admin/invoices";
    public static final String ADMIN_ORDER_DETAILS = PREFIX + "admin/order/details";
    public static final String ADMIN_PROCESS_ORDER = PREFIX + "admin/order/process";
    public static final String ADMIN_REJECT_ORDER = PREFIX + "admin/order/reject";
    public static final String ADMIN_MODIFY_ORDER = PREFIX + "admin/order/modify";
    public static final String ADMIN_ACTIVE_INVOICES = PREFIX + "admin/invoices/active";
    public static final String ADMIN_CLOSED_INVOICES = PREFIX + "admin/invoices/closed";
    public static final String ADMIN_UNPAID_INVOICES = PREFIX + "admin/invoices/unpaid";
    public static final String ADMIN_PAID_INVOICES = PREFIX + "admin/invoices/paid";
    public static final String ADMIN_INVOICE = PREFIX + "admin/invoice";

    public static final String CLIENT_CATEGORIES = PREFIX + "client/categories";
    public static final String CLIENT_MENU_ITEMS = PREFIX + "client/items";
    public static final String CLIENT_ORDER_FORM = PREFIX + "client/orderForm";
    public static final String CLIENT_ORDERS = PREFIX + "client/orders";
    public static final String CLIENT_INVOICE_DETAILS = PREFIX + "client/invoice";
    public static final String CLIENT_INVOICES = PREFIX + "client/invoices";
    public static final String CLIENT_CLOSE_INVOICE = PREFIX + "client/invoices/close";
}
