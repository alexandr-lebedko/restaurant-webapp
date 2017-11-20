package net.lebedko.entity.invoice;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.user.User;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private User user;
    private InvoiceState state;
    private Price amount;
    private LocalDateTime createdOn;

    public Invoice(Long id, User user, InvoiceState state, Price amount, LocalDateTime cratedOn) {
        this.id = id;
        this.user = user;
        this.state = state;
        this.amount = amount;
        this.createdOn = cratedOn;
    }

    public Invoice(User user, InvoiceState state, Price amount, LocalDateTime cratedOn) {
        this(null, user, state, amount, cratedOn);
    }

    public Invoice(User user) {
        this(null, user, InvoiceState.UNPAID, new Price(0.), LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Price getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public InvoiceState getState() {
        return state;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static Builder Builder(Invoice invoice) {
        return new Builder(invoice);
    }

    public static class Builder {
        private Long id;
        private User user;
        private InvoiceState state;
        private Price amount;
        private LocalDateTime createdOn;

        private Builder(Invoice invoice) {
            this.id = invoice.getId();
            this.user = invoice.getUser();
            this.state = invoice.getState();
            this.amount = invoice.getAmount();
        }

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setState(InvoiceState state) {
            this.state = state;
            return this;
        }

        public Builder setCreation(LocalDateTime creation) {
            this.createdOn = creation;
            return this;
        }

        public Builder setPrice(Price price) {
            this.amount = price;
            return this;
        }

        public Invoice build() {
            return new Invoice(id, user, state, amount, createdOn);
        }
    }
}
