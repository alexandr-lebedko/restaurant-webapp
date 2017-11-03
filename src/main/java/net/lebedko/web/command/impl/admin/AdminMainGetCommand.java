package net.lebedko.web.command.impl.admin;

import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;

import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 24.07.2017.
 */
public class AdminMainGetCommand extends AbstractAdminCommand{
    private static final IResponseAction MAIN_PAGE_FORWARD = new ForwardAction(PAGE.ADMIN_MAIN);


    public AdminMainGetCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        return MAIN_PAGE_FORWARD;
    }
}
