package net.lebedko.web.command.impl.client;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetOrderFormCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        return ORDER_FORM_FORWARD;
    }
}
