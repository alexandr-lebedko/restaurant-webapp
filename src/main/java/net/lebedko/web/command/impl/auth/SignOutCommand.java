package net.lebedko.web.command.impl.auth;

import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.URL;

public class SignOutCommand implements Command {
    private static final ResponseAction SIGN_IN_PAGE_REDIRECT = new RedirectAction(URL.SIGN_IN);

    @Override
    public ResponseAction execute(Context context) {
        context.destroySession();
        return SIGN_IN_PAGE_REDIRECT;
    }
}
