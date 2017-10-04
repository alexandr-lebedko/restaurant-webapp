package net.lebedko.entity.invoice;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.user.User;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private User user;
    private State state;
    private Price amount;
    private LocalDateTime cratedOn;

    public Invoice(Long id, User user, State state, Price amount, LocalDateTime cratedOn) {
        this.id = id;
        this.user = user;
        this.state = state;
        this.amount = amount;
        this.cratedOn = cratedOn;
    }

    public Invoice(User user, State state, Price amount, LocalDateTime cratedOn) {
        this(null, user, state, amount, cratedOn);
    }

    public Invoice(User user) {
        this(null, user, State.ACTIVE, new Price(0.), LocalDateTime.now());
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

    public State getState() {
        return state;
    }

    public LocalDateTime getCratedOn() {
        return cratedOn;
    }
}
