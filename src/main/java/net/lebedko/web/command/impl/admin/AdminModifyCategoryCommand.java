package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.item.CategoryValidator;

public class AdminModifyCategoryCommand extends AbstractAdminCommand {
    private static final ResponseAction CATEGORIES_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_CATEGORIES);
    private static final ResponseAction CATEGORIES_REDIRECT = new RedirectAction(URL.ADMIN_CATEGORIES);

    private CategoryService categoryService;
    private CategoryValidator validator;

    public AdminModifyCategoryCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
        this.validator = new CategoryValidator();
    }

    @Override
    protected ResponseAction doExecute(Context context) {
        final Category category = CommandUtils.parseCategory(context);
        final Errors errors = new Errors();
        validator.validate(category, errors);
        if (!errors.hasErrors()) {
            try {
                categoryService.update(category);
                return CATEGORIES_REDIRECT;
            } catch (IllegalArgumentException e) {
                errors.register("categoryExists", PageErrorNames.CATEGORY_EXISTS);
            }
        }
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.MODIFIED_CATEGORY, category);
        context.addErrors(errors);
        return CATEGORIES_FORWARD;
    }
}