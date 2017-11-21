package net.lebedko.dao.jdbc;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.UserMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.*;

import java.util.HashMap;
import java.util.Map;


/**
 * alexandr.lebedko : 26.04.2017.
 */

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    private static final String find_by_email = QUERIES.getProperty("user.getByEmail");
    private static final String find_by_id = QUERIES.getProperty("user.getById");
    private static final String insert = QUERIES.getProperty("user.insert");
    private static final String update = QUERIES.getProperty("user.update");
    private static final String delete = QUERIES.getProperty("user.delete");

    private static final UserMapper mapper = new UserMapper();

    public JdbcUserDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public User findByEmail(EmailAddress email) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email.toString());
        return template.queryOne(find_by_email, params, mapper);
    }

    public User insert(User user) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getRole().name());
        params.put(2, user.getEmail().toString());
        params.put(3, user.getFullName().getFirstName().toString());
        params.put(4, user.getFullName().getLastName().toString());
        params.put(5, user.getPassword().getPasswordHash());

        user.setId(template.insertAndReturnKey(insert, params));

        return user;
    }

    public User findById(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(find_by_id, params, mapper);
    }

    public void update(User user) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getRole().name());
        params.put(2, user.getEmail().toString());
        params.put(3, user.getFullName().getFirstName().toString());
        params.put(4, user.getFullName().getLastName().toString());
        params.put(5, user.getPassword().getPasswordHash());
        params.put(6, user.getId());

        template.update(update, params);
    }

    public void delete(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        template.update(delete, params);
    }


}
