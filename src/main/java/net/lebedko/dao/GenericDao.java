package net.lebedko.dao;

public interface GenericDao<T, ID> {

    T insert(T entity);

    T findById(ID id);

    void update(T t);

    void delete(ID id);

}
