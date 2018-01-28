package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderItemDao;
import net.lebedko.dao.jdbc.mapper.OrderItemMapper;
import net.lebedko.dao.jdbc.mapper.OrderMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcOrderItemDao implements OrderItemDao {
    private static final String INSERT = QUERIES.getProperty("orderItem.insert");
    private static final String FIND_BY_ID = QUERIES.getProperty("orderItem.findById");
    private static final String UPDATE = QUERIES.getProperty("orderItem.update");
    private static final String DELETE = QUERIES.getProperty("orderItem.delete");
    private static final String DELETE_BY_ORDER = QUERIES.getProperty("orderItem.deleteByOrder");
    private static final String GET_BY_ORDER = QUERIES.getProperty("orderItem.getByOrder");
    private static final String GET_BY_INVOICE = QUERIES.getProperty("orderItem.getByInvoice");

    private final QueryTemplate template;

    public JdbcOrderItemDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public OrderItem insert(OrderItem item) {
        Integer id = template.insertAndReturnKey(INSERT, new Object[]{
                item.getOrder().getId(),
                item.getItem().getId(),
                item.getQuantity()});
        return new OrderItem(id.longValue(), item.getOrder(), item.getItem(), item.getQuantity());
    }

    @Override
    public Collection<OrderItem> getByOrder(Order order) {
        return template.queryAll(GET_BY_ORDER,
                new Object[]{order.getId()},
                new OrderItemMapper(order));
    }

    @Override
    public void update(OrderItem item) {
        template.update(UPDATE,
                item.getOrder().getId(),
                item.getItem().getId(),
                item.getQuantity(),
                item.getId());
    }


    @Override
    public Collection<OrderItem> getByInvoice(Invoice invoice) {
        return template.queryAll(GET_BY_INVOICE,
                new Object[]{invoice.getId()},
                new OrderItemMapper(new OrderMapper(invoice)));
    }

    @Override
    public void deleteByOrder(Order order) {
        template.update(DELETE_BY_ORDER, order.getId());
    }

    @Override
    public void update(Collection<OrderItem> items) {
        List<Object[]> params = items.stream()
                .map(oi -> new Object[]{
                        oi.getOrder().getId(),
                        oi.getItem().getId(),
                        oi.getQuantity(),
                        oi.getId()})
                .collect(Collectors.toList());
        template.updateButch(UPDATE, params);
    }

    @Override
    public void delete(Collection<OrderItem> items) {
        List<Object[]> params = items.stream()
                .map(oi -> new Object[]{oi.getId()})
                .collect(Collectors.toList());
        template.updateButch(DELETE, params);
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }

    @Override
    public OrderItem findById(Long id) {
        return template.queryOne(FIND_BY_ID,
                new Object[]{id},
                new OrderItemMapper());
    }
}