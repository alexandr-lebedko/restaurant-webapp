package net.lebedko.web.command.impl.auth;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class SignInGetCommand implements ICommand {
    private static final IResponseAction SIGN_IN_FORWARD = new ForwardAction(PAGE.SIGN_IN);

    @Override
    public IResponseAction execute(IContext context) {
        return SIGN_IN_FORWARD;
    }
}
