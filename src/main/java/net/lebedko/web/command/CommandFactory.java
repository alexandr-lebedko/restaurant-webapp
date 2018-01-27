package net.lebedko.web.command;

import net.lebedko.web.command.impl.CommandFactoryImpl;

public interface CommandFactory {

    Command getCommand(String cmd);

    static CommandFactory getCommandFactory() {
        return CommandFactoryImpl.getInstance();
    }
}
