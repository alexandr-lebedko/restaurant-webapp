package net.lebedko.web.command.impl.client;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant;

/**
 * alexandr.lebedko : 09.09.2017.
 */
public class MainPageGetCommand extends AbstractCommand {
   private static final IResponseAction CLIENT_MAIN_PAGE_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_MAIN);
    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        return CLIENT_MAIN_PAGE_FORWARD;
    }
}
