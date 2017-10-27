package net.lebedko.dao.jdbc;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.CategoryMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.item.Category;

import java.util.*;

import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class JdbcCategoryDao implements CategoryDao {
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
//TODO::remove casting
        int id = (int) template.insertAndReturnKey(INSERT, params);
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

}
