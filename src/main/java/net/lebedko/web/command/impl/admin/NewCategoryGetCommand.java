package net.lebedko.web.command.impl.admin;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;

import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class NewCategoryGetCommand implements ICommand {
    private static final IResponseAction CATEGORY_FORM_FORWARD = new ForwardAction(PAGE.NEW_CATEGORY);

    @Override
    public IResponseAction execute(IContext context) {
        return CATEGORY_FORM_FORWARD;
    }
}
