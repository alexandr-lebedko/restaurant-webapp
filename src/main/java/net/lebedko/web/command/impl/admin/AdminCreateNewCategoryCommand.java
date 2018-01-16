package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.item.CategoryValidator;


public class AdminCreateNewCategoryCommand extends AbstractAdminCommand {
    private static final IResponseAction CATEGORIES_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_CATEGORIES);
    private static final IResponseAction CATEGORIES_REDIRECT = new RedirectAction(URL.ADMIN_CATEGORIES);

    private CategoryService categoryService;
    private CategoryValidator categoryValidator;

    public AdminCreateNewCategoryCommand(OrderService orderService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
        this.categoryValidator = new CategoryValidator();
    }

    @Override
    protected IResponseAction _doExecute(IContext context) {
        final Category category = CommandUtils.parseCategory(context);
        final Errors errors = new Errors();

        categoryValidator.validate(category, errors);

        if (!errors.hasErrors()) {
            try {
                categoryService.insert(category);
                return CATEGORIES_REDIRECT;
            } catch (IllegalArgumentException e) {
                errors.register("categoryExists", PageErrorNames.CATEGORY_EXISTS);
            }
        }

        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.NEW_CATEGORY, category);
        context.addErrors(errors);

        return CATEGORIES_FORWARD;
    }

}
