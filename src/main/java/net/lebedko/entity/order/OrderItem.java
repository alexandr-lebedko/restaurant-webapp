package net.lebedko.entity.order;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Item;

import java.util.Objects;

public class OrderItem {
    private Long id;
    private Order order;
    private Item item;
    private Long quantity;

    public OrderItem(Order order, Item item, Long quantity) {
        this(null, order, item, quantity);
    }

    public OrderItem(Long id, Order order, Item item, Long quantity) {
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

    public Long getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return new Price(item.getPrice().getValue() * quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) &&
                Objects.equals(order, orderItem.order) &&
                Objects.equals(item, orderItem.item) &&
                Objects.equals(quantity, orderItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, item, quantity);
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