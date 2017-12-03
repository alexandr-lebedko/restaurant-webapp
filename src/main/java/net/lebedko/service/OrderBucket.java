package net.lebedko.service;

import net.lebedko.entity.item.Item;

import java.util.HashMap;
import java.util.Map;

public class OrderBucket {
    private Map<Item, Long> quantityToItem;
    private Long size;
    private boolean sizeCalculated;

    public OrderBucket() {
        this.quantityToItem = new HashMap<>();
    }

    public void add(Item item) {
        add(item, 1L);
    }

    public void add(Item item, Long quantity) {
        if (item == null || quantity == null) {
            throw new IllegalArgumentException("Item and quantity cannot be null!");
        }

        sizeCalculated = false;
        quantityToItem.computeIfPresent(item, (k, v) -> v + quantity);
        quantityToItem.putIfAbsent(item, quantity);
    }

    public Long getSize() {
        if (!sizeCalculated) {
            size = quantityToItem.values().stream()
                    .reduce(0L, Long::sum);
            sizeCalculated = true;
        }

        return size;
    }

    public Map<Item, Long> getContent() {
        return new HashMap<>(quantityToItem);
    }

    public Item getItem(Long id) {
        return quantityToItem.keySet().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "OrderBucket{" +
                "quantityToItem=" + quantityToItem +
                ", size=" + size +
                ", sizeCalculated=" + sizeCalculated +
                '}';
    }
}
