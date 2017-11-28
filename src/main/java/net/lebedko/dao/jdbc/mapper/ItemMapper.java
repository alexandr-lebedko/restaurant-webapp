package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.nonNull;
import static net.lebedko.util.SupportedLocales.*;

public class ItemMapper implements Mapper<Item> {

    private static final String ID = "i_id";
    private static final String UKR_TITLE = "i_ukr_title";
    private static final String EN_TITLE = "i_en_title";
    private static final String RU_TITLE = "i_ru_title";
    private static final String PRICE = "i_price";
    private static final String UKR_DESCRIPTION = "i_ukr_description";
    private static final String EN_DESCRIPTION = "i_en_description";
    private static final String RU_DESCRIPTION = "i_ru_description";
    private static final String IMAGE_ID = "i_image_id";

    private Category category;
    private CategoryMapper categoryMapper;

    public ItemMapper(Category category) {
        this.category = category;
    }

    public ItemMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ItemMapper() {
        this(new CategoryMapper());
    }

    @Override
    public Item map(ResultSet rs) throws SQLException {
        return new Item(mapId(rs), mapTitle(rs), mapDescription(rs), mapCategory(rs), mapPrice(rs), mapImageId(rs));
    }

    private String mapImageId(ResultSet rs) throws SQLException {
        return rs.getString(IMAGE_ID);
    }

    private Long mapId(ResultSet rs) throws SQLException {
        return rs.getLong(ID);
    }

    private Price mapPrice(ResultSet rs) throws SQLException {
        return new Price(rs.getDouble(PRICE));
    }

    private Title mapTitle(ResultSet rs) throws SQLException {
        Map<Locale, String> titleByLocale = new HashMap<>();
        titleByLocale.put(getByCode(UA_CODE), rs.getString(UKR_TITLE));
        titleByLocale.put(getByCode(EN_CODE), rs.getString(EN_TITLE));
        titleByLocale.put(getByCode(RU_CODE), rs.getString(RU_TITLE));

        return new Title(new StringI18N(titleByLocale));
    }

    private Description mapDescription(ResultSet rs) throws SQLException {
        Map<Locale, String> descriptionByLocale = new HashMap<>();
        descriptionByLocale.put(getByCode(UA_CODE), rs.getString(UKR_DESCRIPTION));
        descriptionByLocale.put(getByCode(EN_CODE), rs.getString(EN_DESCRIPTION));
        descriptionByLocale.put(getByCode(RU_CODE), rs.getString(RU_DESCRIPTION));

        return new Description(new StringI18N(descriptionByLocale));
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        if (nonNull(category)) {
            return category;
        } else {
            return categoryMapper.map(rs);
        }
    }

}
