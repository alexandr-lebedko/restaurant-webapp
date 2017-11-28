package net.lebedko.entity.invoice;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(user, invoice.user) &&
                state == invoice.state &&
                Objects.equals(amount, invoice.amount) &&
                Objects.equals(createdOn, invoice.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, state, amount, createdOn);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", user=" + user +
                ", state=" + state +
                ", amount=" + amount +
                ", createdOn=" + createdOn +
                '}';
    }
}
