package net.lebedko.dao.jdbc;

import net.lebedko.dao.ItemDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.ItemMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.Category;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

import static net.lebedko.util.SupportedLocales.EN_CODE;
import static net.lebedko.util.SupportedLocales.RU_CODE;
import static net.lebedko.util.SupportedLocales.UA_CODE;

public class JdbcItemDao implements ItemDao {
    private static final String INSERT = QUERIES.getProperty("item.insert");
    private static final String UPDATE = QUERIES.getProperty("item.update");
    private static final String DELETE = QUERIES.getProperty("item.delete");
    private static final String GET_BY_CATEGORY = QUERIES.getProperty("item.getByCategory");
    private static final String GET_BY_ID = QUERIES.getProperty("item.getById");

    private final QueryTemplate template;

    public JdbcItemDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public Item insert(Item item) {
        Integer id = template.insertAndReturnKey(INSERT, new Object[]{
                item.getTitle().getValue().get(UA_CODE),
                item.getTitle().getValue().get(EN_CODE),
                item.getTitle().getValue().get(RU_CODE),
                item.getDescription().getValue().get(UA_CODE),
                item.getDescription().getValue().get(EN_CODE),
                item.getDescription().getValue().get(RU_CODE),
                item.getPrice().getValue(),
                item.getCategory().getId(),
                item.getImageId()
        });

        item.setId(id.longValue());

        return item;
    }

    @Override
    public void update(Item item) {
        template.update(UPDATE,
                item.getTitle().getValue().get(UA_CODE),
                item.getTitle().getValue().get(EN_CODE),
                item.getTitle().getValue().get(RU_CODE),
                item.getDescription().getValue().get(UA_CODE),
                item.getDescription().getValue().get(EN_CODE),
                item.getDescription().getValue().get(RU_CODE),
                item.getPrice().getValue(),
                item.getCategory().getId(),
                item.getImageId(),
                item.getId());
    }

    @Override
    public Collection<Item> getByCategory(Category category) {
        return template.queryAll(GET_BY_CATEGORY,
                new Object[]{category.getId()},
                new ItemMapper(category));
    }

    @Override
    public Item findById(Long id) {
        return template.queryOne(GET_BY_ID,
                new Object[]{id},
                new ItemMapper());
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }
}
