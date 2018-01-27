package net.lebedko.web.response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseAction {
    void executeResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
