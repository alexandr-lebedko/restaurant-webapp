package net.lebedko.web.command.impl;

import net.lebedko.service.UserService;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.admin.AdminMainGetCommand;
import net.lebedko.web.validator.IValidator;
import net.lebedko.web.validator.UserValidator;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.service.ServiceFactory.*;
import static net.lebedko.web.util.constant.Commands.*;

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
    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
