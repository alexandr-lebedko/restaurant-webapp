package net.lebedko.web.command.impl.auth;

import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.WebConstant;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class SignUpGetCommand implements ICommand {
    @Override
    public IResponseAction execute(IContext context) {
        return new ForwardAction(WebConstant.PAGE.SIGN_UP);
    }
}
