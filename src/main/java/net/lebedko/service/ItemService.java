package net.lebedko.service;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public interface ItemService {

    Item insert(Item item, InputStream image);

    Item get(Long id);

    void update(Item item);

    void update(Item item, InputStream image);

    List<Item> get(List<Long> ids);

    Collection<Item> getByCategory(Category category);
}