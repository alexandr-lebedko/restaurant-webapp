package net.lebedko.service;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.exception.ServiceException;

import java.util.UUID;

/**
 * alexandr.lebedko : 05.05.2017.
 */

public interface UserService {

    UUID register(User user) throws ServiceException;

    boolean authenticate(UserView user) throws ServiceException;

    boolean activateUser(UUID key) throws ServiceException;
}
