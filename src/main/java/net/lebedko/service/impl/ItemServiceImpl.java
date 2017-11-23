package net.lebedko.service.impl;

import net.lebedko.dao.ItemDao;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.service.FileService;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class ItemServiceImpl implements ItemService {
    private ServiceTemplate template;
    private FileService fileService;
    private ItemDao itemDao;

    public ItemServiceImpl(ServiceTemplate template, FileService fileService, ItemDao itemDao) {
        this.template = template;
        this.fileService = fileService;
        this.itemDao = itemDao;
    }

    @Override
    public Item insertItemAndImage(final Item item, final InputStream image) throws ServiceException {
        String imageId = fileService.saveImg(image);
        try {
            return template.doTxService(() ->
                    itemDao.insert(new Item(item.getTitle(), item.getDescription(), item.getCategory(), item.getPrice(), imageId)));
        } catch (ServiceException e) {
            fileService.deleteFile(imageId);
            throw e;
        }
    }

    @Override
    public Item get(long id) throws ServiceException {
        return template.doTxService(() -> itemDao.get(id));
    }

    @Override
    public List<Item> get(List<Long> ids) throws ServiceException {
        return template.doTxService(() -> ids.stream()
                .map(itemDao::get)
                .collect(Collectors.toList()));
    }

    @Override
    public Collection<Item> getByCategory(Category category) {
        return template.doTxService(() -> itemDao.getByCategory(category));
    }
}