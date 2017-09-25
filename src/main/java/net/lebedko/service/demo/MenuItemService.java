package net.lebedko.service.demo;

import net.lebedko.entity.demo.item.Category;
import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.entity.demo.item.MenuItemView;
import net.lebedko.service.exception.ServiceException;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface MenuItemService {

    MenuItem insertItemAndImage(MenuItem item, InputStream image) throws ServiceException;

    Collection<MenuItemView> getByCategory(Category category, Locale locale) throws ServiceException;

    MenuItem get(long id) throws ServiceException;
}
