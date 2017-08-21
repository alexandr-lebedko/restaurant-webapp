package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.demo.item.CategoryView;
import net.lebedko.service.demo.CategoryService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageLocations;

import java.util.Collection;
import java.util.Locale;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class AdminMenuGetCommand extends AbstractCommand {
    private static final IResponseAction ADMIN_MENU_FORWARD = new ForwardAction(PageLocations.ADMIN_MENU);

    private CategoryService categoryService;

    public AdminMenuGetCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Locale locale = context.getLocale();

        Collection<CategoryView> categories = categoryService.getAll(locale);
        context.addRequestAttribute("categories", categories);

        return ADMIN_MENU_FORWARD;
    }
}
