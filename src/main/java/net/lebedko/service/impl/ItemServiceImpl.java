package net.lebedko.service.impl;

import net.lebedko.dao.ItemDao;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.item.ItemView;
import net.lebedko.service.FileService;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
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
        item.setPictureId(imageId);

        try {
            return template.doTxService(() -> itemDao.insert(item));
        } catch (ServiceException e) {
            fileService.deleteFile(imageId);
            throw e;
        }
    }

    @Override
    public Collection<ItemView> getByCategory(final Category category, final Locale locale) throws ServiceException {

        final Collection<Item> items = template.doTxService(() -> itemDao.getByCategory(category));

        return items.stream()
                .map(item -> toView(item, locale))
                .collect(Collectors.toList());
    }

    @Override
    public Item get(long id) throws ServiceException {
        return template.doTxService(() -> itemDao.get(id));
    }

    private ItemView toView(final Item item, final Locale locale) {
        ItemView view = new ItemView();

        view.setId(item.getId());
        view.setTitle(item.getTitle().getValue().get(locale));
        view.setDescription(item.getDescription().getValue().get(locale));
        view.setPrice(item.getPrice().getValue());
        view.setPictureId(item.getPictureId());
        view.setCategory(item.getCategory().getValue().get(locale));
        view.setState(item.getState().name());

        return view;
    }
}