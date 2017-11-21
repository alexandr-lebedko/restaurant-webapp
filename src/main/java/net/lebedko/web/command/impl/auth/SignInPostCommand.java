package net.lebedko.web.command.impl.auth;

import net.lebedko.entity.user.*;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static net.lebedko.web.util.constant.PageErrorNames.USER_NOT_EXISTS;
import static net.lebedko.web.util.constant.PageErrorNames.WRONG_PASSWORD;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 14.06.2017
 */
public class SignInPostCommand extends AbstractCommand {
    private static final IResponseAction SIGN_IN_PAGE_FORWARD = new ForwardAction(PAGE.SIGN_IN);
    private static final IResponseAction MAIN_ADMIN_PAGE_REDIRECT = new RedirectAction(URL.ADMIN_MAIN);
    private static final IResponseAction MAIN_CLIENT_PAGE_REDIRECT = new RedirectAction(URL.CLIENT_MENU);

    private UserService userService;

    public SignInPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected IResponseAction doExecute(final IContext context) throws ServiceException {
        final Errors errors = new Errors();

        final Password password = ofNullable(context.getRequestParameter(Attribute.PASSWORD))
                .map(Password::createPasswordFromString)
                .orElse(Password.createPasswordFromString(""));
        final EmailAddress emailAddress = ofNullable(context.getRequestParameter(Attribute.EMAIL))
                .map(EmailAddress::new)
                .orElse(new EmailAddress(""));

        final User user = userService.findByEmail(emailAddress);

        if (isNull(user)) {
            errors.register("user not exists", USER_NOT_EXISTS);
            context.addErrors(errors);
            context.addRequestAttribute(Attribute.EMAIL, emailAddress);
            context.addRequestAttribute(Attribute.PASSWORD, password);

            return SIGN_IN_PAGE_FORWARD;
        }

        if (wrongPassword(password, user.getPassword())) {
            errors.register("wrong password", WRONG_PASSWORD);
            context.addRequestAttribute(Attribute.EMAIL, emailAddress);
            context.addRequestAttribute(Attribute.PASSWORD, password);
            context.addErrors(errors);

            return SIGN_IN_PAGE_FORWARD;
        }

        context.addSessionAttribute(Attribute.USER, user);
        return mainPageRedirectAction(user);
    }

    private IResponseAction mainPageRedirectAction(User user) {
        if (user.getRole() == UserRole.CLIENT) {
            return MAIN_CLIENT_PAGE_REDIRECT;
        } else {
            return MAIN_ADMIN_PAGE_REDIRECT;
        }
    }

    private boolean wrongPassword(Password one, Password other) {
        return !one.equals(other);
    }
}
