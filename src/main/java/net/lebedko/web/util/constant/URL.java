package net.lebedko.web.util.constant;

public class URL {

    public static final String CONTROLLER_NAME = "app";
    public static final String IMAGE_CONTROLLER ="images";
    public static final String CONTROLLER_PATTERN = CONTROLLER_NAME + "/*";

    public static final String IMAGE_PREFIX = "/" + IMAGE_CONTROLLER + "/";
    public static final String PREFIX = "/" + CONTROLLER_NAME + "/";

    public static final String SIGN_IN = PREFIX + "signIn";
    public static final String SIGN_UP = PREFIX + "signUp";
    public static final String SIGN_OUT = PREFIX + "signOut";

    public static final String ADMIN_MENU = PREFIX + "admin/menu";
    public static final String ADMIN_NEW_CATEGORY = PREFIX + "admin/menu/category/new";

    public static final String NEW_ITEM_URL = PREFIX + "admin/menu/item/new";

    public static final String ADMIN_MAIN = PREFIX + "admin/main";
    public static final String CLIENT_CATEGORIES = PREFIX + "client/categories";
    public static final String CLIENT_MENU_ITEMS = PREFIX + "client/items";
    public static final String CLIENT_ORDER_FORM = PREFIX + "client/orderForm";
    public static final String CLIENT_ORDERS = PREFIX + "client/orders";
    public static final String CLIENT_ORDER_DETAILS = PREFIX + "client/order";
    public static final String CLIENT_INVOICE_DETAILS = PREFIX + "client/invoice";
    public static final String CLIENT_INVOICES = PREFIX + "client/invoices";
    public static final String SUCCESS_ORDER = PREFIX + "client/successOrder";
}
