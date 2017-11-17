package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.JdbcCategoryDao;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.lebedko.i18n.SupportedLocales.*;

public class CategoryMapper implements Mapper<Category> {
    private static final String CATEGORY_ID = "c_id";
    private static final String IMAGE_ID = "c_image_id";
    private static final String UKR_TITLE = "c_ukr_title";
    private static final String EN_TITLE = "c_en_title";
    private static final String RU_TITLE = "c_ru_title";


    @Override
    public Category map(ResultSet rs) throws SQLException {
        final long id = rs.getLong(CATEGORY_ID);

        final String imageId = rs.getString(IMAGE_ID);

        final String ukrTitle = rs.getString(UKR_TITLE);
        final String enTitle = rs.getString(EN_TITLE);
        final String ruTitle = rs.getString(RU_TITLE);
        final StringI18N title = new StringI18N();
        title.add(getByCode(UA_CODE), ukrTitle);
        title.add(getByCode(EN_CODE), enTitle);
        title.add(getByCode(RU_CODE), ruTitle);

        return new Category(id, title, imageId);
    }
}
