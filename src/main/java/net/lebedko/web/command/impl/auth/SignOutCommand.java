package net.lebedko.web.command.impl.auth;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.URL;

/**
 * alexandr.lebedko : 09.09.2017.
 */
public class SignOutCommand implements ICommand {
    private static final IResponseAction SIGN_IN_PAGE_REDIRECT = new RedirectAction(URL.SIGN_IN);

    @Override
    public IResponseAction execute(IContext context) {
        context.destroySession();
        return SIGN_IN_PAGE_REDIRECT;
    }
}
