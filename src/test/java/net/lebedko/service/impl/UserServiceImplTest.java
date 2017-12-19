package net.lebedko.service.impl;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.TransactionManagerStub;
import net.lebedko.dao.UserDao;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    private TransactionManager txManager = new TransactionManagerStub();

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        this.userService = new UserServiceImpl(txManager, userDao);
    }

    @Test
    public void register() throws Exception {
        User user = EntityGenerator.getUser();
        userService.register(user);

        verify(userDao).insert(user);
    }

    @Test
    public void findByEmail() throws Exception {
        EmailAddress emailAddress = EntityGenerator.getEmailAddress();

        userService.findByEmail(emailAddress);

        verify(userDao).findByEmail(emailAddress);
    }

}