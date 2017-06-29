package net.lebedko.web.command.impl;

import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.Views;

/**
 * alexandr.lebedko : 14.06.2017
 */
public class LoginPostCommand implements ICommand {
    @Override
    public IResponseAction execute(IContext context) {
        return new RedirectAction(Views.REGISTRATION);
    }
}
