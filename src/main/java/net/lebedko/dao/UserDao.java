package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;

/**
 * alexandr.lebedko : 21.04.2017.
 */
public interface UserDao extends GenericDao<User> {

    User findByEmail(EmailAddress email) throws DataAccessException;
}
