package net.lebedko.entity.demo.order;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.demo.item.MenuItem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class OrderContent implements Validatable {
    private Map<MenuItem, Integer> numberToItem;

    public OrderContent() {
        this.numberToItem = new LinkedHashMap<>();
    }

    private void validate(MenuItem item, Integer number) {
        if (isNull(item) || isNull(number))
            throw new IllegalArgumentException("Item and number cannot be null");

        if (number <= 0)
            throw new IllegalArgumentException("Number cannot be equal or less than zero");
    }

    public void add(MenuItem item, Integer number) {
        validate(item, number);

        numberToItem.computeIfPresent(item, (k, v) -> v + number);
        numberToItem.putIfAbsent(item, number);
    }

    public void add(MenuItem item) {
        add(item, 1);
    }

    public void set(MenuItem item, Integer number) {
        validate(item, number);

        numberToItem.put(item, number);
    }

    @Override
    public boolean isValid() {
        return notEmpty();
    }

    private boolean notEmpty() {
        return !numberToItem.isEmpty();
    }

    public Map<MenuItem, Integer> getMap() {
        return new LinkedHashMap<>(numberToItem);
    }

    public static Optional<MenuItem> getById(OrderContent orderContent, long itemId) {
        return orderContent.numberToItem.keySet().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst();
    }

}
