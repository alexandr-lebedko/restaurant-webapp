package net.lebedko.service;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.exception.ServiceException;

/**
 * alexandr.lebedko : 05.05.2017.
 */

public interface UserService {

    User register(User user) throws ServiceException;

    User findByEmail(EmailAddress emailAddress) throws ServiceException;
}
