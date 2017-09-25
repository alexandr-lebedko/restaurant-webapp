package net.lebedko.service.demo;

import net.lebedko.dao.jdbc.demo.MenuItemDao;
import net.lebedko.entity.demo.item.Category;
import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.entity.demo.item.MenuItemView;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.impl.ServiceTemplate;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class MenuItemServiceImpl implements MenuItemService {
    private ServiceTemplate template;
    private FileService fileService;
    private MenuItemDao itemDao;

    public MenuItemServiceImpl(ServiceTemplate template, FileService fileService, MenuItemDao itemDao) {
        this.template = template;
        this.fileService = fileService;
        this.itemDao = itemDao;
    }

    @Override
    public MenuItem insertItemAndImage(final MenuItem item, final InputStream image) throws ServiceException {
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
    public Collection<MenuItemView> getByCategory(final Category category, final Locale locale) throws ServiceException {

        final Collection<MenuItem> items = template.doTxService(() -> itemDao.getByCategory(category));

        return items.stream()
                .map(item -> toView(item, locale))
                .collect(Collectors.toList());
    }

    @Override
    public MenuItem get(long id) throws ServiceException {
        return template.doTxService(() -> itemDao.get(id));
    }

    private MenuItemView toView(final MenuItem item, final Locale locale) {
        MenuItemView view = new MenuItemView();

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