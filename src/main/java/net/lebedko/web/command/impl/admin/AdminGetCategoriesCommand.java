package net.lebedko.web.command.impl.admin;

import net.lebedko.service.CategoryService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;


public class AdminGetCategoriesCommand extends AbstractAdminCommand {
    private ResponseAction CATEGORIES_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_CATEGORIES);
    private CategoryService categoryService;

    public AdminGetCategoriesCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
    }

    @Override
    protected ResponseAction doExecute(Context context){
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        return CATEGORIES_FORWARD;
    }
}
