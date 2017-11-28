package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderItemDao;
import net.lebedko.dao.jdbc.mapper.OrderItemMapper;
import net.lebedko.dao.jdbc.mapper.OrderMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JdbcOrderItemDao extends AbstractJdbcDao implements OrderItemDao {
    private static final String INSERT = QUERIES.getProperty("orderItem.insert");
    private static final String UPDATE = QUERIES.getProperty("orderItem.update");
    private static final String DELETE = QUERIES.getProperty("orderItem.delete");
    private static final String GET_BY_ORDER = QUERIES.getProperty("orderItem.getByOrder");
    private static final String GET_BY_INVOICE = QUERIES.getProperty("orderItem.getByInvoice");

    public JdbcOrderItemDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public OrderItem insert(OrderItem item) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getOrder().getId());
        params.put(2, item.getItem().getId());
        params.put(3, item.getQuantity());
        Long id = template.insertAndReturnKey(INSERT, params);

        return new OrderItem(id, item.getOrder(), item.getItem(), item.getQuantity());
    }

    @Override
    public Collection<OrderItem> getByOrder(Order order) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getId());

        return template.queryAll(GET_BY_ORDER, params, new OrderItemMapper(order));
    }

    @Override
    public void update(OrderItem item) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, item.getOrder().getId());
        params.put(2, item.getItem().getId());
        params.put(3, item.getQuantity());
        params.put(4, item.getId());

        template.update(UPDATE, params);
    }

    @Override
    public void update(Collection<OrderItem> items) {
        if (!items.isEmpty()) {
            template.updateBatch(UPDATE, items, (item) -> {
                Map<Integer, Object> params = new HashMap<>();
                params.put(1, item.getOrder().getId());
                params.put(2, item.getItem().getId());
                params.put(3, item.getQuantity());
                params.put(4, item.getId());
                return params;
            });
        }
    }

    @Override
    public Collection<OrderItem> getByInvoice(Invoice invoice) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getId());

        return template.queryAll(GET_BY_INVOICE, params, new OrderItemMapper(new OrderMapper(invoice)));
    }

    @Override
    public void delete(Collection<OrderItem> items) {
        if (!items.isEmpty()) {
            template.updateBatch(DELETE, items, (item) -> {
                Map<Integer, Object> params = new HashMap<>();
                params.put(1, item.getId());
                return params;
            });
        }
    }

    @Override
    public void delete(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(DELETE, params);
    }

    @Override
    public OrderItem findById(Long id) {
        //TODO
        return null;
    }
}
