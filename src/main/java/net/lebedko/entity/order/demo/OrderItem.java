package net.lebedko.entity.order.demo;

import net.lebedko.entity.item.Item;

/**
 * alexandr.lebedko : 30.09.2017.
 */
public class OrderItem {
    private Long id;
    private Order order;
    private Item item;
    private Integer quantity;

    public OrderItem(Order order, Item item, Integer quantity) {
        this(null, order, item, quantity);
    }

    public OrderItem(Long id, Order order, Item item, Integer quantity) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
