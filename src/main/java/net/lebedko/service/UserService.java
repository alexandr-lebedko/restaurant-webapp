package net.lebedko.service;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;

/**
 * alexandr.lebedko : 05.05.2017.
 */

public interface UserService {

    void registerUser(User user) throws DataAccessException;

    boolean authenticate(UserView user) throws DataAccessException;

}
