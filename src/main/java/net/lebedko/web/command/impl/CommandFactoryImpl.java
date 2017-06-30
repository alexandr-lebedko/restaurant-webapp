package net.lebedko.web.command.impl;

import net.lebedko.service.ServiceFactory;
import net.lebedko.service.UserService;
import net.lebedko.service.impl.UserServiceImpl;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.service.ServiceFactory.*;
import static net.lebedko.web.util.constant.Commands.GET_LOGIN;
import static net.lebedko.web.util.constant.Commands.GET_REGISTRATION;
import static net.lebedko.web.util.constant.Commands.POST_LOGIN;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class CommandFactoryImpl implements ICommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactoryImpl() {
        commandMap.put(GET_LOGIN, new LoginGetCommand());
        commandMap.put(GET_REGISTRATION, new RegistrationGetCommand());
        commandMap.put(POST_LOGIN, new LoginPostCommand(getService(UserService.class)));
    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
