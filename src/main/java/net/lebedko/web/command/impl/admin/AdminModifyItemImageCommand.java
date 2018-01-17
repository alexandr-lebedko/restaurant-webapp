package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Item;
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
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;

import java.io.InputStream;
import java.util.Objects;

public class AdminModifyItemImageCommand extends AbstractAdminCommand {
    private static final IResponseAction ITEMS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ITEMS);
    private ItemService itemService;
    private CategoryService categoryService;
    private ImageValidator imageValidator;

    public AdminModifyItemImageCommand(OrderService orderService, ItemService itemService, CategoryService categoryService) {
        super(orderService);
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.imageValidator = new ImageValidator();
    }

    @Override
    protected IResponseAction _doExecute(IContext context) {
        final Errors errors = new Errors();

        imageValidator.validate(context.getInputStream(Attribute.IMAGE_ID), errors);

        final Long itemId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID));
        final Item item = Objects.requireNonNull(itemService.get(itemId));
        final InputStream imageStream = context.getInputStream(Attribute.IMAGE_ID);

        if (!errors.hasErrors()) {
            itemService.update(item, imageStream);

            return new RedirectAction(
                    QueryBuilder.base(URL.ADMIN_ITEMS)
                            .addParam(Attribute.CATEGORY_ID, item.getCategory().getId().toString())
                            .toString()
            );
        }

        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.ITEMS, itemService.getByCategory(item.getCategory()));
        context.addRequestAttribute(Attribute.IMAGE_ERRORS, errors);
        return ITEMS_FORWARD;
    }

}
