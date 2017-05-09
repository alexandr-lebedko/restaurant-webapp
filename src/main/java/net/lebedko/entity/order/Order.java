package net.lebedko.entity.order;

import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 27.04.2017.
 */

public class Order implements Validatable, Entity {

    public enum OrderStatus {
        ACTIVE, FINISHED, CLOSED
    }

    private int id;
    private User client;
    private OrderStatus orderStatus;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(User client, OrderStatus orderStatus) {
        this(0, client, orderStatus);
    }


    public Order(int id, User client, OrderStatus orderStatus) {
        this.id = id;
        this.client = requireNonNull(client, "Client cannot be null!");
        this.orderStatus = requireNonNull(orderStatus, "OrderOld Status cannot be null!");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return client;
    }

    public int getUserId() {
        return client.getId();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItem.isValid())
            orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(client, order.client) &&
                orderStatus == order.orderStatus &&
                Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, orderStatus, orderItems);
    }

    public Price countSum() {
        return orderItems.stream()
                .map(OrderItem::calculatePrice)
                .reduce(Price::sum)
                .orElse(new Price(0.00));
    }

    public boolean isValid() {
        return client.isValid();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
