package net.lebedko.entity.order;

import net.lebedko.entity.invoice.Invoice;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Invoice invoice;
    private OrderState state;
    private LocalDateTime createdOn;

    public Order(Long id, Invoice invoice, OrderState state, LocalDateTime createdOn) {
        this.id = id;
        this.invoice = invoice;
        this.state = state;
        this.createdOn = createdOn;
    }

    public Order(Invoice invoice) {
        this(null, invoice, OrderState.NEW, LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public OrderState getState() {
        return state;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state=" + state +
                ", createdOn=" + createdOn +
                '}';
    }
}