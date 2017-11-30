package net.lebedko.web.command;

import net.lebedko.web.command.impl.CommandFactoryImpl;

public interface ICommandFactory {

    ICommand getCommand(String cmd);

    static ICommandFactory getCommandFactory() {
        return CommandFactoryImpl.getInstance();
    }
}
