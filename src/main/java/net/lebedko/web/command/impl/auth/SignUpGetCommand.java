package net.lebedko.web.command.impl.auth;

import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.util.constant.WebConstant;

public class SignUpGetCommand implements Command {
    @Override
    public ResponseAction execute(Context context) {
        return new ForwardAction(WebConstant.PAGE.SIGN_UP);
    }
}
