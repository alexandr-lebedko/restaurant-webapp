package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;
import java.util.NoSuchElementException;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetMenuCommand implements Command {
private static final ResponseAction MENU_FORWARD = new ForwardAction(PAGE.CLIENT_MENU);
    private static final Long DEFAULT_CATEGORY = 1L;

    private CategoryService categoryService;
    private ItemService itemService;


    public ClientGetMenuCommand(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), DEFAULT_CATEGORY);

        final Collection<Category> categories = categoryService.getAll();

        final Category requestedCategory = categories.stream()
                .filter(category -> id.equals(category.getId()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        final Collection<Item> items = itemService.getByCategory(requestedCategory);

        context.addRequestAttribute(Attribute.CATEGORIES, categories);
        context.addRequestAttribute(Attribute.ITEMS, items);

        return MENU_FORWARD;
    }

}
