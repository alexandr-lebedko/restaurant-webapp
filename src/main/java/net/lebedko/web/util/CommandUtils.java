package net.lebedko.web.util;

import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.i18n.SupportedLocales;
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
}
