package net.lebedko.service;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.exception.ServiceException;

import java.util.UUID;

/**
 * alexandr.lebedko : 05.05.2017.
 */

public interface UserService {

    User register(User user) throws ServiceException;

    User findByEmail(EmailAddress emailAddress) throws ServiceException;

  static boolean authenticate(UserView userView, User user) {
        return userView.getEmailAddress().equals(user.getEmail())
                && userView.getPassword().equals(user.getPassword());
    }
}
