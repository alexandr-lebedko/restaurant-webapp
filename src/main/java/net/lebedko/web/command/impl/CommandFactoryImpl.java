package net.lebedko.web.command.impl;

import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;

import java.util.HashMap;
import java.util.Map;

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
        commandMap.put(POST_LOGIN, new DumbLoginPostCommand());
    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
