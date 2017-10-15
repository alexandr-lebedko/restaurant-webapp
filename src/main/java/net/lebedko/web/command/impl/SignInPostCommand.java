package net.lebedko.web.command.impl;

import net.lebedko.entity.user.*;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.PageLocations;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;

import static java.util.Objects.isNull;
import static net.lebedko.service.UserService.authenticate;
import static net.lebedko.web.util.constant.PageErrorNames.USER_NOT_EXISTS;
import static net.lebedko.web.util.constant.PageErrorNames.WRONG_PASSWORD;

/**
 * alexandr.lebedko : 14.06.2017
 */
public class SignInPostCommand extends AbstractCommand {
    private static final IResponseAction SIGN_IN_PAGE_FORWARD = new ForwardAction(PageLocations.SIGN_IN);
    private static final IResponseAction MAIN_ADMIN_PAGE_REDIRECT = new RedirectAction(WebConstant.URL.ADMIN_MAIN);
    private static final IResponseAction MAIN_CLIENT_PAGE_REDIRECT = new RedirectAction(WebConstant.URL.CLIENT_CATEGORIES);

    private UserService userService;

    public SignInPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected IResponseAction doExecute(final IContext context) throws ServiceException {

        final UserView userView = getUserView(context);
        final Errors errors = new Errors();

        final User user = userService.findByEmail(userView.getEmailAddress());

        if (isNull(user)) {
            errors.register("user not exists", USER_NOT_EXISTS);
            context.addErrors(errors);
            context.addRequestAttribute("user",userView);
            LOG.warn("Entered data for not registered account: " + userView.getEmailAddress());

            return SIGN_IN_PAGE_FORWARD;
        }

        if (authenticate(userView, user)) {
            LOG.info(user + " authenticated");
            addUserInfoToSession(context, user);
            return mainPageRedirectAction(user);
        }

        errors.register("wrong password", WRONG_PASSWORD);
        context.addRequestAttribute("user", userView);
        context.addErrors(errors);
        LOG.warn("Entered wrong password for account: " + userView.getEmailAddress());

        return SIGN_IN_PAGE_FORWARD;
    }

    private IResponseAction mainPageRedirectAction(User user) {
        if (user.getRole() == UserRole.CLIENT)
            return MAIN_CLIENT_PAGE_REDIRECT;
        else
            return MAIN_ADMIN_PAGE_REDIRECT;
    }

    private void addUserInfoToSession(IContext context, User user) {
        context.addSessionAttribute("user", user);
        context.addSessionAttribute("role", user.getRole());
    }

    private UserView getUserView(IContext context) {
        String emailString = context.getRequestParameter("email");
        String passwordString = context.getRequestParameter("password");

        EmailAddress emailAddress = isNull(emailString) ? null : new EmailAddress(emailString);
        Password password = isNull(passwordString) ? null : Password.createPasswordFromString(passwordString);

        return new UserView(emailAddress, password);
    }
}
