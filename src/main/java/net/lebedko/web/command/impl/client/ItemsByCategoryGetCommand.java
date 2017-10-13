package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.CategoryView;
import net.lebedko.entity.item.ItemView;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;

import java.util.Collection;
import java.util.Locale;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

/**
 * alexandr.lebedko : 18.09.2017.
 */
public class ItemsByCategoryGetCommand extends AbstractCommand {
    private static final IResponseAction CATEGORIES_REDIRECT = new RedirectAction(WebConstant.URL.CLIENT_CATEGORIES);
    private static final IResponseAction ITEMS_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_MENU_ITEMS);

    private CategoryService categoryService;
    private ItemService itemService;

    public ItemsByCategoryGetCommand(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(final IContext context) throws ServiceException {

        final Errors errors = new Errors();

        final Category category = getCategory(context, errors);

        if (errors.hasErrors()) {
            context.addRequestAttribute("categories", getCategories(context));
            //TODO: or maybe redirect to 404 page??
            return CATEGORIES_REDIRECT;
        }

        context.addRequestAttribute("items", getItems(context, category));

        return ITEMS_FORWARD;
    }

    private Category getCategory(final IContext context, final Errors errors) throws ServiceException {
        final Integer categoryId = ofNullable(getCategoryId(context)).orElse(-1);

        final Category category = categoryService.getByID(categoryId);

        if (isNull(category)) {
            //TODO: localize error
            LOG.error("CATEGORY WITH CURRENT ID NOT EXISTS");
            errors.register("noSuchCategory", "CATEGORY WITH CURRENT ID NOT EXISTS");
        }

        return category;
    }

    private Integer getCategoryId(final IContext context) {
        Integer categoryId = null;
        try {
            return Integer.valueOf(context.getRequestParameter("category"));
        } catch (NumberFormatException e) {
            LOG.error(e);
        }

        if (isNull(categoryId)) {
            try {
                return Integer.valueOf(context.getRequestAttribute("category"));
            } catch (NumberFormatException e) {
                LOG.error(e);
            }
        }
        return categoryId;
    }

    private Collection<ItemView> getItems(final IContext context, final Category category) throws ServiceException {
        return itemService.getByCategory(category, context.getSessionAttribute(Locale.class, SupportedLocales.getLocaleSessionAttributeName()));
    }

    private Collection<CategoryView> getCategories(final IContext context) throws ServiceException {
        return categoryService.getAll(context.getSessionAttribute(Locale.class, SupportedLocales.getLocaleSessionAttributeName()));
    }


}
