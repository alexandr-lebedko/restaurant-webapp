package net.lebedko.web.response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * alexandr.lebedko : 12.06.2017
 */
public interface IResponseAction {
    void executeResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
