package net.lebedko.dao.jdbc;

import net.lebedko.dao.MenuDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.menu.Menu;
import net.lebedko.entity.menu.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.Objects.requireNonNull;
import static net.lebedko.dao.jdbc.JdbcDishDao.DishMapper;
import static net.lebedko.util.PropertyUtil.loadProperties;
import static net.lebedko.util.Util.checkValidity;

/**
 * alexandr.lebedko : 28.04.2017.
 */

public class JdbcMenuDao implements MenuDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String insert = props.getProperty("menu.insert");
    private static final String update = props.getProperty("menu.update");
    private static final String delete = props.getProperty("menu.delete");
    private static final String getById = props.getProperty("menu.getById");
    private static final String getByCategory = props.getProperty("menu.getByCategory");
    private static final String getAll = props.getProperty("menu.getAll");
    private static final String getByActive = props.getProperty("menu.getByActive");

    private MenuMapper mapper = new MenuMapper();
    private QueryTemplate template;

    public JdbcMenuDao(QueryTemplate template) {
        this.template = requireNonNull(template, "Template cannot be null");
    }

    @Override
    public MenuItem insert(MenuItem item) throws DataAccessException{
        checkValidity(item);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getPrice().getDoubleValue());
        params.put(2, item.isActive());
        params.put(3, item.getDish().getId());
        int pk = template.insertAndReturnKey(insert, params);
        item.setId(pk);
        return item;
    }

    @Override
    public MenuItem findById(int id) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(getById, params, mapper);
    }

    @Override
    public void update(MenuItem item) throws DataAccessException{
        checkValidity(item);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getPrice().getDoubleValue());
        params.put(2, item.isActive());
        params.put(3, item.getDish().getId());
        params.put(4, item.getId());
        template.update(update, params);
    }

    @Override
    public void delete(int id) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(delete, params);
    }

    @Override
    public Menu getAllMenu()throws DataAccessException {
        return new Menu(template.queryAll(getAll, mapper));
    }

    @Override
    public Menu getActiveMenu() throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, true);
        return new Menu(template.queryAll(getByActive, params, mapper));
    }

    @Override
    public Menu getStopListMenu() throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, false);
        return new Menu(template.queryAll(getByActive, params, mapper));
    }

    @Override
    public Menu getByCategory(Dish.DishCategory category)throws DataAccessException {
        requireNonNull(category, "Category cannot be null!");
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.name());
        return new Menu(template.queryAll(getByCategory, params, mapper));
    }

    public static class MenuMapper implements Mapper<MenuItem> {
        private DishMapper dishMapper = new DishMapper();

        @Override
        public MenuItem map(ResultSet rs) throws SQLException {
            int id = rs.getInt("m_id");
            Price price = new Price(rs.getDouble("m_price"));
            boolean active = rs.getBoolean("m_is_active");
            Dish dish = dishMapper.map(rs);

            return new MenuItem(id, dish, price, active);
        }
    }
}
