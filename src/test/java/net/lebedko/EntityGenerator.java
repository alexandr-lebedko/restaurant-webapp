package net.lebedko;

import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.general.*;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.Title;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.*;

import java.time.LocalDateTime;
import java.util.*;

import net.lebedko.entity.user.UserRole;
import net.lebedko.util.SupportedLocales;

public class EntityGenerator {

    public static User getUser() {
        return new User(getFullName(), getEmailAddress(), getPassword(), getUserRole());
    }

    public static Category getCategory() {
        return getRandom(categories);
    }

    public static Title getTitle() {
        return getRandom(titles);
    }

    public static Description getDescription() {
        return getRandom(descriptions);
    }

    public static UserRole getUserRole() {
        return getRandom(UserRole.values());
    }

    public static FullName getFullName() {
        return new FullName(getRandom(firstNames), getRandom(familyNames));
    }

    public static Password getPassword() {
        return getRandom(passwords);
    }

    public static EmailAddress getEmailAddress() {
        return getRandom(emailAddresses);
    }

    public static Invoice getInvoice() {
        return new Invoice(getLong(), getUser(), getInvoiceState(), getPrice(), LocalDateTime.now());
    }

    public static InvoiceState getInvoiceState() {
        return getRandom(InvoiceState.values());
    }

    public static Long getLong() {
        return new Random().nextLong();
    }

    public static Pageable getPageable() {
        return new Pageable(new Random().nextInt(10));
    }

    public static Price getPrice() {
        return new Price(new Random().nextDouble() * 100);
    }

    public static Item getItem() {
        return new Item(getLong(), getTitle(), getDescription(), getCategory(), getPrice(), getRandom(imageIds));
    }

    public static Order getOrder() {
        return new Order(getLong(), getInvoice(), getRandom(OrderState.values()), LocalDateTime.now());
    }

    public static OrderItem getOrderItem() {
        return new OrderItem(getOrder(), getItem(), getLong());
    }

    public static boolean getBoolean() {
        return new Random().nextBoolean();
    }

    private static String[] imageIds = {
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    };

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

    private static Title[] titles = createTitles();

    private static Description[] descriptions = createDescriptions();

    private static Category[] categories = createCategories();

    private static Title[] createTitles() {
        Map<Locale, String> localeToString = new HashMap<>();
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Суп");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Soup");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Суп");

        Title soup = new Title(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Мусс");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Pure");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Мус");

        Title pure = new Title(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Торт");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Cake");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Тiстечко");

        Title cake = new Title(new StringI18N(localeToString));

        return new Title[]{soup, pure, cake};
    }

    private static Description[] createDescriptions() {
        Map<Locale, String> localeToString = new HashMap<>();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Очень вкусное блюдо!");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Very tasty dish!");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Дуже смачна страва!");

        Description dishDescription = new Description(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Классический украинский суп");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Classic ukrainian soup");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Класичний український суп");

        Description soupDescription = new Description(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Ароматные охотничьи колбаски");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Fragrant hunting sausages");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Ароматнi мисливськi ковбаски");

        Description sausagesDescription = new Description(new StringI18N(localeToString));
        localeToString.clear();

        return new Description[]{dishDescription, soupDescription, sausagesDescription};
    }

    private static Category[] createCategories() {
        Map<Locale, String> localeToString = new HashMap<>();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Супы");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Soups");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Супи");

        Category soups = new Category(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Паста");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Pasta");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Паста");

        Category pasta = new Category(new StringI18N(localeToString));
        localeToString.clear();

        localeToString.put(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Мясо");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Meat");
        localeToString.put(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "М'ясо");

        Category meat = new Category(new StringI18N(localeToString));

        return new Category[]{soups, pasta, meat};
    }

    private static <T> T getRandom(T[] t) {
        Random random = new Random();
        int index = random.nextInt(t.length);
        return t[index];
    }

    private EntityGenerator() {
    }
}
