package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.Title;
import net.lebedko.service.CategoryService;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.item.ItemValidator;

import java.io.InputStream;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminCreateItemCommand extends AbstractAdminCommand {
    private static final String URL_TEMPLATE = URL.ADMIN_ITEMS.concat("?").concat(Attribute.CATEGORY_ID).concat("=");
    private static final IResponseAction ITEM_FORWARD = new ForwardAction(PAGE.ADMIN_ITEM_FORM);

    private CategoryService categoryService;
    private ItemService itemService;
    private ItemValidator itemValidator;
    private ImageValidator imageValidator;

    public AdminCreateItemCommand(OrderService orderService, ItemService itemService, CategoryService categoryService) {
        this(orderService,itemService, categoryService, new ItemValidator(), new ImageValidator());
    }

    public AdminCreateItemCommand(OrderService orderService,
                                  ItemService itemService,
                                  CategoryService categoryService,
                                  ItemValidator itemValidator,
                                  ImageValidator imageValidator) {
        super(orderService);
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.itemValidator = itemValidator;
        this.imageValidator = imageValidator;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();
        final Item item = parseItem(context);
        final InputStream imageInput = context.getInputStream(Attribute.IMAGE);

        itemValidator.validate(item, errors);
        imageValidator.validate(context.getInputStream(Attribute.IMAGE), errors);

        if (!errors.hasErrors()) {
            try {
                itemService.insert(item, imageInput);
                return new RedirectAction(URL_TEMPLATE.concat(item.getCategory().getId().toString()));
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

    private Item parseItem(IContext context) {
        Title title = CommandUtils.parseTitle(context);
        Description description = CommandUtils.parseDescription(context);
        Category category = categoryService.getById(CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L));
        Price price = CommandUtils.parsePrice(context);

        return new Item(title, description, category, price, null);
    }

}
