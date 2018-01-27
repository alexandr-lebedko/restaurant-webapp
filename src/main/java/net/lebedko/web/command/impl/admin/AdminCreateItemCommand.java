package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Item;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.item.ItemValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;


public class AdminCreateItemCommand extends AbstractAdminCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final ResponseAction ITEM_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ITEM_FORM);

    private CategoryService categoryService;
    private ItemService itemService;
    private ItemValidator itemValidator;
    private ImageValidator imageValidator;

    public AdminCreateItemCommand(OrderService orderService, ItemService itemService, CategoryService categoryService) {
        super(orderService);
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.itemValidator = new ItemValidator();
        this.imageValidator = new ImageValidator();
    }

    @Override
    protected ResponseAction doExecute(Context context) {
        final Errors errors = new Errors();
        final Item item = parseItem(context);
        final InputStream imageInput = context.getInputStream(Attribute.IMAGE);

        itemValidator.validate(item, errors);
        imageValidator.validate(context.getInputStream(Attribute.IMAGE), errors);

        if (!errors.hasErrors()) {
            try {
                itemService.insert(item, imageInput);
                return new RedirectAction(QueryBuilder.base(URL.ADMIN_ITEMS)
                                .addParam(Attribute.CATEGORY_ID, item.getCategory().getId().toString())
                                .build());
                } catch (IllegalArgumentException e) {
                LOG.error("Item exists", e);
                errors.register("itemExists", PageErrorNames.ITEM_EXISTS);
            }
        }
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.ITEM, item);
        context.addErrors(errors);
        return ITEM_FORWARD;
    }

    private Item parseItem(Context context) {
        return new Item(
                CommandUtils.parseTitle(context),
                CommandUtils.parseDescription(context),
                categoryService.getById(CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L)),
                CommandUtils.parsePrice(context),
                null);
    }
}
