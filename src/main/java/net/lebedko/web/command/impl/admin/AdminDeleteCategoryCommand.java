package net.lebedko.web.command.impl.admin;

import net.lebedko.service.CategoryService;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class AdminDeleteCategoryCommand extends AbstractAdminCommand {
    private static final IResponseAction CATEGORIES_REDIRECT = new RedirectAction(URL.ADMIN_CATEGORIES);
    private CategoryService categoryService;

    public AdminDeleteCategoryCommand(OrderService orderService, InvoiceService invoiceService, CategoryService categoryService) {
        super(orderService, invoiceService);
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        categoryService.delete(CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L));
        return CATEGORIES_REDIRECT;
    }
}
