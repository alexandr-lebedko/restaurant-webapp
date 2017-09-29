package net.lebedko.dao.jdbc;

import net.lebedko.dao.ItemDao;
import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderContent;
import net.lebedko.entity.order.OrderInfo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class JdbcOrderDao implements OrderDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String INSERT_ORDER_INFO = props.getProperty("order.insertOrderInfo");
    private static final String INSERT_ORDER_CONTENT = props.getProperty("order.insertOrderContent");

    private QueryTemplate template;
    private ItemDao itemDao;

    public JdbcOrderDao(QueryTemplate template, ItemDao itemDao) {
        this.template = template;
        this.itemDao = itemDao;
    }

    @Override
    public Order insert(Order order) throws DataAccessException {
        int id = insertInfo(order.getInfo());
        insertContent(id, order.getContent());

        return new Order(id, order.getInfo(), order.getContent());
    }

    private int insertInfo(OrderInfo info) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, info.getState().name());
        params.put(2, Timestamp.valueOf(info.getCreation()));
        return template.insertAndReturnKey(INSERT_ORDER_INFO, params);
    }

    private void insertContent(int orderId, OrderContent content) throws DataAccessException {
        template.insertBatch(
                INSERT_ORDER_CONTENT,
                content.getMap().entrySet(),
                (entry) -> {
                    Map<Integer, Object> params = new HashMap<>();
                    params.put(1, orderId);
                    params.put(2, entry.getKey().getId());
                    params.put(3, entry.getValue());
                    return params;
                });
    }

}
