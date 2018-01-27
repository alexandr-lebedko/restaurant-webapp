package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.FullName;
import net.lebedko.entity.user.Password;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.entity.user.FirstName;
import net.lebedko.entity.user.LastName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
    private static final String ID = "u_id";
    private static final String FIRST_NAME = "u_first_name";
    private static final String LAST_NAME = "u_last_name";
    private static final String EMAIL = "u_email";
    private static final String PASSWORD = "u_password_hash";
    private static final String ROLE = "u_role";

    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong(ID),
                new FullName(new FirstName(rs.getString(FIRST_NAME)), new LastName(rs.getString(LAST_NAME))),
                new EmailAddress(rs.getString(EMAIL)),
                Password.createPasswordFromHash(rs.getString(PASSWORD)),
                UserRole.valueOf(rs.getString(ROLE))
        );
    }
}
