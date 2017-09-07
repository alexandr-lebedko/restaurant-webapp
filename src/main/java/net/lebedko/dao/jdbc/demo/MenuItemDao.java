package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.demo.item.MenuItem;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface MenuItemDao {

    MenuItem insert(MenuItem item) throws DataAccessException;

}
