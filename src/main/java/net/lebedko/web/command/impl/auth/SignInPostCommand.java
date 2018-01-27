package net.lebedko.web.command.impl.auth;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.Password;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.service.UserService;
import net.lebedko.web.command.Command;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.command.Context;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;

import static java.util.Objects.isNull;
import static net.lebedko.web.util.constant.PageErrorNames.USER_NOT_EXISTS;
import static net.lebedko.web.util.constant.PageErrorNames.WRONG_PASSWORD;
import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class SignInPostCommand implements Command {
    private static final ResponseAction SIGN_IN_PAGE_FORWARD = new ForwardAction(PAGE.SIGN_IN);
    private static final ResponseAction CLIENT_MAIN_REDIRECT = new RedirectAction(URL.CLIENT_MENU);
    private static final ResponseAction ADMIN_MAIN_REDIRECT = new RedirectAction(URL.ADMIN_MAIN);

    private UserService userService;

    public SignInPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseAction execute(final Context context) {
        final Errors errors = new Errors();

        final Password password = CommandUtils.parsePassword(context);
        final EmailAddress emailAddress = CommandUtils.parseEmail(context);
        final User user = userService.findByEmail(emailAddress);

        if (isNull(user)) {
            errors.register("user not exists", USER_NOT_EXISTS);
            context.addRequestAttribute(Attribute.EMAIL, emailAddress);
            context.addRequestAttribute(Attribute.PASSWORD, password);
            context.addErrors(errors);
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

    private ResponseAction mainPageRedirectAction(User user) {
        if (user.getRole() == UserRole.CLIENT) {
            return CLIENT_MAIN_REDIRECT;
        } else {
            return ADMIN_MAIN_REDIRECT;
        }
    }

    private boolean wrongPassword(Password one, Password other) {
        return !one.equals(other);
    }
}