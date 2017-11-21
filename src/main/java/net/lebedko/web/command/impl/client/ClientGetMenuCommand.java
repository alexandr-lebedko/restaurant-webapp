package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;

public class ClientGetMenuCommand extends AbstractCommand {
    private static final Long DEFAULT_CATEGORY = 1L;

    private CategoryService categoryService;
    private ItemService itemService;


    public ClientGetMenuCommand(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
<<<<<<< HEAD
        final Long categoryId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), DEFAULT_CATEGORY);
        final Collection<Category> categories = categoryService.getAll();

        System.out.println("Categories: " + categories);
=======
        Long categoryId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), DEFAULT_CATEGORY);
>>>>>>> master

        Category requestedCategory = categories.stream()
                .filter(category -> categoryId.equals(category.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        System.out.println("REQUESTED CATEGORY : " + requestedCategory);

        Collection<Item> items = itemService.getByCategory(requestedCategory);

        context.addRequestAttribute(Attribute.CATEGORIES, categories);
        context.addRequestAttribute(Attribute.ITEMS, items);

        return new ForwardAction(WebConstant.PAGE.CLIENT_MENU);
    }

}
