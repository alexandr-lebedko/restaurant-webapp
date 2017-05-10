package net.lebedko.service.impl;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.EntityExistsException;
import net.lebedko.dao.transaction.TransactionManager;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.UserService;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class UserServiceImpl implements UserService {
    private TransactionManager txManager;
    private UserDao userDao;

    public UserServiceImpl(TransactionManager txManager, UserDao userDao) {
        this.txManager = requireNonNull(txManager, "Transaction Manager cannot be null!");
        this.userDao = requireNonNull(userDao, "User dao cannot be null");
    }

    @Override
    public void registerUser(User user) throws EntityExistsException, DataAccessException {
        txManager.tx(() -> {
        });
    }

    @Override
    public boolean authenticate(final UserView user) {
        return false;
    }

    private User findByEmail(final EmailAddress emailAddress) throws Exception {
        return txManager.tx(() -> userDao.findByEmail(emailAddress));
    }
}
