package net.lebedko.web.command;

import net.lebedko.web.response.ResponseAction;

public interface Command {
    ResponseAction execute(Context context);
}
