package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.CategoryView;
import net.lebedko.service.CategoryService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;

import java.util.Collection;
import java.util.Locale;

import static net.lebedko.i18n.SupportedLocales.getLocaleSessionAttributeName;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class AdminMenuGetCommand extends AbstractCommand {
//    private static final IResponseAction ADMIN_MENU_FORWARD = new ForwardAction(PageLocations.ADMIN_MENU);

    private CategoryService categoryService;

    public AdminMenuGetCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        //TODO : REMOVE!
        final Locale locale = context.getSessionAttribute(Locale.class, getLocaleSessionAttributeName());


        Collection<CategoryView> categories = categoryService.getAll(locale);
        context.addRequestAttribute("categories", categories.toArray());
        return null;
//        return ADMIN_MENU_FORWARD;
    }
}
