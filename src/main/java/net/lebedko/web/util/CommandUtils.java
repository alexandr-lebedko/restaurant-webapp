package net.lebedko.web.util;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Title;
import net.lebedko.entity.user.*;
import net.lebedko.util.SupportedLocales;
import net.lebedko.web.command.IContext;
import net.lebedko.web.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;


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

    public static Category parseCategory(IContext context) {
        Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.CATEGORY_ID), -1L);
        String uaTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_UA)).orElse("");
        String enTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_EN)).orElse("");
        String ruTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_RU)).orElse("");

        StringI18N title = new StringI18N();
        title.add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), uaTitleString);
        title.add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), enTitleString);
        title.add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), ruTitleString);

        return new Category(id, title);
    }

    public static Title parseTitle(IContext context) {
        String uaTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_UA)).orElse("");
        String enTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_EN)).orElse("");
        String ruTitleString = ofNullable(context.getRequestParameter(Attribute.TITLE_RU)).orElse("");

        StringI18N localizedValue = new StringI18N();
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), uaTitleString);
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), enTitleString);
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), ruTitleString);

        return new Title(localizedValue);
    }

    public static Description parseDescription(IContext context) {
        String uaDescriptionString = ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_UA)).orElse("");
        String enDescriptionString = ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_EN)).orElse("");
        String ruDescriptionString = ofNullable(context.getRequestParameter(Attribute.DESCRIPTION_RU)).orElse("");

        StringI18N localizedValue = new StringI18N();
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.UA_CODE), uaDescriptionString);
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.EN_CODE), enDescriptionString);
        localizedValue.add(SupportedLocales.getByCode(SupportedLocales.RU_CODE), ruDescriptionString);

        return new Description(localizedValue);
    }

    public static Price parsePrice(IContext context) {
        Double value = -1d;
        try {
            value = Double.valueOf(context.getRequestParameter(Attribute.PRICE));
        } catch (NumberFormatException e) {
            LOG.error("Price parse failed", e);
        }
        return new Price(value);
    }

    public static Password parsePassword(IContext context) {
        return ofNullable(context.getRequestParameter(Attribute.PASSWORD))
                .map(Password::createPasswordFromString)
                .orElse(Password.createPasswordFromString(""));
    }

    public static EmailAddress parseEmail(IContext context) {
        return ofNullable(context.getRequestParameter(Attribute.EMAIL))
                .map(EmailAddress::new)
                .orElse(new EmailAddress(""));
    }

    public static FirstName parseFirstName(IContext context) {
        return ofNullable(context.getRequestParameter(Attribute.FIRST_NAME))
                .map(FirstName::new)
                .orElse(new FirstName(""));
    }

    public static LastName parsLastName(IContext context) {
        return ofNullable(context.getRequestParameter(Attribute.LAST_NAME))
                .map(LastName::new)
                .orElse(new LastName(""));
    }

    public static User parseUser(IContext context) {
        return new User(new FullName(parseFirstName(context), parsLastName(context)),
                parseEmail(context),
                parsePassword(context),
                UserRole.CLIENT);
    }
}


