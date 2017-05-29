
package net.lebedko.dao.jdbc;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.UniqueViolationException;
import net.lebedko.dao.connection.TestConnectionProvider;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static net.lebedko.EntityGenerator.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 04.05.2017.
 */
public class JdbcUserDaoTest {

    @ClassRule
    public static final DataBaseResource dataBaseResource = new DataBaseResource();

    private TestConnectionProvider connectionProvider;
    private QueryTemplate template;
    private JdbcUserDao userDao;


    @Before
    public void beforeTest() throws Exception {
        connectionProvider = dataBaseResource.getConnectionProvider();
        template = new QueryTemplate(connectionProvider);
        userDao = new JdbcUserDao(template);
    }

    @After
    public void afterTest() throws Exception {
        template.update("DELETE FROM users");
        connectionProvider.closeConnection();
    }

    @Test(expected = NullPointerException.class)
    public void nullQueryTemplateArgumentTest() {
        new JdbcUserDao(null);
    }

    @Test
    public void findByEmailTest() throws Exception {
        User user = getUser();
        user = userDao.insert(user);

        EmailAddress email = user.getEmail();

        assertThat("Retrieved user should be equal to inserted!", userDao.findByEmail(email), is(user));
    }

    @Test
    public void insertValidUserTest() throws Exception {
        User user = getUser();

        assertNull("Retrieved user should be equal to null", userDao.findByEmail(user.getEmail()));

        User insertedUser = userDao.insert(user);
        assertNotNull("Retrieved user should not be null!", userDao.findByEmail(insertedUser.getEmail()));
    }


    @Test(expected = RuntimeException.class)
    public void insertInvalidUserTest() throws Exception {
        User user = mock(User.class);
        when(user.isValid()).thenReturn(false);

        userDao.insert(user);
    }

    @Test(expected = UniqueViolationException.class)
    public void insertUserWithSameEmail() throws Exception {
        User user = getUser();
        userDao.insert(user);
        userDao.insert(user);
    }

    @Test
    public void findById() throws Exception {
        User user = getUser();
        user = userDao.insert(user);

        User retrievedUser = userDao.findById(user.getId());
        assertThat("Users should be equals!", retrievedUser, is(user));
    }

    @Test(expected = RuntimeException.class)
    public void updateWithInvalidUserArgument() throws Exception {
        User user = mock(User.class);
        when(user.isValid()).thenReturn(false);

        userDao.update(user);
    }

    @Test
    public void update() throws Exception {
        User initialUser = getUser();
        initialUser = userDao.insert(initialUser);

        assertThat("Inserted and retrieved users should be equal!", userDao.findByEmail(initialUser.getEmail()), is(initialUser));

        User updatedUser = new User(initialUser.getId(), getFullName(), getEmailAddress(), getPassword(), getUserRole());
        userDao.update(updatedUser);

        assertThat("Retrieved user should be equal to updatedUser and not to initial",
                userDao.findById(initialUser.getId()),
                allOf(equalTo(updatedUser), not(equalTo(initialUser))
                ));
    }

    @Test(expected = DataAccessException.class)
    public void updateOnTakenEmailAddress() throws Exception {

        EmailAddress initialEmail = new EmailAddress("initial@gmail.com");
        User user = new User(getFullName(), initialEmail, getPassword(), getUserRole());

        EmailAddress takenEmail = new EmailAddress("taken@mail.ru");
        User otherUser = new User(getFullName(), takenEmail, getPassword(), getUserRole());

        userDao.insert(user);
        userDao.insert(otherUser);

        User updatedUser = new User(user.getId(), user.getFullName(), takenEmail, user.getPassword(), user.getRole());
        userDao.update(updatedUser);
    }


    @Test
    public void delete() throws Exception {
        User user = getUser();
        user = userDao.insert(user);

        assertThat("Inserted and retrieved users should be equal!", userDao.findByEmail(user.getEmail()), is(user));

        userDao.delete(user.getId());

        assertNull("User should be equal to null", userDao.findByEmail(user.getEmail()));
    }


    public void findByRegistrationKey() throws Exception {
        //TODO
    }

    public void insertRegistrationKey() throws Exception {
        //TODO
    }

    public void findActivatedByEmail() throws Exception {
        //TODO
    }
}