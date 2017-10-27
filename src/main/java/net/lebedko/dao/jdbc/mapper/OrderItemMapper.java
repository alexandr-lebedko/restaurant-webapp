package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements Mapper<OrderItem> {
    private static final String ID = "oi_id";
    private static final String ORDER_ID = "oi_order";
    private static final String ITEM_ID = "oi_item";
    private static final String AMOUNT = "oi_item_number";

    private ItemMapper itemMapper;
    private Order order;

    public OrderItemMapper(Order order) {
        this(order, new ItemMapper());
    }

    public OrderItemMapper(Order order, ItemMapper mapper) {
        this.order = order;
        this.itemMapper = mapper;
    }

    @Override
    public OrderItem map(ResultSet rs) throws SQLException {
        return new OrderItem(
                getId(rs),
                order,
                getItem(rs),
                getAmount(rs));
    }

    private Long getId(ResultSet rs) throws SQLException {
        return rs.getLong(ID);
    }

    private Item getItem(ResultSet rs) throws SQLException {
        return itemMapper.map(rs);
    }

    private Integer getAmount(ResultSet rs) throws SQLException {
        return rs.getInt(AMOUNT);
    }
}
