package net.lebedko.web.command.impl;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.Password;
import net.lebedko.entity.user.*;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.PageLocations;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.user.UserValidator;

import static java.util.Optional.*;
import static net.lebedko.web.util.constant.PageErrorNames.USER_EXISTS;

/**
 * alexandr.lebedko : 15.06.2017
 */
public class SignUpPostCommand extends AbstractCommand implements ICommand {
    private static final IResponseAction MAIN_PAGE_REDIRECT = new RedirectAction(PageLocations.MAIN_CLIENT);
    private static final IResponseAction REGISTRATION_PAGE_FORWARD = new ForwardAction(PageLocations.SIGN_UP);

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

        return REGISTRATION_PAGE_FORWARD;
        }

        try {
            userService.register(user);
            context.addSessionAttribute("user", user);

            return MAIN_PAGE_REDIRECT;

        } catch (EntityExistsException eex) {
            LOG.info("Attempt to insert existent user: " + user);
            errors.register("user exists", USER_EXISTS);
        }

        return REGISTRATION_PAGE_FORWARD;
    }


    public static User retrieveUser(IContext context) {
        String firstNameString = ofNullable(context.getRequestParameter("firstName")).orElse("");
        String lastNameString = ofNullable(context.getRequestParameter("lastName")).orElse("");
        String emailString = ofNullable(context.getRequestParameter("email")).orElse("");
        String passwordString = ofNullable(context.getRequestParameter("password")).orElse("");

        return new User(
                new FullName(new FirstName(firstNameString), new LastName(lastNameString)),
                new EmailAddress(emailString),
                Password.createPasswordFromString(passwordString),
                User.UserRole.CLIENT);
    }
}
