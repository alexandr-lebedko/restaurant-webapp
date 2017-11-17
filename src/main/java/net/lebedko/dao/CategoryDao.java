package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.item.Category;

import java.util.Collection;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public interface CategoryDao {

    Category insert(Category category) throws DataAccessException;

    Collection<Category> getAll() throws DataAccessException;

}