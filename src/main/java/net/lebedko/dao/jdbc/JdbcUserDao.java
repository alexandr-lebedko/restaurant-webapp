package net.lebedko.dao.jdbc;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.jdbc.mapper.UserMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.*;

import java.util.HashMap;
import java.util.Map;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    private static final String FIND_BY_EMAIL = QUERIES.getProperty("user.getByEmail");
    private static final String INSERT = QUERIES.getProperty("user.insert");
    private static final UserMapper MAPPER = new UserMapper();

    public JdbcUserDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public User findByEmail(EmailAddress email) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email.toString());

        return template.queryOne(FIND_BY_EMAIL, params, MAPPER);
    }

    @Override
    public User insert(User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getRole().name());
        params.put(2, user.getEmail().toString());
        params.put(3, user.getFullName().getFirstName().toString());
        params.put(4, user.getFullName().getLastName().toString());
        params.put(5, user.getPassword().getPasswordHash());
        user.setId(template.insertAndReturnKey(INSERT, params));

        return user;
    }
}
