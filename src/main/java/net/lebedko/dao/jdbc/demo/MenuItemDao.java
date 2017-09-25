package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.demo.item.Category;
import net.lebedko.entity.demo.item.MenuItem;

import java.util.Collection;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface MenuItemDao {

    MenuItem insert(MenuItem item) throws DataAccessException;

    Collection<MenuItem> getByCategory(Category category) throws DataAccessException;

    MenuItem get(long id) throws DataAccessException;
}
