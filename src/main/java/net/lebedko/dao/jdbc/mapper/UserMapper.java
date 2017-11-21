package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.user.*;

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
        return new User(getId(rs),
                new FullName(getFirstName(rs), getLastName(rs)),
                getEmailAddress(rs),
                getPassword(rs),
                getUserRole(rs));
    }

    private Long getId(ResultSet rs) throws SQLException {
        return rs.getLong(ID);
    }

    private FirstName getFirstName(ResultSet rs) throws SQLException {
        return new FirstName(rs.getString(FIRST_NAME));
    }

    private LastName getLastName(ResultSet rs) throws SQLException {
        return new LastName(rs.getString(LAST_NAME));
    }

    private EmailAddress getEmailAddress(ResultSet rs) throws SQLException {
        return new EmailAddress(rs.getString(EMAIL));
    }

    private Password getPassword(ResultSet rs) throws SQLException {
        return Password.createPasswordFromHash(rs.getString(PASSWORD));
    }

    private UserRole getUserRole(ResultSet rs) throws SQLException {
        return UserRole.valueOf(rs.getString(ROLE));
    }
}
