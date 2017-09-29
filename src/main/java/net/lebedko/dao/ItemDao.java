package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;

import java.util.Collection;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface ItemDao {

    Item insert(Item item) throws DataAccessException;

    Collection<Item> getByCategory(Category category) throws DataAccessException;

    Item get(long id) throws DataAccessException;
}
