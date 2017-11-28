package net.lebedko.web.command.impl.auth;

import net.lebedko.entity.user.Password;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.FirstName;
import net.lebedko.entity.user.LastName;
import net.lebedko.entity.user.FullName;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.user.UserValidator;

import static java.util.Optional.ofNullable;
import static net.lebedko.web.util.constant.PageErrorNames.USER_EXISTS;
import static net.lebedko.web.util.constant.WebConstant.*;

public class SignUpPostCommand extends AbstractCommand implements ICommand {
    private static final IResponseAction MAIN_PAGE_REDIRECT = new RedirectAction(URL.CLIENT_CATEGORIES);
    private static final IResponseAction SIGN_UP_PAGE_FORWARD = new ForwardAction(PAGE.SIGN_UP);

    private UserService userService;
    private UserValidator userValidator;

    public SignUpPostCommand(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        User user = retrieveUser(context);

        final Errors errors = new Errors();
        userValidator.validate(user, errors);

        if (errors.hasErrors()) {
            context.addRequestAttribute("errors", errors);
            context.addRequestAttribute("user", user);
            LOG.info("Attempt to register invalid user: " + user);

            return SIGN_UP_PAGE_FORWARD;
        }

        try {
            userService.register(user);
            context.addSessionAttribute("user", user);

            return MAIN_PAGE_REDIRECT;

        } catch (IllegalArgumentException e) {
            LOG.info("Attempt to insert existent user: " + user, e);
            errors.register("user exists", USER_EXISTS);
        }
        context.addErrors(errors);
        context.addSessionAttribute("user", user);
        return SIGN_UP_PAGE_FORWARD;
    }


    private static User retrieveUser(IContext context) {
        String firstNameString = ofNullable(context.getRequestParameter("firstName")).orElse("");
        String lastNameString = ofNullable(context.getRequestParameter("lastName")).orElse("");
        String emailString = ofNullable(context.getRequestParameter("email")).orElse("");
        String passwordString = ofNullable(context.getRequestParameter("password")).orElse("");

        return new User(
                new FullName(
                        new FirstName(firstNameString),
                        new LastName(lastNameString)
                ),
                new EmailAddress(emailString),
                Password.createPasswordFromString(passwordString),
                UserRole.CLIENT
        );
    }
}
