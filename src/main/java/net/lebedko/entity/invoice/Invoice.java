package net.lebedko.entity.invoice;

import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.order.Order;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * alexandr.lebedko : 19.04.2017.
 */

public class Invoice implements Entity, Validatable {

    private int id;
    private Order order;
    private Price price;
    private boolean paid;
    private LocalDateTime creationDateTime;


    public Invoice(Order order, boolean paid) {
        this(0, order, paid, LocalDateTime.now());
    }

    public Invoice(int id, Order order, boolean paid, LocalDateTime creationDateTime) {
        this.id = id;
        this.order = order;
        this.paid = paid;
        this.creationDateTime = creationDateTime.truncatedTo(SECONDS);
        this.price = order.countSum();
    }

    protected Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public int getOrderId() {
        return order.getId();
    }

    public Price getPrice() {
        return price;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public boolean isValid() {
        return order.isValid() &&
                price.isValid() &&
                creationDateTime.compareTo(LocalDateTime.now()) <= 0;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", order=" + order +
                ", price=" + price +
                ", paid=" + paid +
                ", creationDateTime=" + creationDateTime +
                '}';
    }



}
