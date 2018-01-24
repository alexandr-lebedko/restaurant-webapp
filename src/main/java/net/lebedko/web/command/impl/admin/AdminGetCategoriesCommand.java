package net.lebedko.web.command.impl.admin;

import net.lebedko.service.CategoryService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;


public class AdminGetCategoriesCommand extends AbstractAdminCommand {
    private IResponseAction CATEGORIES_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_CATEGORIES);
    private CategoryService categoryService;

    public AdminGetCategoriesCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
    }

    @Override
    protected IResponseAction doExecute(IContext context){
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        return CATEGORIES_FORWARD;
    }
}
