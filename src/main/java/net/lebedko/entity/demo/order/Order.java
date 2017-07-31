package net.lebedko.entity.demo.order;

import net.lebedko.entity.item.MenuItem;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class Order {

    public enum OrderState {
    }

    private long id;

    private Map<MenuItem, Integer> quantitiesByMenuItem;
    private OrderState state;
    private LocalDateTime creationTimeStamp;
}
