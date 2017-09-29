package net.lebedko.entity.order;

import net.lebedko.entity.Validatable;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class Order implements Validatable {

    private long id;

    private OrderInfo info;
    private OrderContent content;

    public Order(OrderInfo info, OrderContent content) {
        this(0, info, content);
    }

    public Order(long id, OrderInfo info, OrderContent content) {
        this.id = id;
        this.info = info;
        this.content = content;
    }

    @Override
    public boolean isValid() {
        return content.isValid();
    }

    public long getId() {
        return id;
    }

    public OrderInfo getInfo() {
        return info;
    }

    public OrderContent getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", info=" + info +
                ", content=" + content +
                '}';
    }
}
