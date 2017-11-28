package net.lebedko.entity.order;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Item;

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
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
