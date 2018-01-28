package net.lebedko.service;

import net.lebedko.entity.item.Category;

import java.util.Collection;

public interface CategoryService {

    Collection<Category> getAll();

    Category insert(Category category);

    void update(Category category);

    void delete(Long id);

    Category getById(Long id);
}