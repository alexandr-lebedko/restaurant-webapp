package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.i18n.SupportedLocales;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static net.lebedko.i18n.SupportedLocales.*;
import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class JdbcMenuItemDao implements MenuItemDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String INSERT = props.getProperty("menuItem.insert");


    private static final String ID = "mi_id";
    private static final String IMAGE_ID = "c_image_id";
    private static final String UKR_TITLE = "mi_ukr_title";
    private static final String EN_TITLE = "mi_en_title";
    private static final String RU_TITLE = "mi_ru_title";
    private static final String UKR_DESCRIPTION = "mi_ukr_description";
    private static final String EN_DESCRIPTION = "mi_en_description";
    private static final String RU_DESCRIPTION = "mi_ru_description";
    private static final String STATE = "mi_state";
    private static final String CATEGORY = "mi_category";


    private QueryTemplate template;

    public JdbcMenuItemDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public MenuItem insert(MenuItem item) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getTitle().getValue().get(UA_CODE));
        params.put(2, item.getTitle().getValue().get(EN_CODE));
        params.put(3, item.getTitle().getValue().get(RU_CODE));
        params.put(4, item.getDescription().getValue().get(UA_CODE));
        params.put(5, item.getDescription().getValue().get(EN_CODE));
        params.put(6, item.getDescription().getValue().get(RU_CODE));
        params.put(7, item.getState().name());
        params.put(8, item.getPrice().getValue());
        params.put(9, item.getPictureId());

        int id = template.insertAndReturnKey(INSERT, params);
        item.setId(id);

        return item;
    }



}
