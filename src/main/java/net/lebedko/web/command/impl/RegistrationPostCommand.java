package net.lebedko.web.command.impl;

import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Password;
import net.lebedko.entity.user.*;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.mail.MailMessage;
import net.lebedko.service.mail.Mailer;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.Pages;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.UserValidator;

import java.util.Locale;
import java.util.UUID;

import static java.util.Optional.*;

/**
 * alexandr.lebedko : 15.06.2017
 */
public class RegistrationPostCommand implements ICommand {
    private static ThreadLocal<Locale> userLocale = new ThreadLocal<>();

    private UserService userService;
    private UserValidator userValidator;

    public RegistrationPostCommand(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Override
    public IResponseAction execute(IContext context) {
        User user = retrieveUser(context);

        Errors errors = new Errors();
        userValidator.validate(user, errors);

        if (errors.hasErrors()) {
            context.addRequestAttribute("errors", errors);
            return new ForwardAction(Pages.REGISTRATION);
        }

        userLocale.set(context.getLocale());


        return null;
    }


    public static User retrieveUser(IContext context) {
        FirstName firstName = new FirstName(ofNullable(context.getRequestAttribute("firstName")).orElse(""));
        LastName familyName = new LastName(ofNullable(context.getRequestAttribute("familyName")).orElse(""));
        EmailAddress emailAddress = new EmailAddress(ofNullable(context.getRequestAttribute("emailAddress")).orElse(""));
        Password password = Password.createPasswordFromString(ofNullable(context.getRequestAttribute("password")).orElse(""));
        return new User(new FullName(firstName, familyName), emailAddress, password, User.UserRole.CLIENT);
    }
}
