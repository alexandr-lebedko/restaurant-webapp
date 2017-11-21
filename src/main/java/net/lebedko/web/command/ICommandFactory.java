package net.lebedko.web.command;

/**
 * alexandr.lebedko : 12.06.2017
 */
public interface ICommandFactory {

    ICommand getCommand(String cmd);

    static ICommandFactory getCommandFactory(){
        return null;
    }
}
