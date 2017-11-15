package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.InvoiceMapper;
import net.lebedko.dao.jdbc.mapper.ItemMapper;
import net.lebedko.dao.jdbc.mapper.OrderItemMapper;
import net.lebedko.dao.jdbc.mapper.OrderMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.State;
import net.lebedko.entity.user.User;

import java.sql.Timestamp;
import java.util.*;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class JdbcOrderDao extends AbstractJdbcDao implements OrderDao {
    private static final String INSERT_ORDER = QUERIES.getProperty("order.insertOrder");
    private static final String INSERT_ORDER_ITEM = QUERIES.getProperty("order.insertOrderItem");
    private static final String GET_BY_INVOICE = QUERIES.getProperty("order.getByInvoiceId");
    private static final String GET_BY_INVOICE_AND_STATE = QUERIES.getProperty("order.getByInvoiceIdAndState");
    private static final String GET_BY_STATE = QUERIES.getProperty("order.getByState");
    private static final String GET_BY_USER = QUERIES.getProperty("order.getByUser");
    private static final String GET_BY_ID_AND_USER = QUERIES.getProperty("order.getByOrderAndUser");
    private static final String GET_ORDER_ITEMS_BY_ORDER = QUERIES.getProperty("order.getOrderItemsByOrder");
    private static final String GET_ORDER_ITEMS_BY_INVOICE = QUERIES.getProperty("order.getOrderItemsByInvoice");

    private static final String GET_BY_ID = QUERIES.getProperty("order.getById");
    private static final String UPDATE = QUERIES.getProperty("order.update");
    private static final String DELETE = QUERIES.getProperty("order.delete");

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

    @Override
    public Collection<Order> get(Invoice invoice) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());

        return template.queryAll(GET_BY_INVOICE, params, new OrderMapper(invoice));
    }

    @Override
    public Collection<Order> get(Invoice invoice, State state) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());
        params.put(2, state.name());

        return template.queryAll(GET_BY_INVOICE_AND_STATE, params, new OrderMapper(invoice, state));
    }

    @Override
    public Collection<Order> getByState(State state) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, state.name());

        return template.queryAll(GET_BY_STATE, params, new OrderMapper(state));
    }

    @Override
    public Collection<Order> getByUser(User user) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());

        return template.queryAll(GET_BY_USER, params, new OrderMapper(new InvoiceMapper(user)));
    }

    @Override
    public Order getByOrderIdAndUser(Long id, User user) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        params.put(2, user.getId());

        return template.queryOne(GET_BY_ID_AND_USER, params, new OrderMapper(new InvoiceMapper(user)));
    }

    @Override
    public Collection<OrderItem> getByOrder(Order order) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getId());

        return template.queryAll(GET_ORDER_ITEMS_BY_ORDER, params, new OrderItemMapper(order));
    }

    @Override
    public Order getById(Long id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        return template.queryOne(GET_BY_ID, params, new OrderMapper(new InvoiceMapper()));
    }

    @Override
    public Collection<OrderItem> getOrderItemsByInvoice(Invoice invoice) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());


        return template.queryAll(
                GET_ORDER_ITEMS_BY_INVOICE,
                params,
                new OrderItemMapper(new ItemMapper(), new OrderMapper(invoice), null)
        );
    }

    @Override
    public void update(Order order) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getInvoice().getId());
        params.put(2, order.getState().name());
        params.put(3, order.getCreatedOn());
        params.put(4, order.getId());

        template.update(UPDATE, params);
    }

    @Override
    public void delete(Order order) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getId());

        template.update(DELETE, params);
    }
}

