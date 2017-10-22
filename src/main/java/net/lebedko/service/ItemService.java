package net.lebedko.service;

import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.ItemView;
import net.lebedko.service.exception.ServiceException;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface ItemService {

    Item insertItemAndImage(Item item, InputStream image) throws ServiceException;

    Collection<ItemView> getByCategory(Category category, Locale locale) throws ServiceException;

    Item get(long id) throws ServiceException;

    List<Item> get(List<Long> ids) throws ServiceException;
}
