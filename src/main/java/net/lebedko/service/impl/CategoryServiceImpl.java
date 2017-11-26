package net.lebedko.service.impl;

import net.lebedko.dao.CategoryDao;
import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;
import java.util.Objects;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class CategoryServiceImpl implements CategoryService {

    private ServiceTemplate template;
    private CategoryDao categoryDao;

    public CategoryServiceImpl(ServiceTemplate template, CategoryDao categoryDao) {
        this.template = Objects.requireNonNull(template);
        this.categoryDao = Objects.requireNonNull(categoryDao);
    }

    @Override
    public Collection<Category> getAll() throws ServiceException {
        return template.doTxService(() -> categoryDao.getAll());
    }

    @Override
    public Category insert(final Category category) throws ServiceException {
        return template.doTxService(() -> categoryDao.insert(category));
    }

    @Override
    public void update(final Category category) {
        template.doTxService(() -> categoryDao.update(category));
    }

    @Override
    public void delete(Long id) {
        template.doTxService(() -> categoryDao.delete(id));
    }

    @Override
    public Category getById(Long id) {
        return template.doTxService(()->categoryDao.getById(id));
    }

}
