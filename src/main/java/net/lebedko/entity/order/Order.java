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

    private Order() {
    }

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

    public static Builder builder(Order order) {
        return new Builder(order);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Invoice invoice;
        private State state;
        private LocalDateTime createdOn;

        private Builder() {
        }

        private Builder(Order order) {
            this.id = order.getId();
            this.invoice = order.getInvoice();
            this.state = order.getState();
            this.createdOn = order.getCreatedOn();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setInvoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }

        public Builder setState(State state) {
            this.state = state;
            return this;
        }

        public Builder setCreation(LocalDateTime creation) {
            this.createdOn = creation;
            return this;
        }

        public Order build() {
            return new Order(this.id, this.invoice, this.state, this.createdOn);
        }

    }
}
