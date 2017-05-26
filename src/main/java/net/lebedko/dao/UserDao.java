package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;

import java.util.UUID;

/**
 * alexandr.lebedko : 21.04.2017.
 */
public interface UserDao extends GenericDao<User> {

    User findByEmail(EmailAddress email) throws DataAccessException;

    User findActivatedByEmail(EmailAddress email) throws DataAccessException;

    void insertRegistrationKey(User user, UUID key) throws DataAccessException;

    User findByRegistrationKey(UUID key) throws DataAccessException;

}
