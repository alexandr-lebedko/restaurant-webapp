package net.lebedko.web.command.impl.admin;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class NewDishGetCommand implements ICommand {
//    private static final IResponseAction NEW_DISH_PAGE_FORWARD = new ForwardAction(PageLocations.MENU_NEW_DISH);

    @Override
    public IResponseAction execute(IContext context) {
        return null;
//        return NEW_DISH_PAGE_FORWARD;
    }
}
