package net.lebedko.service.impl;

import net.lebedko.dao.UserDao;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserView;
import net.lebedko.service.UserService;
import net.lebedko.service.exception.ServiceException;


import java.util.UUID;

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
    public UUID register(User user) throws ServiceException {
        UUID uniqueKey = UUID.randomUUID();
        template.doTxService(() -> {
            User newUser = userDao.insert(user);
            userDao.insertRegistrationKey(newUser, uniqueKey);
        });
        return uniqueKey;
    }

    @Override
    public boolean authenticate(UserView userView) throws ServiceException {
        User user = template.doTxService(() -> userDao.findByEmail(userView.getEmailAddress()));
        return nonNull(user) && user.getPassword().equals(userView.getPassword());
    }

    @Override
    public boolean activateUser(UUID key) throws ServiceException {
        return template.doTxService(() -> {
            User user = userDao.findByRegistrationKey(key);
            if (isNull(user))
                return false;

            userDao.deleteRegistrationKey(key);
            userDao.update(new User(user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    true));
            return true;
        });
    }

    public User findByEmail(EmailAddress address) throws ServiceException {
        return template.doTxService(() -> userDao.findByEmail(address));
    }
}
