package net.lebedko.dao.jdbc;

import net.lebedko.dao.ItemDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.*;
import net.lebedko.util.CheckedFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class JdbcItemDao implements ItemDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String INSERT = props.getProperty("item.insert");
    private static final String GET_BY_CATEGORY = props.getProperty("item.getByCategory");
    private static final String GET_BY_ID = props.getProperty("item.getById");


    private static final String ID = "i_id";
    private static final String IMAGE_ID = "i_image_id";
    private static final String UKR_TITLE = "i_ukr_title";
    private static final String EN_TITLE = "i_en_title";
    private static final String RU_TITLE = "i_ru_title";
    private static final String UKR_DESCRIPTION = "i_ukr_description";
    private static final String EN_DESCRIPTION = "i_en_description";
    private static final String RU_DESCRIPTION = "i_ru_description";
    private static final String STATE = "i_state";
    private static final String CATEGORY = "i_category";
    private static final String PRICE = "i_price";


    private QueryTemplate template;
    private JdbcCategoryDao categoryDao;

    public JdbcItemDao(QueryTemplate template, JdbcCategoryDao categoryDao) {
        this.template = template;
        this.categoryDao = categoryDao;
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

        return template.queryAll(GET_BY_CATEGORY, params, new MenuItemMapper(category));
    }

    @Override
    public Item get(long id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        return template.queryOne(
                GET_BY_ID,
                params,
                new MenuItemMapper(categoryId -> categoryDao.getById(categoryId)));
    }

    private static final class MenuItemMapper implements Mapper<Item> {
        private CheckedFunction<Integer, Category, DataAccessException> idToCategory;

        public MenuItemMapper(CheckedFunction<Integer, Category, DataAccessException> idToCategory) {
            this.idToCategory = idToCategory;
        }

        public MenuItemMapper(Category category) {
            this.idToCategory = (id) -> category;
        }

        @Override
        public Item map(ResultSet rs) throws SQLException {
            return new Item(
                    mapId(rs),
                    mapItemInfo(rs),
                    mapState(rs),
                    mapImageId(rs)
            );
        }

        private String mapImageId(ResultSet rs) throws SQLException {
            return rs.getString(IMAGE_ID);
        }

        private int mapId(ResultSet rs) throws SQLException {
            return rs.getInt(ID);
        }

        private ItemInfo mapItemInfo(ResultSet rs) throws SQLException {
            return new ItemInfo(
                    mapTitle(rs),
                    mapDescription(rs),
                    mapCategory(rs),
                    mapPrice(rs));
        }

        private Category mapCategory(ResultSet rs) throws SQLException {
            try {
                return idToCategory.apply(rs.getInt(CATEGORY));
            } catch (DataAccessException dae) {
                throw (SQLException) dae.getCause();
            }
        }

        private Price mapPrice(ResultSet rs) throws SQLException {
            return new Price(rs.getDouble(PRICE));
        }

        private ItemState mapState(ResultSet rs) throws SQLException {
            return ItemState.valueOf(rs.getString(STATE));
        }

        private Title mapTitle(ResultSet rs) throws SQLException {
            Map<Locale, String> localesToTitle = new HashMap<>();
            localesToTitle.put(getByCode(UA_CODE), rs.getString(UKR_TITLE));
            localesToTitle.put(getByCode(EN_CODE), rs.getString(EN_TITLE));
            localesToTitle.put(getByCode(RU_CODE), rs.getString(RU_TITLE));

            return new Title(new StringI18N(localesToTitle));
        }

        private Description mapDescription(ResultSet rs) throws SQLException {
            Map<Locale, String> localesToDescription = new HashMap<>();
            localesToDescription.put(getByCode(UA_CODE), rs.getString(UKR_DESCRIPTION));
            localesToDescription.put(getByCode(EN_CODE), rs.getString(EN_DESCRIPTION));
            localesToDescription.put(getByCode(RU_CODE), rs.getString(RU_DESCRIPTION));

            return new Description(new StringI18N(localesToDescription));
        }
    }


}
