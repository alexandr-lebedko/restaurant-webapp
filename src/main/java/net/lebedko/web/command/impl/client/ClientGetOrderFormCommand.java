package net.lebedko.web.command.impl.client;

import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetOrderFormCommand implements Command {
    private static final ResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);

    @Override
    public ResponseAction execute(Context context) {
        return ORDER_FORM_FORWARD;
    }
}