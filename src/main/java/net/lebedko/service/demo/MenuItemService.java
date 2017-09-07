package net.lebedko.service.demo;

import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.service.exception.ServiceException;

import java.io.InputStream;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public interface MenuItemService {

    MenuItem insertItemAndImage(MenuItem item, InputStream image) throws ServiceException;
}
