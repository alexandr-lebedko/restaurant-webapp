package net.lebedko.web.command.impl.auth;

import net.lebedko.entity.user.User;
import net.lebedko.service.UserService;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.user.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.lebedko.web.util.constant.PageErrorNames.USER_EXISTS;
import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class SignUpPostCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final ResponseAction MAIN_REDIRECT = new RedirectAction(URL.CLIENT_MENU);
    private static final ResponseAction SIGN_UP_PAGE_FORWARD = new ForwardAction(PAGE.SIGN_UP);

    private UserService userService;
    private UserValidator userValidator;

    public SignUpPostCommand(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Override
    public ResponseAction execute(Context context){
        final Errors errors = new Errors();
        final User user = CommandUtils.parseUser(context);
        userValidator.validate(user, errors);

        if (errors.hasErrors()) {
            context.addRequestAttribute(Attribute.USER, user);
            context.addErrors(errors);
            LOG.info("Attempt to register invalid user: " + user);
            return SIGN_UP_PAGE_FORWARD;
        }
        try {
            userService.register(user);
            context.addSessionAttribute(Attribute.USER, user);
            return MAIN_REDIRECT;
        } catch (IllegalArgumentException e) {
            LOG.info("Attempt to insert existent user: " + user, e);
            errors.register("user exists", USER_EXISTS);
        }

        context.addRequestAttribute(Attribute.USER, user);
        context.addErrors(errors);
        return SIGN_UP_PAGE_FORWARD;
    }
}