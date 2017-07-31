package net.lebedko.entity.demo.invoice;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class Invoice {

    private long id;
    private User user;
    private List<Order> orders;

    private LocalDateTime creationDateTime;

}
