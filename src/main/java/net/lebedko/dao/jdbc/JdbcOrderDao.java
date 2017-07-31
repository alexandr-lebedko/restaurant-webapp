package net.lebedko.dao.jdbc;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.menu.MenuItem;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static net.lebedko.dao.jdbc.JdbcMenuDao.MenuMapper;
import static net.lebedko.dao.jdbc.JdbcUserDao.UserMapper;
import static net.lebedko.entity.order.Order.OrderStatus;
import static net.lebedko.util.PropertyUtil.loadProperties;
import static net.lebedko.util.Util.checkValidity;

/**
 * alexandr.lebedko : 30.04.2017.
 */

public class JdbcOrderDao implements OrderDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String insertOrder = props.getProperty("order.insert");
    private static final String updateOrder = props.getProperty("order.update");
    private static final String deleteOrder = props.getProperty("order.delete");
    private static final String findOrderById = props.getProperty("order.findById");
    private static final String findOrderByStatus = props.getProperty("order.findOrdersByStatus");
    private static final String insertOrderItem = props.getProperty("order.insertOrderItem");
    private static final String deleteOrderItem = props.getProperty("order.deleteOrderItem");
    private static final String updateOrderItem = props.getProperty("order.updateOrderItem");
    private static final String findOrderItemsByOrder = props.getProperty("order.findOrderItemsByOrderId");

    private OrderRowMapper orderMapper = new OrderRowMapper();
    private QueryTemplate template;

    public JdbcOrderDao(QueryTemplate template) {
        this.template = requireNonNull(template);
    }

    @Override
    public Order insert(Order order) throws DataAccessException{
        checkValidity(order);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getUserId());
        params.put(2, order.getOrderStatus().name());
        int pk = template.insertAndReturnKey(insertOrder, params);
        order.setId(pk);

        return order;
    }

    @Override
    public void update(Order order) throws DataAccessException{
        checkValidity(order);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getUserId());
        params.put(2, order.getOrderStatus().name());
        params.put(3, order.getId());

        template.update(updateOrder, params);
    }

    @Override
    public void delete(int id) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(deleteOrder, params);
    }


    @Override
    public Order findById(int id) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(findOrderById, params, orderMapper);
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, orderStatus.name());
        return new ArrayList<>(template.queryAll(findOrderByStatus, params, orderMapper));
    }

    @Override
    public OrderItem insertOrderItem(OrderItem orderItem) throws DataAccessException{
        checkValidity(orderItem);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, orderItem.getOrder().getId());
        params.put(2, orderItem.isProcessed());
        params.put(3, orderItem.getMenuItem().getId());
        params.put(4, orderItem.getQuantity());

        int pk = template.insertAndReturnKey(insertOrderItem, params);
        orderItem.setId(pk);

        return orderItem;
    }

    @Override
    public void deleteOrderItem(int id) throws DataAccessException{
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(deleteOrderItem, params);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) throws DataAccessException{
        checkValidity(orderItem);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, orderItem.getId());
        params.put(2, orderItem.getOrderId());
        params.put(3, orderItem.getMenuItemId());
        params.put(4, orderItem.getQuantity());
        params.put(5, orderItem.isProcessed());
        template.updateProcedure(updateOrderItem, params);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrder(Order order) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, order.getId());
        return new ArrayList<>(template.queryAll(findOrderItemsByOrder, params, new OrderItemMapper(order)));
    }


    public static final class OrderRowMapper implements Mapper<Order> {
        private UserMapper userMapper = new UserMapper();

        @Override
        public Order map(ResultSet rs) throws SQLException {
            int id = rs.getInt("o_id");
            OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("o_status"));
            User user = userMapper.map(rs);

            return new Order(id, user, orderStatus);
        }
    }

    public static final class OrderItemMapper implements Mapper<OrderItem> {
        private MenuMapper menuMapper = new MenuMapper();
        private Order order;

        public OrderItemMapper(Order order) {
            this.order = order;
        }

        @Override
        public OrderItem map(ResultSet rs) throws SQLException {
            int id = rs.getInt("oi_id");
            int quantity = rs.getInt("oi_quantity");
            boolean processed = rs.getBoolean("oi_processed");
            MenuItem menuItem = menuMapper.map(rs);

            return new OrderItem(id, order, menuItem, quantity, processed);
        }
    }
}
