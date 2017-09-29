package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.CategoryView;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.service.CategoryService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;
import java.util.Locale;

/**
 * alexandr.lebedko : 09.09.2017.
 */
public class MainPageGetCommand extends AbstractCommand {
    private static final IResponseAction CLIENT_MAIN_PAGE_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_MAIN);

    private CategoryService categoryService;

    public MainPageGetCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        addCategoriesToRequestAttribute(context);
        return CLIENT_MAIN_PAGE_FORWARD;
    }

    private void addCategoriesToRequestAttribute(IContext context) throws ServiceException {
        Collection<CategoryView> categories = categoryService.getAll(context.getSessionAttribute(Locale.class, SupportedLocales.getLocaleSessionAttributeName()));
        context.addRequestAttribute("categories", categories);
    }
}
