package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.Title;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.item.ItemValidator;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class AdminModifyItemCommand extends AbstractAdminCommand {
    private static final IResponseAction ITEMS_FORWARD = new ForwardAction(PAGE.ADMIN_ITEMS);

    private CategoryService categoryService;
    private ItemService itemService;
    private ItemValidator itemValidator;

    public AdminModifyItemCommand(OrderService orderService, ItemService itemService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.itemValidator = new ItemValidator();
    }

    @Override
    protected IResponseAction _doExecute(IContext context) {
        final Long categoryId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID));
        final Item item = parseItem(context, categoryId);
        final Errors errors = new Errors();

        itemValidator.validate(item, errors);
        try {
            if (!errors.hasErrors()) {
                itemService.update(item);

                return new RedirectAction(
                        QueryBuilder.base(URL.ADMIN_ITEMS)
                                .addParam(Attribute.CATEGORY_ID, categoryId.toString())
                                .toString()
                );
            }
        } catch (IllegalArgumentException e) {
            LOG.error(e);
            errors.register("itemExists", PageErrorNames.ITEM_EXISTS);
        }

        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.ITEMS, itemService.getByCategory(item.getCategory()));
        context.addRequestAttribute(Attribute.MODIFIED_ITEM, item);
        context.addErrors(errors);
        return ITEMS_FORWARD;
    }

    private Item parseItem(IContext context, Long categoryId) {
        final Long itemId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID));
        final Title title = CommandUtils.parseTitle(context);
        final Description description = CommandUtils.parseDescription(context);
        final Price price = CommandUtils.parsePrice(context);
        final Category category = categoryService.getById(categoryId);
        final String imageId = context.getRequestParameter(Attribute.IMAGE_ID);

        return new Item(itemId, title, description, category, price, imageId);
    }
}