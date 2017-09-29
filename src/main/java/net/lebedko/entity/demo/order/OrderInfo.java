package net.lebedko.entity.demo.order;

import java.time.LocalDateTime;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class OrderInfo {
    private OrderState state;
    private LocalDateTime creation;

    public OrderInfo() {
        this(OrderState.NEW, LocalDateTime.now());
    }

    public OrderInfo(OrderState state, LocalDateTime creation) {
        this.state = state;
        this.creation = creation;
    }

    public OrderState getState() {
        return state;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "state=" + state +
                ", creation=" + creation +
                '}';
    }
}
