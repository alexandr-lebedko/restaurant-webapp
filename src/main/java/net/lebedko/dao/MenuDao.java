package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.dish.Dish.DishCategory;
import net.lebedko.entity.menu.Menu;
import net.lebedko.entity.menu.MenuItem;

/**
 * alexandr.lebedko : 21.04.2017.
 */

public interface MenuDao extends GenericDao<MenuItem> {

    Menu getAllMenu() throws DataAccessException;

    Menu getActiveMenu() throws DataAccessException;

    Menu getStopListMenu() throws DataAccessException;

    Menu getByCategory(DishCategory category) throws DataAccessException;

}
