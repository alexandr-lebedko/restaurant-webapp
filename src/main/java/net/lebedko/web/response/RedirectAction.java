package net.lebedko.web.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectAction implements ResponseAction {
    private static final Logger LOG = LogManager.getLogger();
    private String page;

    public RedirectAction(String page) {
        this.page = page;
    }

    @Override
    public void executeResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("Redirecting request to: " + page);
        response.sendRedirect(request.getContextPath() +page);
    }
}