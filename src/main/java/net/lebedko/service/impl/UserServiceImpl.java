package net.lebedko.service.impl;

import net.lebedko.dao.UserDao;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
    private ServiceTemplate template;
    private UserDao userDao;

    public UserServiceImpl(ServiceTemplate template, UserDao userDao) {
        this.template = template;
        this.userDao = userDao;
    }

    @Override
    public User register(User user) {
        return template.doTxService(() -> userDao.insert(user));
    }

    @Override
    public User findByEmail(EmailAddress address) {
        return template.doTxService(() -> userDao.findByEmail(address));
    }
}
