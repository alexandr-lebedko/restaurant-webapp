package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.demo.item.CategoryView;
import net.lebedko.service.demo.CategoryService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;

import java.util.Collection;
import java.util.Locale;

import static net.lebedko.web.util.constant.WebConstant.PAGE.NEW_ITEM_PAGE;


/**
 * alexandr.lebedko : 27.08.2017.
 */
public class NewItemGetCommand extends AbstractCommand {
    private static final IResponseAction ITEM_FORM_FORWARD = new ForwardAction(NEW_ITEM_PAGE);

    private CategoryService categoryService;

    public NewItemGetCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {

        addCategoriesToRequestAttribute(context);
        return ITEM_FORM_FORWARD;
    }

    private void addCategoriesToRequestAttribute(IContext context) throws ServiceException {
        context.addRequestAttribute("categories", getAllCategories(context));
    }

    private Collection<CategoryView> getAllCategories(IContext context) throws ServiceException {
        return categoryService.getAll(context.getSessionAttribute(Locale.class, "lang"));
    }


}
