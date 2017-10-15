package net.lebedko;

import net.lebedko.entity.general.*;
import net.lebedko.entity.user.*;

import java.util.Random;

import net.lebedko.entity.user.UserRole;

/**
 * alexandr.lebedko : 04.05.2017.
 */

public class EntityGenerator {

    public static User getUser() {
        return new User(getFullName(), getEmailAddress(), getPassword(), getUserRole());
    }

    public static UserRole getUserRole() {
        return getRandom(UserRole.values());
    }

    public static FullName getFullName() {
        FirstName firstName = getRandom(firstNames);
        LastName familyName = getRandom(familyNames);
        return new FullName(firstName, familyName);
    }

    public static EmailAddress getEmailAddress() {
        return getRandom(emailAddresses);
    }

    public static Password getPassword() {
        return getRandom(passwords);
    }


    public static EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    public static Price getPrice() {
        Random random = new Random();
        double doubleValue = random.nextDouble() * 1000;
        return new Price(doubleValue);
    }

    public static boolean getBoolean() {
        return new Random().nextBoolean();
    }

    private static <T> T getRandom(T[] t) {
        Random random = new Random();
        int index = random.nextInt(t.length);
        return t[index];
    }


    private static Password[] passwords = {
            Password.createPasswordFromString("12345678"),
            Password.createPasswordFromString("qweqweqwe"),
            Password.createPasswordFromString("zxcvbnm12")
    };
    private static EmailAddress[] emailAddresses = {
            new EmailAddress("random@mail.ru"),
            new EmailAddress("username@gmail.com"),
            new EmailAddress("surname@gamil.com"),
            new EmailAddress("fantom@rambler.com"),
            new EmailAddress("mrpresident@gmail.com")
    };
    private static FirstName[] firstNames = {
            new FirstName("Alex"),
            new FirstName("Jack"),
            new FirstName("Mellisa"),
            new FirstName("Jane"),
            new FirstName("Dart"),
            new FirstName("Aya")
    };
    private static LastName[] familyNames = {
            new LastName("Smith"),
            new LastName("O'Neel"),
            new LastName("Jones"),
            new LastName("Brooks"),
            new LastName("Joshua"),
            new LastName("Silva")
    };

    private EntityGenerator() {
    }


}
