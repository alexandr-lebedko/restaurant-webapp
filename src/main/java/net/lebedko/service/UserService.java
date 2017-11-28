package net.lebedko.service;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;

public interface UserService {

    User register(User user);

    User findByEmail(EmailAddress emailAddress);
}
