package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.Order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class JdbcOrderDao extends AbstractJdbcDao implements OrderDao {
    private static final String INSERT_ORDER = QUERIES.getProperty("order.insertOrder");
    private static final String INSERT_ORDER_ITEM = QUERIES.getProperty("order.insertOrderItem");

    public JdbcOrderDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public OrderItem insert(OrderItem item) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getOrder().getId());
        params.put(2, item.getItem().getId());
        params.put(3, item.getQuantity());

        Long id = template.insertAndReturnKey(INSERT_ORDER_ITEM, params);

        return new OrderItem(id, item.getOrder(), item.getItem(), item.getQuantity());
    }

    @Override
    public Order insert(Order order) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getInvoice().getId());
        params.put(2, order.getState().name());
        params.put(3, Timestamp.valueOf(order.getCreatedOn()));

        Long id = template.insertAndReturnKey(INSERT_ORDER, params);

        return new Order(id, order.getInvoice(), order.getState(), order.getCreatedOn());
    }
}
