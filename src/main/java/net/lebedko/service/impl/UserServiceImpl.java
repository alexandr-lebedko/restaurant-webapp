package net.lebedko.service.impl;

import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.UserDao;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;

import static java.util.Objects.*;

/**
 * alexandr.lebedko : 11.05.2017.
 */

public class UserServiceImpl implements UserService {
    private ServiceTemplate template;
    private UserDao userDao;

    public UserServiceImpl(ServiceTemplate template, UserDao userDao) {
        this.template = template;
        this.userDao = userDao;
    }

    @Override
    public User register(User user) throws ServiceException {
        return template.doTxService(() -> userDao.insert(user));
    }

    @Override
    public User findByEmail(EmailAddress address) throws ServiceException {
        return template.doTxService(() -> userDao.findByEmail(address));
    }
}
