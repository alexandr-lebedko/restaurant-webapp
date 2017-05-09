package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;

/**
 * alexandr.lebedko : 21.04.2017.
 */

public interface GenericDao<T extends Validatable & Entity> {

    T insert(T entity) throws DataAccessException;

    T findById(int id) throws DataAccessException;

    void update(T t) throws DataAccessException;

    void delete(int id) throws DataAccessException;

}
