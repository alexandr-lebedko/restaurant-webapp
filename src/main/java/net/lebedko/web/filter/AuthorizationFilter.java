package net.lebedko.web.filter;

import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

import static net.lebedko.web.filter.AuthenticationFilter.isAuthenticated;

@WebFilter(urlPatterns = {"/app/admin/*", "/app/client/*"},
        initParams = {
                @WebInitParam(name = "adminUrlRegex", value = ".+\\/admin\\/.+"),
                @WebInitParam(name = "clientUrlRegex", value = ".+\\/client\\/.+")
        }
)
public class AuthorizationFilter extends AbstractFilter {
    private Pattern adminPattern;
    private Pattern clientPattern;

    @Override
    public void init(FilterConfig filterConfig) {
        adminPattern = Pattern.compile(filterConfig.getInitParameter("adminUrlRegex"));
        clientPattern = Pattern.compile(filterConfig.getInitParameter("clientUrlRegex"));
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (notAuthenticated(request)) {
            redirectToSignInPage(request, response);

        } else if (isAdmin(request) && isToClientPages(request)) {
            redirectToAdminMain(request, response);

        } else if (isClient(request) && isToAdminPages(request)) {
            redirectToClientMain(request, response);

        } else {
            chain.doFilter(request, response);
        }
    }

    private void redirectToSignInPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + URL.SIGN_IN);
    }

    private void redirectToAdminMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + URL.ADMIN_MAIN);
    }

    private void redirectToClientMain(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + URL.CLIENT_MENU);
    }

    private boolean isAdmin(HttpServletRequest request) {
        return UserRole.ADMIN == ((User) request.getSession().getAttribute(Attribute.USER)).getRole();
    }

    private boolean isClient(HttpServletRequest request) {
        return UserRole.CLIENT == ((User) request.getSession().getAttribute(Attribute.USER)).getRole();
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