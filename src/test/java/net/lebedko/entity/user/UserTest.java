package net.lebedko.entity.user;

import net.lebedko.entity.user.User.UserRole;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexandr.lebedko on 21.03.2017.
 */
public class UserTest {
    private static boolean setUpIsDone = false;

    private static FullName invalidFullName;
    private static FullName validFullName;
    private static EmailAddress invalidEmail;
    private static EmailAddress validEmail;
    private static Password invalidPassword;
    private static Password validPassword;
    private static User.UserRole role;
    private static int id=1;

    @Before
    public void setUp() {
        if (!setUpIsDone) {
            role = UserRole.CLIENT;
            validFullName = mock(FullName.class);
            invalidFullName = mock(FullName.class);
            validEmail = mock(EmailAddress.class);
            invalidEmail = mock(EmailAddress.class);
            validPassword = mock(Password.class);
            invalidPassword = mock(Password.class);

            when(validFullName.isValid()).thenReturn(true);
            when(invalidFullName.isValid()).thenReturn(false);
            when(validEmail.isValid()).thenReturn(true);
            when(invalidEmail.isValid()).thenReturn(false);
            when(validPassword.isValid()).thenReturn(true);
            when(invalidPassword.isValid()).thenReturn(false);

            setUpIsDone = true;
        }

    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new User(null, null, null, null);
    }

    @Test
    public void invalidFullNameArgumentValidityTest() {
        assertFalse(new User(invalidFullName, validEmail, validPassword, role).isValid());
    }

    @Test
    public void invalidEmailArgumentValidityTest() {
        assertFalse(new User(validFullName, invalidEmail, validPassword, role).isValid());
    }

    @Test
    public void invalidPasswordArgumentValidityTest() {
        assertFalse(new User(validFullName, validEmail, invalidPassword, role).isValid());
    }

    @Test
    public void allValidArgumentsValidityTest() {
        assertTrue(new User(validFullName, validEmail, validPassword, role).isValid());
    }

    @Test
    public void getterMethodsTest() {
        User user = new User(id, validFullName, validEmail, validPassword, role);

        assertEquals(id, user.getId());
        assertEquals(validFullName, user.getFullName());
        assertEquals(validEmail, user.getEmail());
        assertEquals(validPassword, user.getPassword());
    }

}