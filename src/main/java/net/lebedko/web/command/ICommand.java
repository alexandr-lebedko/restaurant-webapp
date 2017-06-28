package net.lebedko.web.command;

import net.lebedko.web.response.IResponseAction;

/**
 * alexandr.lebedko : 12.06.2017
 */
public interface ICommand {

    IResponseAction execute(IContext context);

}
