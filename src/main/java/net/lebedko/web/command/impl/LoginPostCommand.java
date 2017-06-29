package net.lebedko.web.command.impl;

import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Password;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.Pages;
import net.lebedko.web.validator.Errors;

import static java.util.Objects.isNull;
import static java.util.Optional.*;
import static net.lebedko.service.UserService.authenticate;
import static net.lebedko.web.util.constant.PageErrorNames.USER_NOT_EXISTS;
import static net.lebedko.web.util.constant.PageErrorNames.WRONG_PASSWORD;

/**
 * alexandr.lebedko : 14.06.2017
 */
public class LoginPostCommand extends AbstractCommand {
    private static final IResponseAction LOGIN_PAGE_FORWARD = new ForwardAction(Pages.LOGIN);
    private static final IResponseAction MAIN_ADMIN_PAGE_REDIRECT = new RedirectAction(Pages.MAIN_ADMIN);
    private static final IResponseAction MAIN_CLIENT_PAGE_REDIRECT = new RedirectAction(Pages.MAIN_CLIENT);

    private UserService userService;

    public LoginPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {

        final UserView userView = getUserView(context);
        final User user = userService.findByEmail(userView.getEmailAddress());

        Errors errors = new Errors();

        if (isNull(user)) {
            errors.register("user not exists", USER_NOT_EXISTS);
            context.addErrors(errors);
            logger.warn("Entered data for not registered account: " + userView.getEmailAddress());
            return LOGIN_PAGE_FORWARD;
        }

        if (authenticate(userView, user)) {
            context.addSessionAttribute("user", user);
            return mainPageRedirectAction(user);
        } else {
            errors.register("wrong password", WRONG_PASSWORD);
            logger.warn("Entered wrong password for account: " + userView.getEmailAddress());
            return LOGIN_PAGE_FORWARD;
        }
    }

    private IResponseAction mainPageRedirectAction(User user) {
        if (user.getRole() == User.UserRole.CLIENT)
            return MAIN_CLIENT_PAGE_REDIRECT;
        else
            return MAIN_ADMIN_PAGE_REDIRECT;
    }

    private UserView getUserView(IContext context) {
        EmailAddress email = new EmailAddress(ofNullable(context.getRequestAttribute("email"))
                .orElse(""));
        Password password = Password.createPasswordFromString(ofNullable(context.getRequestAttribute("password"))
                .orElse(""));

        return new UserView(email, password);
    }

}
