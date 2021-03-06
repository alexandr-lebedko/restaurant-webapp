package net.lebedko.web.controller;

import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.CommandFactory;
import net.lebedko.web.command.impl.WebContext;
import net.lebedko.web.util.constant.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(URL.CONTROLLER_PATTERN)
@MultipartConfig
public class FrontController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger();
    private static final CommandFactory commandFactory = CommandFactory.getCommandFactory();

    @Override
    protected void service(final HttpServletRequest req,
                           final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final String cmd = parseCommandName(req);
            LOG.info("REQUESTED CMD: " + cmd);

            final Command command = commandFactory.getCommand(cmd);
            final ResponseAction responseAction = command.execute(new WebContext(req, resp));

            responseAction.executeResponse(req, resp);
            LOG.info("CMD: " + cmd + " EXECUTED");
        } catch (NoSuchElementException e) {
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }catch (Exception e){
            LOG.error(e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String parseCommandName(HttpServletRequest request) {
        String cmd = request.getMethod() + ":" + request.getRequestURI().substring(request.getContextPath().length());

        final int lastIndex = cmd.length() - 1;

        if (cmd.charAt(lastIndex) == '/') {
            cmd = cmd.substring(0, lastIndex);
        }
        return cmd;
    }
}