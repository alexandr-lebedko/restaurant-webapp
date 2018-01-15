package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        return new Item(
                rs.getLong(ID),
                mapTitle(rs),
                mapDescription(rs),
                mapCategory(rs),
                new Price(rs.getDouble(PRICE)),
                rs.getString(IMAGE_ID));
    }

    private Title mapTitle(ResultSet rs) throws SQLException {
        return new Title(
                StringI18N.builder()
                        .add(getByCode(UA_CODE), rs.getString(UKR_TITLE))
                        .add(getByCode(EN_CODE), rs.getString(EN_TITLE))
                        .add(getByCode(RU_CODE), rs.getString(RU_TITLE))
                        .build()
        );
    }

    private Description mapDescription(ResultSet rs) throws SQLException {
        return new Description(
                StringI18N.builder()
                        .add(getByCode(UA_CODE), rs.getString(UKR_DESCRIPTION))
                        .add(getByCode(EN_CODE), rs.getString(EN_DESCRIPTION))
                        .add(getByCode(RU_CODE), rs.getString(RU_DESCRIPTION))
                        .build()
        );
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        if (nonNull(category)) {
            return category;
        } else {
            return categoryMapper.map(rs);
        }
    }

}
