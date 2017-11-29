package net.lebedko.web.filter;

import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.web.util.constant.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * alexandr.lebedko : 08.09.2017.
 */
@WebFilter(urlPatterns = {
        "/app/signIn/", "/app/signIn",
        "/app/signUp/", "/app/signUp"
})
public class AuthenticationFilter extends AbstractFilter {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (isAuthenticated(request)) {
            LOG.debug("USER AUTHENTICATED, REDIRECTING TO MAIN PAGE");
            redirectToMainPage(request, response);
        } else {
            LOG.debug("USER not AUTHENTICATED, ALLOWING TO AUTHENTICATION");
            chain.doFilter(request, response);
        }
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (isNull(session))
            return false;

        return nonNull(request.getSession().getAttribute("user"));
    }

    private void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserRole role = ((User) (request.getSession().getAttribute("user"))).getRole();

        if (role == UserRole.ADMIN) {
            LOG.debug("REDIRECTING TO ADMIN MAIN PAGE");
            response.sendRedirect(request.getContextPath() + URL.ADMIN_MAIN);
        } else {
            LOG.debug("REDIRECTING TO CLIENT MAIN PAGE");
            response.sendRedirect(request.getContextPath() + URL.CLIENT_MENU);
        }
    }
}
