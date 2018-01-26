package net.lebedko.service.impl;

import net.lebedko.dao.ItemDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import net.lebedko.service.ImageService;
import net.lebedko.service.ItemService;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    private TransactionManager txManager;
    private ImageService imageService;
    private ItemDao itemDao;

    public ItemServiceImpl(TransactionManager txManager, ImageService imageService, ItemDao itemDao) {
        this.txManager = txManager;
        this.imageService = imageService;
        this.itemDao = itemDao;
    }

    @Override
    public Item insert(final Item item, final InputStream image) {
        String imageId = imageService.saveImg(image);
        try {
            return txManager.tx(() ->
                    itemDao.insert(new Item(item.getTitle(), item.getDescription(), item.getCategory(), item.getPrice(), imageId)));
        } catch (RuntimeException e) {
            imageService.deleteImg(imageId);
            throw e;
        }
    }

    @Override
    public void update(Item item) {
        txManager.tx(() -> itemDao.update(item));
    }

    @Override
    public void update(Item item, InputStream image) {
        String imageId = imageService.saveImg(image);
        try {
            imageService.deleteImg(item.getImageId());
            txManager.tx(() -> itemDao.update(
                    new Item(item.getId(),
                            item.getTitle(),
                            item.getDescription(),
                            item.getCategory(),
                            item.getPrice(),
                            imageId)));
        } catch (RuntimeException e) {
            imageService.deleteImg(imageId);
            throw e;
        }
    }

    @Override
    public Item get(Long id) {
        return txManager.tx(() -> itemDao.findById(id));
    }

    @Override
    public List<Item> get(List<Long> ids) {
        return txManager.tx(() -> ids.stream()
                .map(itemDao::findById)
                .collect(Collectors.toList()));
    }

    @Override
    public Collection<Item> getByCategory(Category category) {
        return txManager.tx(() -> itemDao.getByCategory(category));
    }
}