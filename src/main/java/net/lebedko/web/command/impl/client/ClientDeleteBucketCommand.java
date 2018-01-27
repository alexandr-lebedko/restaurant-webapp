package net.lebedko.web.command.impl.client;

import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientDeleteBucketCommand implements Command {
    private static final ResponseAction ORDER_FORM_REDIRECT = new RedirectAction(URL.CLIENT_ORDER_FORM);
    @Override
    public ResponseAction execute(Context context) {
        context.removeSessionAttribute(Attribute.ORDER_BUCKET);

        return ORDER_FORM_REDIRECT;
    }
}