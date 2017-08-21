package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.demo.general.StringI18N;
import net.lebedko.entity.demo.item.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class JdbcCategoryDao implements CategoryDao {
    private static final String CATEGORY_ID = "c_id";
    private static final String IMAGE_ID = "c_image_id";
    private static final String UKR_LOCALIZED_TITLE = "c_ukr_title";
    private static final String EN_LOCALIZED_TITLE = "c_en_title";
    private static final String RU_LOCALIZED_TITLE = "c_ru_title";

    private static final Locale UKR = new Locale("ukr");
    private static final Locale EN = Locale.ENGLISH;
    private static final Locale RU = new Locale("ru");

    private static final Properties PROPS = loadProperties("sql-queries.properties");
    private static final String INSERT = PROPS.getProperty("category.insert");
    private static final String GET_ALL = PROPS.getProperty("category.getAll");

    private static final CategoryMapper MAPPER = new CategoryMapper();

    private QueryTemplate template;

    public JdbcCategoryDao(QueryTemplate template) {
        this.template = template;
    }

    public Category insert(Category category) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.getValue().get(UKR));
        params.put(2, category.getValue().get(EN));
        params.put(3, category.getValue().get(RU));
        params.put(4, category.getImageId());

        int id = template.insertAndReturnKey(INSERT, params);
        category.setId(id);

        return category;
    }

    @Override
    public Collection<Category> getAll() throws DataAccessException {
        return template.queryAll(GET_ALL, MAPPER);
    }

    private static class CategoryMapper implements Mapper<Category> {

        @Override
        public Category map(ResultSet rs) throws SQLException {
            final int id = rs.getInt(CATEGORY_ID);

            final String imageId = rs.getString(IMAGE_ID);

            final String ukrTitle = rs.getString(UKR_LOCALIZED_TITLE);
            final String enTitle = rs.getString(EN_LOCALIZED_TITLE);
            final String ruTitle = rs.getString(RU_LOCALIZED_TITLE);

            final StringI18N title = new StringI18N();
            title.add(UKR, ukrTitle);
            title.add(EN, enTitle);
            title.add(UKR, ruTitle);

            return new Category(id, title, imageId);
        }
    }
}
