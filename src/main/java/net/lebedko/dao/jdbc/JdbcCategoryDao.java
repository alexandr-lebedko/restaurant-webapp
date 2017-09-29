package net.lebedko.dao.jdbc;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class JdbcCategoryDao implements CategoryDao {
    private static final String CATEGORY_ID = "c_id";
    private static final String IMAGE_ID = "c_image_id";
    private static final String UKR_TITLE = "c_ukr_title";
    private static final String EN_TITLE = "c_en_title";
    private static final String RU_TITLE = "c_ru_title";


    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String INSERT = props.getProperty("category.insert");
    private static final String GET_ALL = props.getProperty("category.getAll");
    private static final String GET_BY_ID = props.getProperty("category.getById");
    private static final CategoryMapper MAPPER = new CategoryMapper();

    private QueryTemplate template;

    public JdbcCategoryDao(QueryTemplate template) {
        this.template = template;
    }

    public Category insert(Category category) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.getValue().get(UA_CODE));
        params.put(2, category.getValue().get(EN_CODE));
        params.put(3, category.getValue().get(RU_CODE));
        params.put(4, category.getImageId());

        int id = template.insertAndReturnKey(INSERT, params);
        category.setId(id);

        return category;
    }

    @Override
    public Collection<Category> getAll() throws DataAccessException {
        return template.queryAll(GET_ALL, MAPPER);
    }

    @Override
    public Category getById(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(GET_BY_ID, params, MAPPER);
    }

    private static class CategoryMapper implements Mapper<Category> {

        @Override
        public Category map(ResultSet rs) throws SQLException {
            final int id = rs.getInt(CATEGORY_ID);

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
}
