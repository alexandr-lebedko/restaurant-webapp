package net.lebedko.web.command.impl;

import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Password;
import net.lebedko.entity.user.*;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Pages;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.UserValidator;


import static java.util.Optional.*;
import static net.lebedko.web.util.constant.PageErrorNames.USER_EXISTS;

/**
 * alexandr.lebedko : 15.06.2017
 */
public class RegistrationPostCommand extends AbstractCommand implements ICommand {
    private static final IResponseAction MAIN_PAGE_REDIRECT = new RedirectAction(Pages.MAIN_CLIENT);
    private static final IResponseAction REGISTRATION_PAGE_FORWARD = new ForwardAction(Pages.REGISTRATION);

    private UserService userService;
    private UserValidator userValidator;

    public RegistrationPostCommand(UserService userService, UserValidator userValidator) {
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
        FirstName firstName = new FirstName(ofNullable(context.getRequestAttribute("firstName")).orElse(""));
        LastName familyName = new LastName(ofNullable(context.getRequestAttribute("familyName")).orElse(""));
        EmailAddress emailAddress = new EmailAddress(ofNullable(context.getRequestAttribute("emailAddress")).orElse(""));
        Password password = Password.createPasswordFromString(ofNullable(context.getRequestAttribute("password")).orElse(""));
        return new User(new FullName(firstName, familyName), emailAddress, password, User.UserRole.CLIENT);
    }
}
