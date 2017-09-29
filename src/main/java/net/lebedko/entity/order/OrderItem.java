package net.lebedko.entity.order;

import net.lebedko.entity.item.Item;

/**
 * alexandr.lebedko : 27.09.2017.
 */
public class OrderItem {
    private long id;

    private Order order;
    private Item item;
    private int quantity;
}
