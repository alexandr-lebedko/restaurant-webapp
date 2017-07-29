package net.lebedko.web.command.impl.admin;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageLocations;

/**
 * alexandr.lebedko : 24.07.2017.
 */
public class AdminMainGetCommand extends AbstractCommand{
    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        return new ForwardAction(PageLocations.MAIN_ADMIN);
    }
}
