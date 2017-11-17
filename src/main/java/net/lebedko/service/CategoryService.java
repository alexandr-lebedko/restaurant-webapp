package net.lebedko.service;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.CategoryView;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;
import java.util.Locale;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public interface CategoryService {

    Category insert(Category category) throws ServiceException;

    Collection<CategoryView> getAll(Locale locale) throws ServiceException;

    Collection<Category> getAll() throws ServiceException;
}
