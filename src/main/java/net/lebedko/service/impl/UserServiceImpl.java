package net.lebedko.service.impl;

import net.lebedko.dao.UserDao;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
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
        this.template = requireNonNull(template, "Service Template cannot be null!");
        this.userDao = requireNonNull(userDao, "UserDao cannot be null!");
    }

    @Override
    public User register(User user) throws ServiceException {
        return template.doTxService(() -> userDao.insert(user));
    }

    @Override
    public boolean authenticate(UserView userView) throws ServiceException {
        User user = template.doTxService(() -> userDao.findByEmail(userView.getEmailAddress()));
        return nonNull(user) && user.getPassword().equals(userView.getPassword());
    }


    public User findByEmail(EmailAddress address) throws ServiceException {
        return template.doTxService(() -> userDao.findByEmail(address));
    }
}
