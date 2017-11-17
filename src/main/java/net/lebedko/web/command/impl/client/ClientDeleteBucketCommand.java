package net.lebedko.web.command.impl.client;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientDeleteBucketCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_REDIRECT = new RedirectAction(URL.CLIENT_ORDER_FORM);
    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        context.removeSessionAttribute(Attribute.ORDER_BUCKET);
        context.removeSessionAttribute(Attribute.ORDER_BUCKET_AMOUNT);

        return ORDER_FORM_REDIRECT;
    }
}
