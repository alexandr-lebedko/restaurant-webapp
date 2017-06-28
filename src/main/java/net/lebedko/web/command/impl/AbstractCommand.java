package net.lebedko.web.command.impl;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Pages;
import net.lebedko.web.validator.Errors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * alexandr.lebedko : 28.06.2017
 */
public abstract class AbstractCommand implements ICommand {
    protected static final Logger logger = LogManager.getLogger();


    @Override
    public final IResponseAction execute(IContext context) {
        Errors errors = new Errors();
        try {
            return doExecute(context);
        } catch (ServiceException e) {
            logger.error("Exception executing command", e);
            errors.register("Service failure", e.getCause().getMessage());
        }
        context.addRequestAttribute("errors", errors);
        return new ForwardAction(Pages.ERROR_500);
    }


    protected abstract IResponseAction doExecute(IContext context) throws ServiceException;
}
