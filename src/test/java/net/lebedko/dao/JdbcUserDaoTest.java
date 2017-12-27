package net.lebedko.dao;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.exception.EntityExistsException;
import net.lebedko.dao.jdbc.JdbcUserDao;
import net.lebedko.entity.user.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class JdbcUserDaoTest extends AbstractJdbcTest {
    private JdbcUserDao userDao;

    public void setUp() throws Exception {
        super.setUp();
        this.userDao = new JdbcUserDao(queryTemplate);
    }

    public void testFindByEmail() throws Exception {
        User expected = new User(1L,
                new FullName(new FirstName("Boris"), new LastName("Blade")),
                new EmailAddress("test.user@gmail.com"),
                Password.createPasswordFromHash("25d55ad283aa40af464c76d713c7ad"),
                UserRole.CLIENT);

        User actual = userDao.findByEmail(expected.getEmail());

        assertEquals(expected, actual);
    }

    public void testInsertNewUser() throws Exception {
        User user = EntityGenerator.getUser();

        userDao.insert(user);

        ITable actualTable = getConnection().createDataSet().getTable("users");
        ITable initialTable = getDataSet().getTable("users");

        assertFalse(actualTable.getRowCount() == initialTable.getRowCount());
        assertEquals(user, userDao.findByEmail(user.getEmail()));
    }

    public void testInsertUserWithSameEmail() {
        User user = EntityGenerator.getUser();
        userDao.insert(user);

        EntityExistsException expected = null;
        try {
            userDao.insert(user);
        } catch (EntityExistsException e) {
            expected = e;
        }
        assertNotNull(expected);
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass().getResourceAsStream("/data/usersDataSet.xml"));
    }
}
