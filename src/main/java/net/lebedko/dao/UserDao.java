package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;

/**
 * alexandr.lebedko : 21.04.2017.
 */
public interface UserDao{

    User findByEmail(EmailAddress email) throws DataAccessException;

    User insert(User user);
}
