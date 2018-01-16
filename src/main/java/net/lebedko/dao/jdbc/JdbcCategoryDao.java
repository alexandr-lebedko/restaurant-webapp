package net.lebedko.dao.jdbc;

import java.util.Collection;

import static net.lebedko.util.SupportedLocales.EN_CODE;
import static net.lebedko.util.SupportedLocales.UA_CODE;
import static net.lebedko.util.SupportedLocales.RU_CODE;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.jdbc.mapper.CategoryMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.item.Category;
import net.lebedko.util.SupportedLocales;

public class JdbcCategoryDao implements CategoryDao {
    private static final String INSERT = QUERIES.getProperty("category.insert");
    private static final String GET_ALL = QUERIES.getProperty("category.getAll");
    private static final String UPDATE = QUERIES.getProperty("category.update");
    private static final String GET_BY_ID = QUERIES.getProperty("category.getById");
    private static final String DELETE = QUERIES.getProperty("category.delete");
    private static final CategoryMapper MAPPER = new CategoryMapper();

    private final QueryTemplate template;

    public JdbcCategoryDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public Category insert(Category category) {
        Integer id = template.insertAndReturnKey(INSERT, new Object[]{
                category.getTitle().get(UA_CODE),
                category.getTitle().get(EN_CODE),
                category.getTitle().get(RU_CODE)});

        category.setId(id.longValue());

        return category;
    }

    @Override
    public void update(Category category) {
        template.update(UPDATE,
                category.getTitle().get(SupportedLocales.UA_CODE),
                category.getTitle().get(SupportedLocales.EN_CODE),
                category.getTitle().get(SupportedLocales.RU_CODE),
                category.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }

    @Override
    public Category findById(Long id) {
        return template.queryOne(GET_BY_ID,
                new Object[]{id},
                MAPPER);
    }

    @Override
    public Collection<Category> getAll() {
        return template.queryAll(GET_ALL, MAPPER);
    }

}
