package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.Title;
import net.lebedko.service.CategoryService;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.item.ItemValidator;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminModifyItemCommand extends AbstractAdminCommand {
    private static final IResponseAction ITEMS_FORWARD = new ForwardAction(PAGE.ADMIN_ITEMS);
    private static final String ITEMS_TEMPLATE = URL.ADMIN_ITEMS.concat("?").concat(Attribute.CATEGORY_ID).concat("=");

    private CategoryService categoryService;
    private ItemService itemService;
    private ItemValidator itemValidator;

    public AdminModifyItemCommand(OrderService orderService, InvoiceService invoiceService, CategoryService categoryService, ItemService itemService, ItemValidator itemValidator) {
        super(orderService, invoiceService);
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.itemValidator = itemValidator;
    }

    public AdminModifyItemCommand(OrderService orderService, InvoiceService invoiceService, ItemService itemService, CategoryService categoryService) {
        this(orderService, invoiceService, categoryService, itemService, new ItemValidator());
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Long categoryId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L);
        final Item item = parseItem(context, categoryId);
        final Errors errors = new Errors();

        itemValidator.validate(item, errors);
        try {
            if (!errors.hasErrors()) {
                itemService.update(item);
                return new RedirectAction(ITEMS_TEMPLATE.concat(categoryId.toString()));
            }
        } catch (IllegalArgumentException e) {
            LOG.error(e);
            errors.register("itemExists", PageErrorNames.ITEM_EXISTS);
        }

        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.ITEMS, itemService.getByCategory(item.getCategory()));
        context.addRequestAttribute(Attribute.MODIFIED_ITEM, item);
        context.addErrors(errors);

        return ITEMS_FORWARD;
    }

    public Item parseItem(IContext context, Long categoryId) {
        final Long itemId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID), -1L);
        final Title title = CommandUtils.parseTitle(context);
        final Description description = CommandUtils.parseDescription(context);
        final Price price = CommandUtils.parsePrice(context);
        final Category category = categoryService.getById(categoryId);
        final String imageId =context.getRequestParameter(Attribute.IMAGE_ID);

        return new Item(itemId, title, description, category, price, imageId);
    }
}
//public class NewItemPostCommand extends AbstractCommand {
////    private static final IResponseAction NEW_ITEM_PAGE_FORWARD = new ForwardAction(NEW_ITEM_PAGE);
//
//    private ImageValidator imageValidator;
//    private MenuItemValidator itemValidator;
//    private CategoryService categoryService;
//    private ItemService itemService;
//
//    public NewItemPostCommand(ImageValidator imageValidator, MenuItemValidator itemValidator, CategoryService categoryService, ItemService itemService) {
//        this.imageValidator = imageValidator;
//        this.itemValidator = itemValidator;
//        this.categoryService = categoryService;
//        this.itemService = itemService;
//    }
//
//    @Override
//    protected IResponseAction doExecute(IContext context) throws ServiceException {
////        final Item item = getMenuItem(context);
////        final InputStream image = getImage(context);
////
////        final Errors errors = new Errors();
////
////        validateItem(item, errors);
////        validateImage(context, errors);
////
////        if (errors.hasErrors()) {
////            context.addErrors(errors);
////            addCategoriesToRequest(context);
////            addItemToRequestAttribute(context, item);
////            return NEW_ITEM_PAGE_FORWARD;
////        }
////
////        return saveItemAndImage(item, image, context, errors);
//        return null;
//    }
//
//    private IResponseAction saveItemAndImage(Item item, InputStream image, IContext context, Errors errors) throws ServiceException {
//        try {
//            Item newItem = itemService.insert(item, image);
//            context.addRequestAttribute("newItem", newItem);
//            return NEW_ITEM_PAGE_FORWARD;
//        } catch (EntityExistsException eee) {
//            LOG.error(eee);
//            errors.register("itemExists", PageErrorNames.ITEM_EXISTS);
//        }
//
//        context.addErrors(errors);
//        addItemToRequestAttribute(context, item);
//        addCategoriesToRequest(context);
//        return NEW_ITEM_PAGE_FORWARD;
//    }
//
//    private void addItemToRequestAttribute(IContext context, Item item) {
//        context.addRequestAttribute("item", item);
//    }
//
//    private void addCategoriesToRequest(IContext context) throws ServiceException {
//        context.addRequestAttribute("categories", getCategories(context));
//    }
//
//    private Collection<CategoryView> getCategories(IContext context) throws ServiceException {
//        return categoryService.getAll(context.getSessionAttribute(Locale.class, SupportedLocales.getLocaleSessionAttributeName()));
//    }
//
//    private Item getMenuItem(IContext context) throws ServiceException {
//        return new Item(
//                getItemInfo(context),
//                getItemState(context)
//        );
//    }


//    private ItemInfo getItemInfo(IContext context) throws ServiceException {
//        return new ItemInfo(
//                getTitle(context),
//                getDescription(context),
//                getCategory(context),
//                getPrice(context)
//        );
//    }
//
//    private ItemState getItemState(IContext context) {
//        ItemState state = null;
//        try {
//            state = ItemState.valueOf(context.getRequestParameter("state"));
//        } catch (IllegalArgumentException e) {
//            /*NOP*/
//        }
//        return state;
//    }
//
//    private Category getCategory(IContext context) throws ServiceException {
//        Integer categoryId = -1;
//        try {
//            categoryId = Integer.parseInt(context.getRequestParameter("category"));
//        } catch (NumberFormatException nfe) {
//                /*NOP*/
//        }
//        System.out.println("CATEGORY: " + categoryService.getByID(categoryId));
//
//        return categoryService.getByID(categoryId);
//    }


//    private Title getTitle(IContext context) {
//        final StringI18N title = new StringI18N();
//
//        title.add(getByCode(EN_CODE), ofNullable(context.getRequestParameter("title_en")).orElse(""));
//        title.add(getByCode(UA_CODE), ofNullable(context.getRequestParameter("title_ua")).orElse(""));
//        title.add(getByCode(RU_CODE), ofNullable(context.getRequestParameter("title_ru")).orElse(""));
//        return new Title(title);
//    }
//
//    private Description getDescription(IContext context) {
//        final StringI18N description = new StringI18N();
//
//        description.add(getByCode(EN_CODE), ofNullable(context.getRequestParameter("description_en")).orElse(""));
//        description.add(getByCode(UA_CODE), ofNullable(context.getRequestParameter("description_ua")).orElse(""));
//        description.add(getByCode(RU_CODE), ofNullable(context.getRequestParameter("description_ru")).orElse(""));
//
//        return new Description(description);
//    }
//
//    private Price getPrice(IContext context) {
//        Double priceValue = -1.;
//        try {
//            priceValue = Double.valueOf(ofNullable(context.getRequestParameter("price")).orElse("-1."));
//        } catch (NumberFormatException nfe) {
//                /* NOP */
//        }
//        return new Price(priceValue);
//    }
//
//    private InputStream getImage(IContext context) throws ServiceException {
//        return getInputStream(context, "itemImage");
//    }
//
//    private void validateImage(IContext context, Errors errors) throws ServiceException {
//        imageValidator.validate(getImage(context), errors);
//    }
//
//    private void validateItem(Item item, Errors errors) {
//        itemValidator.validate(item, errors);
//    }
//
//}