package net.lebedko.web.filter;

import net.lebedko.entity.user.UserRole;
import net.lebedko.web.util.constant.WebConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

import static net.lebedko.web.filter.AuthenticationFilter.isAuthenticated;

/**
 * alexandr.lebedko : 08.09.2017.
 */

@WebFilter(urlPatterns= {"/app/admin/*","/app/client/*"},
        initParams = {
                @WebInitParam(name = "adminUrlRegex", value = ".+\\/admin\\/.+"),
                @WebInitParam(name = "clientUrlRegex", value = ".+\\/client\\/.+")
        })
public class AuthorizationFilter extends AbstractFilter {
    private static final Logger LOG = LogManager.getLogger();

    private Pattern adminPattern;
    private Pattern clientPattern;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminPattern = Pattern.compile(filterConfig.getInitParameter("adminUrlRegex"));
        clientPattern = Pattern.compile(filterConfig.getInitParameter("clientUrlRegex"));
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (notAuthenticated(request)) {
            redirectToSignInPage(request, response);
        }
        else if (isAdmin(request)) {
            if (isToClientPages(request)) {
                redirectToAdminMain(request, response);
            }
        }
        else if (isClient(request)) {
            if (isToAdminPages(request)) {
                redirectToClientMain(request, response);
            }
        }else {
            chain.doFilter(request, response);
        }
    }

    private void redirectToSignInPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + WebConstant.URL.SIGN_IN);
    }

    private void redirectToAdminMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + WebConstant.URL.ADMIN_MAIN);

    }

    private void redirectToClientMain(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + WebConstant.URL.CLIENT_MAIN);
    }

    private boolean isAdmin(HttpServletRequest request) {
        return UserRole.ADMIN == (UserRole) request.getSession().getAttribute("role");
    }

    private boolean isClient(HttpServletRequest request) {
        return UserRole.CLIENT == (UserRole) request.getSession().getAttribute("role");
    }

    private boolean isToAdminPages(HttpServletRequest request) {
        return adminPattern.matcher(request.getRequestURI()).matches();
    }

    private boolean isToClientPages(HttpServletRequest request) {
        return clientPattern.matcher(request.getRequestURI()).matches();
    }

    private boolean notAuthenticated(HttpServletRequest request) {
        return !isAuthenticated(request);
    }
}
