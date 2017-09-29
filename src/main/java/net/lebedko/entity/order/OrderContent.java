package net.lebedko.entity.order;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class OrderContent implements Validatable {
    private Map<Item, Integer> numberToItem;

    public OrderContent() {
        this.numberToItem = new LinkedHashMap<>();
    }

    private void validate(Item item, Integer number) {
        if (isNull(item) || isNull(number))
            throw new IllegalArgumentException("Item and number cannot be null");

        if (number <= 0)
            throw new IllegalArgumentException("Number cannot be equal or less than zero");
    }

    public void add(Item item, Integer number) {
        validate(item, number);

        numberToItem.computeIfPresent(item, (k, v) -> v + number);
        numberToItem.putIfAbsent(item, number);
    }

    public void add(Item item) {
        add(item, 1);
    }

    public void set(Item item, Integer number) {
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

    public Map<Item, Integer> getMap() {
        return new LinkedHashMap<>(numberToItem);
    }

    public static Item getById(OrderContent orderContent, long itemId) {
        return orderContent.numberToItem.keySet().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "OrderContent{" +
                "numberToItem=" + numberToItem +
                '}';
    }
}
