package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;
import java.util.NoSuchElementException;

public class AdminGetItemsCommand extends AbstractAdminCommand {
    private static final ResponseAction ITEMS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ITEMS);
    private static final Long DEFAULT_ID = 1L;

    private ItemService itemService;
    private CategoryService categoryService;

    public AdminGetItemsCommand(OrderService orderService, ItemService itemService, CategoryService categoryService) {
        super(orderService);
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Override
    protected ResponseAction doExecute(Context context) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), DEFAULT_ID);
        final Collection<Category> categories = categoryService.getAll();

        final Category category = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        context.addRequestAttribute(Attribute.CATEGORIES, categories);
        context.addRequestAttribute(Attribute.CATEGORY, category);
        context.addRequestAttribute(Attribute.ITEMS, itemService.getByCategory(category));

        return ITEMS_FORWARD;
    }
}
