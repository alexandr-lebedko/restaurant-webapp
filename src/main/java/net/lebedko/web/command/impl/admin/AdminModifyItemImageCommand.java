package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.item.Item;
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
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.ImageValidator;

import java.io.InputStream;
import java.util.Objects;

public class AdminModifyItemImageCommand extends AbstractAdminCommand {
    private static final String URL_TEMPLATE = URL.ADMIN_ITEMS.concat("?").concat(Attribute.ITEM_ID).concat("=");
    private static final IResponseAction ITEMS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ITEMS);
    private ItemService itemService;
    private CategoryService categoryService;
    private ImageValidator imageValidator;

    public AdminModifyItemImageCommand(OrderService orderService, InvoiceService invoiceService, ItemService itemService, CategoryService categoryService) {
        this(orderService, invoiceService, itemService, categoryService, new ImageValidator());
    }

    public AdminModifyItemImageCommand(OrderService orderService, InvoiceService invoiceService, ItemService itemService, CategoryService categoryService, ImageValidator imageValidator) {
        super(orderService, invoiceService);
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.imageValidator = imageValidator;

    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();

        imageValidator.validate(context.getInputStream(Attribute.IMAGE_ID), errors);

        final Long itemId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID), -1L);
        final Item item = Objects.requireNonNull(itemService.get(itemId));
        final InputStream imageStream = context.getInputStream(Attribute.IMAGE_ID);

        if (!errors.hasErrors()) {
            itemService.update(item, imageStream);
            return new RedirectAction(URL_TEMPLATE.concat(item.getCategory().toString()));
        }
        context.addRequestAttribute(Attribute.CATEGORIES, categoryService.getAll());
        context.addRequestAttribute(Attribute.ITEMS, itemService.getByCategory(item.getCategory()));
        context.addRequestAttribute(Attribute.IMAGE_ERRORS, errors);

        return ITEMS_FORWARD;
    }

}
