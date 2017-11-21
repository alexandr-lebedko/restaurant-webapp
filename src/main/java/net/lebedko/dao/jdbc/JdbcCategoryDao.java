package net.lebedko.dao.jdbc;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.CategoryMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.item.Category;

import java.util.*;

import static net.lebedko.i18n.SupportedLocales.*;


/**
 * alexandr.lebedko : 03.08.2017.
 */
public class JdbcCategoryDao extends AbstractJdbcDao implements CategoryDao {
    private static final String INSERT = QUERIES.getProperty("category.insert");
    private static final String GET_ALL = QUERIES.getProperty("category.getAll");
    private static final String GET_BY_ID = QUERIES.getProperty("category.getById");

    private static final CategoryMapper MAPPER = new CategoryMapper();


    public JdbcCategoryDao(QueryTemplate template) {
        super(template);
    }


    public Category insert(Category category) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.getTitle().get(UA_CODE));
        params.put(2, category.getTitle().get(EN_CODE));
        params.put(3, category.getTitle().get(RU_CODE));

        Long id = template.insertAndReturnKey(INSERT, params);
        category.setId(id);

        return category;
    }

    @Override
    public Collection<Category> getAll() throws DataAccessException {
        return template.queryAll(GET_ALL, MAPPER);
    }

}
