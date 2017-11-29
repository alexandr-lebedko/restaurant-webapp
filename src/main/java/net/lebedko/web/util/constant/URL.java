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

    public static final String ADMIN_ORDER = PREFIX + "admin/order";
    public static final String ADMIN_ORDERS = PREFIX + "admin/orders";
    public static final String ADMIN_INVOICE = PREFIX + "admin/invoice";
    public static final String ADMIN_CATEGORIES = PREFIX + "admin/categories";
    public static final String ADMIN_PROCESS_ORDER = PREFIX + "admin/order/process";
    public static final String ADMIN_REJECT_ORDER = PREFIX + "admin/order/reject";
    public static final String ADMIN_MODIFY_ORDER = PREFIX + "admin/order/modify";
    public static final String ADMIN_UNPAID_INVOICES = PREFIX + "admin/invoices/unpaid";
    public static final String ADMIN_MODIFY_CATEGORY = PREFIX + "admin/category/modify";
    public static final String ADMIN_CREATE_CATEGORY = PREFIX + "admin/category/create";
    public static final String ADMIN_DELETE_CATEGORY = PREFIX + "admin/category/delete";
    public static final String ADMIN_PAID_INVOICES = PREFIX + "admin/invoices/paid";
    public static final String ADMIN_ITEMS = PREFIX + "admin/items";
    public static final String ADMIN_CREATE_ITEM = PREFIX + "admin/item/create";
    public static final String ADMIN_MODIFY_ITEM = PREFIX + "admin/item/modify";
    public static final String ADMIN_MODIFY_ITEM_IMAGE = PREFIX + "admin/item/image/modify";

    public static final String CLIENT_MENU = PREFIX + "client/menu";
    public static final String CLIENT_ORDER = PREFIX + "client/order";
    public static final String CLIENT_ORDERS = PREFIX + "client/orders";
    public static final String CLIENT_REJECT_ORDER = PREFIX + "client/order/reject";
    public static final String CLIENT_SUBMIT_MODIFIED_ORDER = PREFIX + "client/order/submit";
    public static final String CLIENT_MODIFY_ORDER = PREFIX + "client/order/modify";
    public static final String CLIENT_ITEM_ADD = PREFIX + "client/item/add";
    public static final String CLIENT_ORDER_FORM = PREFIX + "client/order/create";
    public static final String CLIENT_INVOICE = PREFIX + "client/invoice";
    public static final String CLIENT_PAY_INVOICE = PREFIX + "client/invoice/pay";
    public static final String CLIENT_INVOICES = PREFIX + "client/invoices";
    public static final String CLIENT_CLEAR_ORDER_BUCKET = PREFIX + "client/order/clear";
}
