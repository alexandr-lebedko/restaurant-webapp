package net.lebedko.web.command.impl.admin;


import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.*;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.item.MenuItemValidator;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

import static java.util.Optional.ofNullable;
import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.web.util.constant.WebConstant.PAGE.NEW_ITEM_PAGE;

public class NewItemPostCommand extends AbstractCommand {
    private static final IResponseAction NEW_ITEM_PAGE_FORWARD = new ForwardAction(NEW_ITEM_PAGE);

    private ImageValidator imageValidator;
    private MenuItemValidator itemValidator;
    private CategoryService categoryService;
    private ItemService itemService;

    public NewItemPostCommand(ImageValidator imageValidator, MenuItemValidator itemValidator, CategoryService categoryService, ItemService itemService) {
        this.imageValidator = imageValidator;
        this.itemValidator = itemValidator;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Item item = getMenuItem(context);
        final InputStream image = getImage(context);

        final Errors errors = new Errors();

        validateItem(item, errors);
        validateImage(context, errors);

        if (errors.hasErrors()) {
            context.addErrors(errors);
            addCategoriesToRequest(context);
            addItemToRequestAttribute(context, item);
            return NEW_ITEM_PAGE_FORWARD;
        }

        return saveItemAndImage(item, image, context, errors);
    }

    private IResponseAction saveItemAndImage(Item item, InputStream image, IContext context, Errors errors) throws ServiceException {
        try {
            Item newItem = itemService.insertItemAndImage(item, image);
            context.addRequestAttribute("newItem", newItem);
            return NEW_ITEM_PAGE_FORWARD;
        } catch (EntityExistsException eee) {
            LOG.error(eee);
            errors.register("itemExists", PageErrorNames.ITEM_EXISTS);
        }

        context.addErrors(errors);
        addItemToRequestAttribute(context, item);
        addCategoriesToRequest(context);
        return NEW_ITEM_PAGE_FORWARD;
    }

    private void addItemToRequestAttribute(IContext context, Item item) {
        context.addRequestAttribute("item", item);
    }

    private void addCategoriesToRequest(IContext context) throws ServiceException {
        context.addRequestAttribute("categories", getCategories(context));
    }

    private Collection<CategoryView> getCategories(IContext context) throws ServiceException {
        return categoryService.getAll(context.getSessionAttribute(Locale.class, SupportedLocales.getLocaleSessionAttributeName()));
    }

    private Item getMenuItem(IContext context) throws ServiceException {
        return new Item(
                getItemInfo(context),
                getItemState(context)
        );
    }


    private ItemInfo getItemInfo(IContext context) throws ServiceException {
        return new ItemInfo(
                getTitle(context),
                getDescription(context),
                getCategory(context),
                getPrice(context)
        );
    }

    private ItemState getItemState(IContext context) {
        ItemState state = null;
        try {
            state = ItemState.valueOf(context.getRequestParameter("state"));
        } catch (IllegalArgumentException e) {
            /*NOP*/
        }
        return state;
    }

    private Category getCategory(IContext context) throws ServiceException {
        Integer categoryId = -1;
        try {
            categoryId = Integer.parseInt(context.getRequestParameter("category"));
        } catch (NumberFormatException nfe) {
                /*NOP*/
        }
        System.out.println("CATEGORY: " + categoryService.getByID(categoryId));

        return categoryService.getByID(categoryId);
    }


    private Title getTitle(IContext context) {
        final StringI18N title = new StringI18N();

        title.add(getByCode(EN_CODE), ofNullable(context.getRequestParameter("title_en")).orElse(""));
        title.add(getByCode(UA_CODE), ofNullable(context.getRequestParameter("title_ua")).orElse(""));
        title.add(getByCode(RU_CODE), ofNullable(context.getRequestParameter("title_ru")).orElse(""));
        return new Title(title);
    }

    private Description getDescription(IContext context) {
        final StringI18N description = new StringI18N();

        description.add(getByCode(EN_CODE), ofNullable(context.getRequestParameter("description_en")).orElse(""));
        description.add(getByCode(UA_CODE), ofNullable(context.getRequestParameter("description_ua")).orElse(""));
        description.add(getByCode(RU_CODE), ofNullable(context.getRequestParameter("description_ru")).orElse(""));

        return new Description(description);
    }

    private Price getPrice(IContext context) {
        Double priceValue = -1.;
        try {
            priceValue = Double.valueOf(ofNullable(context.getRequestParameter("price")).orElse("-1."));
        } catch (NumberFormatException nfe) {
                /* NOP */
        }
        return new Price(priceValue);
    }

    private InputStream getImage(IContext context) throws ServiceException {
        return getInputStream(context, "itemImage");
    }

    private void validateImage(IContext context, Errors errors) throws ServiceException {
        imageValidator.validate(getImage(context), errors);
    }

    private void validateItem(Item item, Errors errors) {
        itemValidator.validate(item, errors);
    }

}
