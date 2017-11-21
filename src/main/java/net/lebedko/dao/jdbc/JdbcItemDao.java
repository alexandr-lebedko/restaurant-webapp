package net.lebedko.dao.jdbc;

import net.lebedko.dao.ItemDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.CategoryMapper;
import net.lebedko.dao.jdbc.mapper.ItemMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.item.*;

import java.util.*;

import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class JdbcItemDao extends AbstractJdbcDao implements ItemDao {
    private static final String INSERT = QUERIES.getProperty("item.insert");
    private static final String GET_BY_CATEGORY = QUERIES.getProperty("item.getByCategory");
    private static final String GET_BY_ID = QUERIES.getProperty("item.getById");


    public JdbcItemDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public Item insert(Item item) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getTitle().getValue().get(UA_CODE));
        params.put(2, item.getTitle().getValue().get(EN_CODE));
        params.put(3, item.getTitle().getValue().get(RU_CODE));
        params.put(4, item.getDescription().getValue().get(UA_CODE));
        params.put(5, item.getDescription().getValue().get(EN_CODE));
        params.put(6, item.getDescription().getValue().get(RU_CODE));
        params.put(7, item.getState().name());
        params.put(8, item.getPrice().getValue());
        params.put(9, item.getCategory().getId());
        params.put(10, item.getPictureId());

        long id = template.insertAndReturnKey(INSERT, params);
        item.setId(id);

        return item;
    }

    @Override
    public Collection<Item> getByCategory(Category category) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.getId());

        return template.queryAll(GET_BY_CATEGORY, params, new ItemMapper(category));
    }

    @Override
    public Item get(long id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        return template.queryOne(
                GET_BY_ID,
                params,
                new ItemMapper(new CategoryMapper()));
    }


}
