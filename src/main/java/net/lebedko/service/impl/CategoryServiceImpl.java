package net.lebedko.service.impl;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.entity.item.Category;
import net.lebedko.service.CategoryService;

import java.util.Collection;

public class CategoryServiceImpl implements CategoryService {
    private TransactionManager txManager;
    private CategoryDao categoryDao;

    public CategoryServiceImpl(TransactionManager txManager, CategoryDao categoryDao) {
        this.txManager = txManager;
        this.categoryDao = categoryDao;
    }

    @Override
    public Collection<Category> getAll() {
        return txManager.tx(() -> categoryDao.getAll());
    }

    @Override
    public Category insert(final Category category) {
        return txManager.tx(() -> categoryDao.insert(category));
    }

    @Override
    public void update(final Category category) {
        txManager.tx(() -> categoryDao.update(category));
    }

    @Override
    public void delete(Long id) {
        txManager.tx(() -> categoryDao.delete(id));
    }

    @Override
    public Category getById(Long id) {
        return txManager.tx(() -> categoryDao.findById(id));
    }
}