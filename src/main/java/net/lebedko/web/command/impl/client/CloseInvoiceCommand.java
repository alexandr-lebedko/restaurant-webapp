package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.NoSuchEntityException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnprocessedOrdersException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;

public class CloseInvoiceCommand extends AbstractCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_INVOICES);
    private static final IResponseAction INVOICES_REDIRECT = new RedirectAction(URL.CLIENT_INVOICES);

    private InvoiceService invoiceService;

    public CloseInvoiceCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        Errors errors = new Errors();

        try {
            invoiceService.closeActiveInvoice(getUserFromSession(context));
        } catch (NoSuchEntityException nse) {

            errors.register("invoiceNotExists", PageErrorNames.INVOICE_NOT_EXISTS);
            LOG.error("User doesn't have active invoice");

        } catch (UnprocessedOrdersException uoe) {

            errors.register("invoiceHasUnprocessedOrders", PageErrorNames.INVOICE_HAS_UNPROCESSED_ORDERS);
            LOG.error("Attempt to close invoice with unprocessed orders");

        }


        if (errors.hasErrors()) {
            context.addErrors(errors);
            context.addRequestAttribute("currentInvoice", invoiceService.getCurrentInvoiceAndContent(getUserFromSession(context)));
            return INVOICES_FORWARD;
        }

        return INVOICES_REDIRECT;
    }


    private User getUserFromSession(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }
}
