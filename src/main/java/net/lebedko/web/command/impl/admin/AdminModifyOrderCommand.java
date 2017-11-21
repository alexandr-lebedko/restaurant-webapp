package net.lebedko.web.command.impl.admin;

import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.lebedko.web.util.CommandUtils.parseToLong;
import static net.lebedko.web.util.CommandUtils.parseToLongList;


public class AdminModifyOrderCommand extends AbstractAdminCommand {
    public AdminModifyOrderCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
      //TODO : REFACTOR

        orderService.modify(getOrderId(context), getItemIdAndQuantityByOrderItemIds(context));

        return new RedirectAction(URL.ADMIN_ORDER_DETAILS
                .concat("?").concat(Attribute.ORDER_ID).concat("=").concat(getOrderId(context).toString()));
    }

    private Long getOrderId(IContext context) {
        return parseToLong(context.getRequestParameter(Attribute.ORDER_ID), -1L);
    }

    private Map<Long, Pair<Long, Long>> getItemIdAndQuantityByOrderItemIds(IContext context) {
        List<Long> orderItemIds = parseToLongList(context.getRequestParameters(Attribute.ORDER_ITEM_ID), -1L);
        List<Long> itemIds = parseToLongList(context.getRequestParameters(Attribute.ITEM_ID), -1L);
        List<Long> itemQuantities = parseToLongList(context.getRequestParameters(Attribute.ORDER_ITEM_QUANTITY), -1L);

        return IntStream.range(0, orderItemIds.size())
                .mapToObj(i -> Triple.of(orderItemIds.get(i), itemIds.get(i), itemQuantities.get(i)))
                .collect(Collectors.toMap(Triple::getLeft, t -> Pair.of(t.getMiddle(), t.getRight())));
    }
}
