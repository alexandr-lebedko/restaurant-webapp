package net.lebedko.dao.jdbc;

import net.lebedko.dao.DishDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.template.Mapper;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Description;
import net.lebedko.entity.general.Title;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static net.lebedko.entity.dish.Dish.DishCategory;
import static net.lebedko.util.PropertyUtil.loadProperties;
import static net.lebedko.util.Util.checkValidity;

/**
 * alexandr.lebedko : 27.04.2017.
 */

public class JdbcDishDao implements DishDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String insert = props.getProperty("dish.insert");
    private static final String update = props.getProperty("dish.update");
    private static final String delete = props.getProperty("dish.delete");
    private static final String findById = props.getProperty("dish.getById");

    private DishMapper mapper = new DishMapper();
    private QueryTemplate template;

    public JdbcDishDao(QueryTemplate template) {
        this.template = Objects.requireNonNull(template, "Template cannot be null!");
    }

    @Override
    public Dish insert(Dish dish) throws DataAccessException {
        checkValidity(dish);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, dish.getCategory().name());
        params.put(2, dish.getTitle().toString());
        params.put(3, dish.getDescription().toString());
        int pk = template.insertAndReturnKey(insert, params);
        dish.setId(pk);
        return dish;
    }

    @Override
    public Dish findById(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(findById, params, mapper);
    }

    @Override
    public void update(Dish dish) throws DataAccessException {
        checkValidity(dish);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, dish.getCategory().name());
        params.put(2, dish.getTitle().toString());
        params.put(3, dish.getDescription().toString());
        params.put(4, dish.getId());

        template.update(update, params);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(delete, params);
    }


    public static final class DishMapper implements Mapper<Dish> {

        @Override
        public Dish map(ResultSet rs) throws SQLException {
            int id = rs.getInt("d_id");
            DishCategory category = DishCategory.valueOf(rs.getString("d_category"));
            Title title = new Title(rs.getString("d_title"));
            Description description = new Description(rs.getString("d_description"));

            return new Dish(id, title, description, category);
        }
    }
}
