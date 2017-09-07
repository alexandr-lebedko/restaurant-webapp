package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.demo.item.Category;

import java.util.Collection;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public interface CategoryDao {

    public Category insert(Category category) throws DataAccessException;

    public Collection<Category> getAll() throws DataAccessException;

    public Category getById(int id) throws DataAccessException;
}