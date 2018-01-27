package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.lebedko.util.SupportedLocales.UA_CODE;
import static net.lebedko.util.SupportedLocales.RU_CODE;
import static net.lebedko.util.SupportedLocales.EN_CODE;
import static net.lebedko.util.SupportedLocales.getByCode;

public class CategoryMapper implements Mapper<Category> {
    private static final String CATEGORY_ID = "c_id";
    private static final String UKR_TITLE = "c_ukr_title";
    private static final String EN_TITLE = "c_en_title";
    private static final String RU_TITLE = "c_ru_title";

    @Override
    public Category map(ResultSet rs) throws SQLException {
        return new Category(rs.getLong(CATEGORY_ID),
                StringI18N.builder()
                        .add(getByCode(UA_CODE), rs.getString(UKR_TITLE))
                        .add(getByCode(EN_CODE), rs.getString(EN_TITLE))
                        .add(getByCode(RU_CODE), rs.getString(RU_TITLE))
                        .build());
    }
}
