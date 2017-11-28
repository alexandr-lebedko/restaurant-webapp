package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.jdbc.mapper.InvoiceMapper;
import net.lebedko.dao.jdbc.mapper.OrderMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class JdbcOrderDao extends AbstractJdbcDao implements OrderDao {
    private static final String INSERT_ORDER = QUERIES.getProperty("order.insertOrder");
    private static final String GET_BY_INVOICE = QUERIES.getProperty("order.getByInvoiceId");
    private static final String GET_BY_INVOICE_AND_STATE = QUERIES.getProperty("order.getByInvoiceIdAndState");
    private static final String GET_BY_STATE = QUERIES.getProperty("order.getByState");
    private static final String GET_BY_USER = QUERIES.getProperty("order.getByUser");
    private static final String GET_BY_ID_AND_USER = QUERIES.getProperty("order.getByOrderAndUser");

    private static final String GET_BY_ID = QUERIES.getProperty("order.getById");
    private static final String UPDATE = QUERIES.getProperty("order.update");
    private static final String DELETE = QUERIES.getProperty("order.delete");

    public JdbcOrderDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public Order insert(Order order) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getInvoice().getId());
        params.put(2, order.getState().name());
        params.put(3, Timestamp.valueOf(order.getCreatedOn()));

        Long id = template.insertAndReturnKey(INSERT_ORDER, params);
        return new Order(id, order.getInvoice(), order.getState(), order.getCreatedOn());
    }

    @Override
    public Collection<Order> getByInvoice(Invoice invoice) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());

        return template.queryAll(GET_BY_INVOICE, params, new OrderMapper(invoice));
    }

    @Override
    public Collection<Order> getByInvoiceAndState(Invoice invoice, OrderState state) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());
        params.put(2, state.name());

        return template.queryAll(GET_BY_INVOICE_AND_STATE, params, new OrderMapper());
    }

    @Override
    public Collection<Order> getByState(OrderState state) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, state.name());

        return template.queryAll(GET_BY_STATE, params, new OrderMapper());
    }

    @Override
    public Collection<Order> getByUser(User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());

        return template.queryAll(GET_BY_USER, params, new OrderMapper(new InvoiceMapper(user)));
    }

    @Override
    public Order getByOrderIdAndUser(Long id, User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        params.put(2, user.getId());

        return template.queryOne(GET_BY_ID_AND_USER, params, new OrderMapper(new InvoiceMapper(user)));
    }

    @Override
    public Order findById(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        return template.queryOne(GET_BY_ID, params, new OrderMapper(new InvoiceMapper()));
    }

    @Override
    public void update(Order order) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getInvoice().getId());
        params.put(2, order.getState().name());
        params.put(3, order.getCreatedOn());
        params.put(4, order.getId());

        template.update(UPDATE, params);
    }

    @Override
    public void delete(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        template.update(DELETE, params);
    }
}

