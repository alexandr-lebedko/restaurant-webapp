package net.lebedko.service.demo;

import net.lebedko.dao.jdbc.demo.MenuItemDao;
import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.impl.ServiceTemplate;

import java.io.InputStream;

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

}
