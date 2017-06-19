package net.lebedko;

import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.dish.Dish.DishCategory;
import net.lebedko.entity.general.*;
import net.lebedko.entity.order.Order.OrderStatus;
import net.lebedko.entity.user.*;

import java.util.Random;

import static net.lebedko.entity.user.User.UserRole;

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

    public static Dish getDish() {
        return new Dish(getTitle(), getDescription(), getDishCategory());
    }

    public static Title getTitle() {
        return getRandom(titles);
    }

    public static Text getDescription() {
        return getRandom(descriptions);
    }

    public static DishCategory getDishCategory() {
        return getRandom(DishCategory.values());
    }

    public static Title[] getTitles() {
        return titles;
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

    public static OrderStatus getOrderStatus(){
        return getRandom(OrderStatus.values());
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

    private static Title[] titles = {
            new Title("Fish'n'Chips"),
            new Title("Duck Brest"),
            new Title("The Burger"),
            new Title("Spring Roll with Duck"),
            new Title("Ice Cream")
    };


    private static Text[] descriptions = {
            new Text("Such a delicious dish!"),
            new Text("Fantastic flavour!"),
            new Text("Chefs favourite dish!")
    };

    private EntityGenerator() {
    }


}
