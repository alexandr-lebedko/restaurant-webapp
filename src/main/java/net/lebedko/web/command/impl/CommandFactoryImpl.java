package net.lebedko.web.command.impl;

import net.lebedko.service.UserService;
import net.lebedko.service.demo.CategoryService;
import net.lebedko.service.demo.FileService;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.admin.*;
import net.lebedko.web.validator.item.CategoryValidator;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.user.UserValidator;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.service.ServiceFactory.*;
import static net.lebedko.web.util.constant.Commands.*;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.GET_ADMIN_NEW_ITEM;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class CommandFactoryImpl implements ICommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactoryImpl() {
        commandMap.put(POST_SIGN_IN, new SignInPostCommand(getService(UserService.class)));
        commandMap.put(GET_SIGN_IN, new SignInGetCommand());

        commandMap.put(GET_SIGN_UP, new SignUpGetCommand());
        commandMap.put(POST_SIGN_UP, new SignUpPostCommand(getService(UserService.class), new UserValidator()));

        commandMap.put(GET_ADMIN_MAIN, new AdminMainGetCommand());
        commandMap.put(GET_NEW_DISH, new NewDishGetCommand());

        commandMap.put(GET_NEW_CATEGORY, new NewCategoryGetCommand());

        commandMap.put(POST_NEW_CATEGORY, new NewCategoryPostCommand(
                getService(CategoryService.class),
                new CategoryValidator(),
                getService(FileService.class),
                new ImageValidator()));

        commandMap.put(GET_ADMIN_MENU, new AdminMenuGetCommand(getService(CategoryService.class)));

        commandMap.put(GET_ADMIN_NEW_ITEM, new NewItemGetCommand(getService(CategoryService.class)));

    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
