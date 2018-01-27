package net.lebedko.web.util;

import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Title;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.FirstName;
import net.lebedko.entity.user.FullName;
import net.lebedko.entity.user.LastName;
import net.lebedko.entity.user.Password;
import net.lebedko.entity.user.User;
import net.lebedko.entity.user.UserRole;
import net.lebedko.service.OrderBucket;
import net.lebedko.web.command.Context;
import net.lebedko.web.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static net.lebedko.util.SupportedLocales.EN_CODE;
import static net.lebedko.util.SupportedLocales.RU_CODE;
import static net.lebedko.util.SupportedLocales.UA_CODE;
import static net.lebedko.util.SupportedLocales.getByCode;

public class CommandUtils {
    private static final Logger LOG = LogManager.getLogger();

    private CommandUtils() {
    }

    public static Long parseToLong(String value) {
        return parseToLong(value, null);
    }

    public static List<Long> parseToLongList(List<String> values) {
        return parseToLongList(values, null);
    }

    public static Long parseToLong(String value, Long defaultValue) {
        Long result = defaultValue;
        try {
            result = Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return result;
    }

    public static List<Long> parseToLongList(List<String> values, Long defaultValue) {
        return values.stream()
                .map(value -> parseToLong(value, defaultValue))
                .collect(Collectors.toList());
    }

    public static Category parseCategory(Context context) {
        Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L);
        String uaTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_UA)).orElse("");
        String enTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_EN)).orElse("");
        String ruTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_RU)).orElse("");

        StringI18N title = new StringI18N();
        title.add(getByCode(UA_CODE), uaTitleString);
        title.add(getByCode(EN_CODE), enTitleString);
        title.add(getByCode(RU_CODE), ruTitleString);

        return new Category(id, title);
    }

    public static Title parseTitle(Context context) {
        return new Title(
                StringI18N.builder()
                        .add(getByCode(UA_CODE), ofNullable(context.getRequestParameter(Attribute.TITLE_UA)).orElse(""))
                        .add(getByCode(EN_CODE), ofNullable(context.getRequestParameter(Attribute.TITLE_EN)).orElse(""))
                        .add(getByCode(RU_CODE), ofNullable(context.getRequestParameter(Attribute.TITLE_RU)).orElse(""))
                        .build());
    }

    public static Description parseDescription(Context context) {
        return new Description(
                StringI18N.builder()
                        .add(getByCode(UA_CODE), ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_UA)).orElse(""))
                        .add(getByCode(EN_CODE), ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_EN)).orElse(""))
                        .add(getByCode(RU_CODE), ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_RU)).orElse(""))
                        .build());
    }

    public static Price parsePrice(Context context) {
        Double value = -1d;
        try {
            value = Double.valueOf(context.getRequestParameter(Attribute.PRICE));
        } catch (NumberFormatException e) {
            LOG.error("Price parse failed", e);
        }
        return new Price(value);
    }

    public static Password parsePassword(Context context) {
        return ofNullable(context.getRequestParameter(Attribute.PASSWORD))
                .map(Password::createPasswordFromString)
                .orElse(Password.createPasswordFromString(""));
    }

    public static EmailAddress parseEmail(Context context) {
        return ofNullable(context.getRequestParameter(Attribute.EMAIL))
                .map(EmailAddress::new)
                .orElse(new EmailAddress(""));
    }

    public static FirstName parseFirstName(Context context) {
        return ofNullable(context.getRequestParameter(Attribute.FIRST_NAME))
                .map(FirstName::new)
                .orElse(new FirstName(""));
    }

    public static LastName parsLastName(Context context) {
        return ofNullable(context.getRequestParameter(Attribute.LAST_NAME))
                .map(LastName::new)
                .orElse(new LastName(""));
    }

    public static User parseUser(Context context) {
        return new User(new FullName(parseFirstName(context), parsLastName(context)),
                parseEmail(context),
                parsePassword(context),
                UserRole.CLIENT);
    }

    public static OrderBucket getOrderBucketFromSession(Context context) {
        return ofNullable(context.getSessionAttribute(OrderBucket.class, Attribute.ORDER_BUCKET))
                .orElseGet(OrderBucket::new);
    }

    public static Pageable parsePageable(Context context) {
        Integer pageNum = 1;
        try {
            Integer value = Math.abs(Integer.valueOf(context.getRequestParameter(Attribute.PAGE_NUM)));
            if (value > 0) {
                pageNum = value;
            }
        } catch (NumberFormatException | NullPointerException e) {
            LOG.error("Page num parse error ", e);
        }
        return new Pageable(pageNum);
    }
}