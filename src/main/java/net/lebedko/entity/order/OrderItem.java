package net.lebedko.entity.order;

import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.menu.MenuItem;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 27.04.2017.
 */

public class OrderItem implements Validatable, Entity {

    private int id;
    private Order order;
    private MenuItem menuItem;
    private boolean processed;
    private int quantity;

    public OrderItem(Order order, MenuItem menuItem, int quantity, boolean processed) {
        this(0, order, menuItem, quantity, processed);
    }

    public OrderItem(int id, Order order, MenuItem menuItem, int quantity, boolean processed) {
        this.id = id;
        this.order = requireNonNull(order, "Order cannot be null!");
        this.menuItem = requireNonNull(menuItem, "Menu item cannot be null!");
        this.processed = processed;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isProcessed() {
        return processed;
    }

    public Price calculatePrice() {
        return this.menuItem.getPrice().multiplyBy(quantity);
    }

    public int getOrderId() {
        return order.getId();
    }

    public int getMenuItemId() {
        return menuItem.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id &&
                processed == orderItem.processed &&
                quantity == orderItem.quantity &&
                Objects.equals(order, orderItem.order) &&
                Objects.equals(menuItem, orderItem.menuItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, menuItem, processed, quantity);
    }

    @Override
    public boolean isValid() {
        return quantity > 0 &&
                menuItem.isValid() &&
                order.isValid();
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", menuItem=" + menuItem +
                ", quantity=" + quantity +
                ", processed=" + processed +
                '}';
    }

}
