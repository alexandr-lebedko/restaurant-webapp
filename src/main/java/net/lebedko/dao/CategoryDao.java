package net.lebedko.dao;

import net.lebedko.entity.item.Category;

import java.util.Collection;

public interface CategoryDao extends GenericDao<Category, Long> {
    Collection<Category> getAll();
}