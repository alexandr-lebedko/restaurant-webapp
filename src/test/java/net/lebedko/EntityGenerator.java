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
import net.lebedko.service.OrderBucket;
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

    public static OrderState getOrderState() {
        return getRandom(OrderState.values());
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
        return new Order(getLong(), getInvoice(), getOrderState(), LocalDateTime.now());
    }

    public static OrderBucket getOrderBucket() {
        OrderBucket orderBucket = new OrderBucket();
        int limit = 1 + new Random().nextInt(10);

        for (int i = 0; i < limit; i++) {
            orderBucket.add(getItem(), getLong());
        }
        return orderBucket;
    }

    public static OrderItem getOrderItem() {
        return new OrderItem(getOrder(), getItem(), getLong());
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
        return new Title[]{
                new Title(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Суп")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Soup")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Суп")
                        .build()
                ),
                new Title(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Мусс")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Pure")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Мус")
                        .build()
                ),
                new Title(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Торт")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Cake")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Тiстечко")
                        .build()
                )};
    }

    private static Description[] createDescriptions() {
        return new Description[]{
                new Description(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Очень вкусное блюдо!")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Very tasty dish!")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Дуже смачна страва!")
                        .build()
                ),
                new Description(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Классический украинский суп")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Classic ukrainian soup")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Класичний український суп")
                        .build()
                ),
                new Description(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Ароматные охотничьи колбаски")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Fragrant hunting sausages")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Ароматнi мисливськi ковбаски")
                        .build()
                )
        };
    }

    private static Category[] createCategories() {
        return new Category[]{
                new Category(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Супы")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Soups")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Супи")
                        .build()
                ),
                new Category(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Паста")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Pasta")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "Паста")
                        .build()
                ),
                new Category(StringI18N.builder()
                        .add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), "Мясо")
                        .add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), "Meat")
                        .add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), "М'ясо")
                        .build()
                )
        };
    }

    private static <T> T getRandom(T[] t) {
        Random random = new Random();
        int index = random.nextInt(t.length);
        return t[index];
    }

    private EntityGenerator() {
    }
}
