package net.lebedko.service.demo;

import net.lebedko.entity.demo.item.Category;
import net.lebedko.entity.demo.item.CategoryView;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public interface CategoryService {

    Category insert(Category category) throws ServiceException;

    Collection<CategoryView> getAll(Locale locale) throws ServiceException;

    Category getByID(int id) throws ServiceException;
}
