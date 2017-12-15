package net.lebedko.service.impl;

import net.lebedko.dao.CategoryDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.TransactionManagerStub;
import net.lebedko.entity.item.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;
    private TransactionManager txManager = new TransactionManagerStub();

    @Mock
    private CategoryDao categoryDao;
    @Mock
    private Category category;

    @Before
    public void setUp() {
        categoryService = new CategoryServiceImpl(txManager, categoryDao);
    }

    @Test
    public void getAll() throws Exception {
        categoryService.getAll();

        verify(categoryDao).getAll();
    }

    @Test
    public void insert() throws Exception {
        categoryService.insert(category);

        verify(categoryDao).insert(category);
    }

    @Test
    public void update() throws Exception {
        categoryService.update(category);

        verify(categoryDao).update(category);
    }

    @Test
    public void delete() throws Exception {
        Long id = 123L;
        categoryService.delete(id);

        verify(categoryDao).delete(id);
    }

    @Test
    public void getById() throws Exception {
        Long id = 123L;
        categoryService.getById(id);

        verify(categoryDao).findById(id);
    }

}