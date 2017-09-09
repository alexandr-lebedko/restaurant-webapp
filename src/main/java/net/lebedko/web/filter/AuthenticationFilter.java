package net.lebedko.web.filter;

import net.lebedko.entity.user.User.UserRole;
import net.lebedko.web.util.constant.WebConstant;
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
import static net.lebedko.entity.user.User.UserRole.*;

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

        return isRoleExists(request);
    }

    private static boolean isRoleExists(HttpServletRequest request) {
        return nonNull(request.getSession().getAttribute("role"));
    }

    private void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserRole role = (UserRole) (request.getSession().getAttribute("role"));

        if (role == UserRole.ADMIN) {
            LOG.debug("REDIRECTING TO ADMIN MAIN PAGE");
            response.sendRedirect(request.getContextPath() + WebConstant.URL.ADMIN_MAIN);
        } else {
            LOG.debug("REDIRECTING TO CLIENT MAIN PAGE");
            response.sendRedirect(request.getContextPath() + WebConstant.URL.CLIENT_MAIN);
        }
    }
}
