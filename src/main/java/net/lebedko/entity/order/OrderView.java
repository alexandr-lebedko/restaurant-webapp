package net.lebedko.entity.order;

import java.time.LocalDateTime;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class OrderView {
    private int id;
    private OrderState state;
    private LocalDateTime creation;

    public OrderView(int id, OrderState state, LocalDateTime creation) {
        this.id = id;
        this.state = state;
        this.creation = creation;
    }

    public int getId() {
        return id;
    }

    public OrderState getState() {
        return state;
    }

    public LocalDateTime getCreation() {
        return creation;
    }
}
