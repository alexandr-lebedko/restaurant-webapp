package net.lebedko.web.command.impl;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageLocations;
import net.lebedko.web.validator.Errors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;

/**
 * alexandr.lebedko : 28.06.2017
 */
public abstract class AbstractCommand implements ICommand {
    protected static final Logger LOG = LogManager.getLogger();


    @Override
    public final IResponseAction execute(IContext context) {
        Errors errors = new Errors();
        try {
            LOG.info("Starting to executing command");
            IResponseAction responseAction = doExecute(context);
            LOG.info("Command executing finished");
            return responseAction;

        } catch (ServiceException e) {
            LOG.error("Exception executing command", e);
            errors.register("Service failure", e.getCause().getMessage());
        }
        context.addRequestAttribute("errors", errors);
        return new ForwardAction(PageLocations.ERROR_500);
    }


    protected abstract IResponseAction doExecute(IContext context) throws ServiceException;

    protected InputStream getInputStream(IContext context, String name) throws ServiceException{
        try {
            return context.getPart(name).getInputStream();
        } catch (IOException e) {
            //TODO: localize message
            throw new ServiceException(e);
        } catch (ServletException e) {
            //TODO: localize message
            throw new ServiceException(e);
        }
    }
}
