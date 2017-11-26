package net.lebedko.dao;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;

import java.util.Collection;

public interface ItemDao {

    Item insert(Item item);

    void update(Item item);

    Collection<Item> getByCategory(Category category);

    Item get(Long id);
}
