package net.lebedko.web.command.impl.admin;

import net.lebedko.service.CategoryService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class AdminDeleteCategoryCommand extends AbstractAdminCommand {
    private static final ResponseAction CATEGORIES_REDIRECT = new RedirectAction(URL.ADMIN_CATEGORIES);
    private CategoryService categoryService;

    public AdminDeleteCategoryCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
    }

    @Override
    protected ResponseAction doExecute(Context context){
        categoryService.delete(CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID)));
        return CATEGORIES_REDIRECT;
    }
}