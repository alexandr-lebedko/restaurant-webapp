package net.lebedko.web.command.impl.auth;

import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class SignInGetCommand implements Command {
    private static final ResponseAction SIGN_IN_FORWARD = new ForwardAction(PAGE.SIGN_IN);

    @Override
    public ResponseAction execute(Context context) {
        return SIGN_IN_FORWARD;
    }
}
