package net.lebedko.web.command.impl.client;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetOrderFormCommand implements ICommand{
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);

    @Override
    public IResponseAction execute(IContext context) {
        return ORDER_FORM_FORWARD;
    }
}
