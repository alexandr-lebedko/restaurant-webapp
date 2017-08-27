package net.lebedko.service.demo;

import net.lebedko.dao.jdbc.demo.CategoryDao;
import net.lebedko.entity.demo.item.Category;
import net.lebedko.entity.demo.item.CategoryView;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.impl.ServiceTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * alexandr.lebedko : 03.08.2017.
 */
public class CategoryServiceImpl implements CategoryService {

    private ServiceTemplate template;
    private CategoryDao categoryDao;

    public CategoryServiceImpl(ServiceTemplate template, CategoryDao categoryDao) {
        this.template = template;
        this.categoryDao = categoryDao;
    }

    public Category insert(final Category category) throws ServiceException {
        return template.doTxService(() -> categoryDao.insert(category));
    }

    @Override
    public Collection<CategoryView> getAll(final Locale locale) throws ServiceException {

        final Collection<Category> categories = template.doTxService(() -> categoryDao.getAll());

        return categories.stream()
                .map(category -> toView(category, locale))
                .collect(Collectors.toList());
    }

    private CategoryView toView(final Category category, final Locale locale) {
        return new CategoryView(category.getId(), category.getValue().get(locale), category.getImageId());
    }

}
