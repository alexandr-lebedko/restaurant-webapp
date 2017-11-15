package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.State;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;

import java.util.Collection;

import static java.util.Optional.ofNullable;

public abstract class AbstractAdminCommand extends AbstractCommand {
    protected OrderService orderService;
    protected InvoiceService invoiceService;

    public AbstractAdminCommand(OrderService orderService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @Override
    protected final IResponseAction doExecute(IContext context) throws ServiceException {
        IResponseAction responseAction = _doExecute(context);

        addOrderAndInvoiceStatistics(context);

        return responseAction;
    }

    private void addOrderAndInvoiceStatistics(IContext context) {

        Collection<Invoice> unprocessedInvoices = invoiceService.getClosedInvoices();
        if (!unprocessedInvoices.isEmpty()){
            context.addRequestAttribute("unprocessedInvoiceNum", unprocessedInvoices.size());
        }

        Collection<Order> unprocessedOrders = orderService.getOrders(State.NEW);
        if (!unprocessedOrders.isEmpty()) {
            context.addRequestAttribute("unprocessedOrderNum", unprocessedOrders.size());
        }
    }


    protected abstract IResponseAction _doExecute(IContext context) throws ServiceException;
}
