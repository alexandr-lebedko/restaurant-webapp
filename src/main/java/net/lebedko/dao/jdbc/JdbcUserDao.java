package net.lebedko.dao.jdbc;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.dao.template.Mapper;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Password;
import net.lebedko.entity.user.*;
import net.lebedko.entity.user.User.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.PropertyUtil.loadProperties;
import static net.lebedko.util.Util.checkValidity;

/**
 * alexandr.lebedko : 26.04.2017.
 */

public class JdbcUserDao implements UserDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String find_by_email = props.getProperty("user.getByEmail");
    private static final String find_active_by_email = props.getProperty("user.getActiveByEmail");
    private static final String find_by_id = props.getProperty("user.getById");
    private static final String insert = props.getProperty("user.insert");
    private static final String update = props.getProperty("user.update");
    private static final String delete = props.getProperty("user.delete");
    private static final UserMapper mapper = new UserMapper();

    private QueryTemplate template;

    public JdbcUserDao(QueryTemplate template) {
        this.template = requireNonNull(template, "Template cannot be null");
    }

    @Override
    public User findByEmail(EmailAddress email) throws DataAccessException {
        requireNonNull(email, "Email Address cannot be null");
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email.toString());
        return template.queryOne(find_by_email, params, mapper);
    }


    @Override
    public User findActivatedByEmail(EmailAddress email) throws DataAccessException {
        requireNonNull(email, "Email Address cannot be null");
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email.toString());
        return template.queryOne(find_active_by_email, params, mapper);
    }

    @Override
    public User insert(User user) throws DataAccessException {
        checkValidity(user);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getRole().name());
        params.put(2, user.getEmail().toString());
        params.put(3, user.getFullName().getFirstName().toString());
        params.put(4, user.getFullName().getLastName().toString());
        params.put(5, user.getPassword().getPasswordHash());

        user.setId(template.insertAndReturnKey(insert, params));

        return user;
    }

    @Override
    public User findById(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(find_by_id, params, mapper);
    }

    @Override
    public void update(User user) throws DataAccessException {
        checkValidity(user);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getRole().name());
        params.put(2, user.getEmail().toString());
        params.put(3, user.getFullName().getFirstName().toString());
        params.put(4, user.getFullName().getLastName().toString());
        params.put(5, user.getPassword().getPasswordHash());
        params.put(6, user.getId());

        template.update(update, params);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        template.update(delete, params);
    }


    public static final class UserMapper implements Mapper<User> {
        @Override
        public User map(ResultSet rs) throws SQLException {
            return new User(rs.getInt("u_id"),
                    new FullName(new FirstName(rs.getString("u_first_name")), new LastName(rs.getString("u_last_name"))),
                    new EmailAddress(rs.getString("u_email")),
                    Password.createPasswordFromHash(rs.getString("u_password_hash")),
                    UserRole.valueOf(rs.getString("u_role")),
                    rs.getBoolean("u_activated"));
        }
    }

}
