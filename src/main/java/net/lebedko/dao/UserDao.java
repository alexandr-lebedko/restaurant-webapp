package net.lebedko.dao;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;

public interface UserDao{

    User findByEmail(EmailAddress email);

    User insert(User user);
}
