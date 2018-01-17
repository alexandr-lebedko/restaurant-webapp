package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.jdbc.mapper.InvoiceMapper;
import net.lebedko.dao.jdbc.mapper.OrderMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class JdbcOrderDao implements OrderDao {
    private static final String INSERT_ORDER = QUERIES.getProperty("order.insertOrder");
    private static final String GET_BY_INVOICE = QUERIES.getProperty("order.getByInvoiceId");
    private static final String GET_BY_STATE = QUERIES.getProperty("order.getByState");
    private static final String GET_BY_ID_AND_USER = QUERIES.getProperty("order.getByOrderAndUser");
    private static final String GET_BY_ID = QUERIES.getProperty("order.getById");
    private static final String UPDATE = QUERIES.getProperty("order.update");
    private static final String DELETE = QUERIES.getProperty("order.delete");
    private static final String GET_PAGED_BY_USER = QUERIES.getProperty("order.getPagedByUser");
    private static final String GET_PAGED_BY_STATE = QUERIES.getProperty("order.getPagedByState");
    private static final String COUNT_BY_USER = QUERIES.getProperty("order.countByUser");
    private static final String COUNT_BY_STATE = QUERIES.getProperty("order.countByState");

    private final QueryTemplate template;

    public JdbcOrderDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public Order insert(Order order) {
        Integer id = template.insertAndReturnKey(INSERT_ORDER, new Object[]{
                order.getInvoice().getId(),
                order.getState().name(),
                Timestamp.valueOf(order.getCreatedOn())});

        return new Order(id.longValue(), order.getInvoice(), order.getState(), order.getCreatedOn());
    }

    @Override
    public Collection<Order> getByInvoice(Invoice invoice) {
        return template.queryAll(GET_BY_INVOICE,
                new Object[]{invoice.getId()},
                new OrderMapper(invoice));
    }

    @Override
    public Collection<Order> getByState(OrderState state) {
        return template.queryAll(GET_BY_STATE,
                new Object[]{state.name()},
                new OrderMapper());
    }

    @Override
    public Page<Order> getByState(OrderState state, Pageable pageable) {
        final Collection<Order> orders = template.queryAll(
                GET_PAGED_BY_STATE,
                new Object[]{state.name(), pageable.getPageSize(), pageable.getOffset()},
                new OrderMapper());

        final Integer total = countOrdersByState(state);

        return new Page<>(orders, total, pageable.getPageNumber());
    }

    private Integer countOrdersByState(OrderState state) {
        return template.queryOne(
                COUNT_BY_STATE,
                new Object[]{state.name()},
                (rs) -> rs.getInt("total"));
    }

    @Override
    public Page<Order> getByUser(User user, Pageable pageable) {
        final Collection<Order> orders = template.queryAll(
                GET_PAGED_BY_USER,
                new Object[]{user.getId(), pageable.getPageSize(), pageable.getOffset()},
                new OrderMapper());

        final Integer total = countOrdersByUser(user);

        return new Page<>(orders, total, pageable.getPageNumber());
    }

    private Integer countOrdersByUser(User user) {
        return template.queryOne(
                COUNT_BY_USER,
                new Object[]{user.getId()},
                (rs) -> rs.getInt("total"));
    }

    @Override
    public Order getByOrderIdAndUser(Long id, User user) {
        return template.queryOne(GET_BY_ID_AND_USER,
                new Object[]{id, user.getId()},
                new OrderMapper(new InvoiceMapper(user)));
    }

    @Override
    public Order findById(Long id) {
        return template.queryOne(GET_BY_ID,
                new Object[]{id},
                new OrderMapper(new InvoiceMapper()));
    }

    @Override
    public void update(Order order) {
        template.update(UPDATE,
                order.getInvoice().getId(),
                order.getState().name(),
                order.getCreatedOn(),
                order.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }
}

