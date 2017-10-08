package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.NoSuchEntityException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnprocessedOrdersException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;

public class CloseInvoiceCommand extends AbstractCommand {
    private InvoiceService invoiceService;

    public CloseInvoiceCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        try {
            invoiceService.closeActiveInvoice(getUserFromSession(context));
        } catch (NoSuchEntityException nse) {

        } catch (UnprocessedOrdersException uoe) {

        }

        return null;
    }

    private User getUserFromSession(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }
}
