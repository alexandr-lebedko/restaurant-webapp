package net.lebedko.service.impl;

import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.UserDao;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.service.UserService;

public class UserServiceImpl implements UserService {
    private TransactionManager txManager;
    private UserDao userDao;

    public UserServiceImpl(TransactionManager txManager, UserDao userDao) {
        this.txManager = txManager;
        this.userDao = userDao;
    }

    @Override
    public User register(User user) {
        return txManager.tx(() -> userDao.insert(user));
    }

    @Override
    public User findByEmail(EmailAddress address) {
        return txManager.tx(() -> userDao.findByEmail(address));
    }
}
