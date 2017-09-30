package net.lebedko.entity.order.demo;

import java.time.LocalDateTime;

/**
 * alexandr.lebedko : 30.09.2017.
 */
public class Order {
    private Long id;
    private State state;
    private LocalDateTime createdOn;

    public Order(State state, LocalDateTime createdOn) {
        this(null, state, createdOn);
    }

    public Order(Long id, State state, LocalDateTime createdOn) {
        this.id = id;
        this.state = state;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
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
