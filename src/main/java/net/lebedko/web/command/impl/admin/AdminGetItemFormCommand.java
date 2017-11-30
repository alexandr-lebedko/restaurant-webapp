package net.lebedko.web.command.impl.admin;

import net.lebedko.service.CategoryService;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class AdminGetItemFormCommand extends AbstractAdminCommand {
    private static final IResponseAction ITEM_FORM_FORWARD = new ForwardAction(PAGE.ADMIN_ITEM_FORM);
    private CategoryService categoryService;

    public AdminGetItemFormCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        return ITEM_FORM_FORWARD;
    }
}
