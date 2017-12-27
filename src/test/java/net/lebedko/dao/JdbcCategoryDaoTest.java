package net.lebedko.dao;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.exception.EntityExistsException;
import net.lebedko.dao.jdbc.JdbcCategoryDao;
import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.util.SupportedLocales;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.util.Collection;

import static net.lebedko.util.SupportedLocales.EN_CODE;
import static net.lebedko.util.SupportedLocales.RU_CODE;
import static net.lebedko.util.SupportedLocales.UA_CODE;

public class JdbcCategoryDaoTest extends AbstractJdbcTest {
    private JdbcCategoryDao categoryDao;

    public void setUp() throws Exception {
        super.setUp();
        categoryDao = new JdbcCategoryDao(queryTemplate);
    }

    public void testFindById() throws Exception {
        Category expected = new Category(1L,
                StringI18N.builder()
                        .add(SupportedLocales.getByCode(RU_CODE), "Чай")
                        .add(SupportedLocales.getByCode(EN_CODE), "Tea")
                        .add(SupportedLocales.getByCode(UA_CODE), "Чай")
                        .build());

        Category actual = categoryDao.findById(expected.getId());

        assertEquals(actual, expected);
    }

    public void testGetAll() throws Exception {
        Collection<Category> categories = categoryDao.getAll();

        assertEquals(3, categories.size());
    }

    public void testInsert() throws Exception {
        Category category = EntityGenerator.getCategory();

        categoryDao.insert(category);

        ITable actualTable = getConnection().createDataSet().getTable("categories");
        ITable initialTable = getDataSet().getTable("categories");

        assertFalse(actualTable.equals(initialTable));
        assertTrue(categoryDao.getAll().contains(category));
    }

    public void testInsertExistentCategory() {
        Category category = EntityGenerator.getCategory();

        EntityExistsException expected = null;
        try {
            categoryDao.insert(category);
            categoryDao.insert(category);
        } catch (EntityExistsException e) {
            expected = e;
        }
        assertNotNull(expected);
    }

    public void testUpdate() throws Exception {
        Long id = 1L;
        Category expected = new Category(id,
                StringI18N.builder()
                        .add(SupportedLocales.getByCode(RU_CODE), "Мясо")
                        .add(SupportedLocales.getByCode(EN_CODE), "Meat")
                        .add(SupportedLocales.getByCode(UA_CODE), "М'ясо")
                        .build());

        Category initial = categoryDao.findById(id);

        categoryDao.update(expected);

        Category actual = categoryDao.findById(id);

        assertTrue(expected.equals(actual));
        assertFalse(expected.equals(initial));
    }

    public void testDelete() throws Exception {
        Long id = 1L;
        categoryDao.delete(id);

        ITable actualTable = getConnection().createDataSet().getTable("categories");
        ITable initialTable = getDataSet().getTable("categories");

        assertFalse(initialTable.equals(actualTable));
        assertNull(categoryDao.findById(id));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass().getResourceAsStream("/data/categoriesDataSet.xml"));
    }
}