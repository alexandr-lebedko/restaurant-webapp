package net.lebedko.web;

import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.impl.CommandFactoryImpl;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.WebContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.lebedko.web.util.constant.Commands.CONTROLLER;

/**
 * alexandr.lebedko : 10.06.2017
 */
@WebServlet(CONTROLLER + "/*")
public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final ICommandFactory commandFactory = new CommandFactoryImpl();

    @Override
    protected void service(final HttpServletRequest req,
                           final HttpServletResponse resp) throws ServletException, IOException {
        final String cmd = parseRequest(req);

        logger.info("REQUESTED CMD: " + cmd);

        final ICommand command = commandFactory.getCommand(cmd);
        final IResponseAction responseAction = command.execute(new WebContext(req, resp));

        responseAction.executeResponse(req, resp);
    }


    private String parseRequest(HttpServletRequest request) {
        String cmd = request.getMethod() + ":" + request.getRequestURI().substring(request.getContextPath().length());

        final int lastIndex = cmd.length() - 1;

        if (cmd.charAt(lastIndex) == '/') {
            cmd = cmd.substring(0, lastIndex);
        }
        return cmd;
    }
}
