package net.lebedko.dao;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;

import java.util.Collection;

public interface ItemDao extends GenericDao<Item, Long>{

    Collection<Item> getByCategory(Category category);
}
