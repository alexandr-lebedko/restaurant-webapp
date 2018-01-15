package net.lebedko.dao;

import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

public interface GenericDao<T, ID> {
    Properties QUERIES = loadProperties("sql-queries.properties");

    T insert(T entity);

    T findById(ID id);

    void update(T t);

    void delete(ID id);

}
