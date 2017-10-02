package net.lebedko.entity.order;

import net.lebedko.entity.invoice.Invoice;

import java.time.LocalDateTime;

/**
 * alexandr.lebedko : 30.09.2017.
 */
public class Order {
    private Long id;
    private Invoice invoice;
    private State state;
    private LocalDateTime createdOn;

    public Order(Long id, Invoice invoice, State state, LocalDateTime createdOn) {
        this.id = id;
        this.invoice = invoice;
        this.state = state;
        this.createdOn = createdOn;
    }

    public Order(Invoice invoice) {
        this(null, invoice, State.NEW, LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public State getState() {
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
