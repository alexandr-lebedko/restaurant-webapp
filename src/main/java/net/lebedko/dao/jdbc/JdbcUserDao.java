package net.lebedko.dao.jdbc;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.jdbc.mapper.UserMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;


public class JdbcUserDao implements UserDao {
    private static final String FIND_BY_EMAIL = QUERIES.getProperty("user.getByEmail");
    private static final String INSERT = QUERIES.getProperty("user.insert");
    private static final UserMapper MAPPER = new UserMapper();

    private QueryTemplate template;

    public JdbcUserDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public User insert(User user) {
        Integer id = template.insertAndReturnKey(INSERT, new Object[]{
                user.getRole().name(),
                user.getEmail().toString(),
                user.getFullName().getFirstName().toString(),
                user.getFullName().getLastName().toString(),
                user.getPassword().getPasswordHash()});

        user.setId(id.longValue());

        return user;
    }

    @Override
    public User findByEmail(EmailAddress email) {
        return template.queryOne(FIND_BY_EMAIL,
                new Object[]{email.toString()},
                MAPPER);
    }

    @Override
    public User findById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException();
    }
}
