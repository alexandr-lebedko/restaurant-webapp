package net.lebedko.web.command.impl;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.validator.Errors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractCommand implements ICommand {
    protected static final Logger LOG = LogManager.getLogger();

    @Override
    public final IResponseAction execute(IContext context) {
        LOG.info("Starting to executing command");
        IResponseAction responseAction = doExecute(context);
        LOG.info("Command executing finished");
        return responseAction;
    }

    protected abstract IResponseAction doExecute(IContext context) throws ServiceException;
}
