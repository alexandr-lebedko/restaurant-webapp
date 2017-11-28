package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

//TODO: refactor class with Builder pattern!!!!!!
public class OrderItemMapper implements Mapper<OrderItem> {
    private static final String ID = "oi_id";
    private static final String ORDER_ID = "oi_order";
    private static final String ITEM_ID = "oi_item";
    private static final String AMOUNT = "oi_item_number";

    private ItemMapper itemMapper = new ItemMapper();
    private OrderMapper orderMapper;
    private Order order;

    public OrderItemMapper(Order order) {
        this();
        this.order = order;
    }

    public OrderItemMapper(OrderMapper orderMapper) {
        this(new ItemMapper(), orderMapper);
    }

    public OrderItemMapper() {
        this(new ItemMapper(), new OrderMapper());
    }

    public OrderItemMapper(ItemMapper itemMapper, OrderMapper orderMapper) {
        this.itemMapper = itemMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderItem map(ResultSet rs) throws SQLException {
        return new OrderItem(
                getId(rs),
                getOrder(rs),
                getItem(rs),
                getAmount(rs));
    }

    private Long getId(ResultSet rs) throws SQLException {
        return rs.getLong(ID);
    }

    private Item getItem(ResultSet rs) throws SQLException {
        return itemMapper.map(rs);
    }

    private Long getAmount(ResultSet rs) throws SQLException {
        return rs.getLong(AMOUNT);
    }

    private Order getOrder(ResultSet rs) throws SQLException {
        return nonNull(order) ? order : orderMapper.map(rs);
    }
}
