package net.lebedko.dao;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.jdbc.JdbcCategoryDao;
import net.lebedko.dao.jdbc.JdbcItemDao;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.item.Category;
import net.lebedko.entity.item.Item;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.util.Collection;
import java.util.UUID;

public class JdbcItemDaoTest extends AbstractJdbcTest {
    private JdbcItemDao itemDao;
    private Category teaCategory;
    private Category dessertCategory;

    public void setUp() throws Exception {
        super.setUp();
        CategoryDao categoryDao = new JdbcCategoryDao(queryTemplate);
        this.teaCategory = categoryDao.findById(1L);
        this.dessertCategory = categoryDao.findById(3L);
        this.itemDao = new JdbcItemDao(queryTemplate);
    }


    public void testGetByCategory() {
        Collection<Item> teaItems = itemDao.getByCategory(teaCategory);
        Collection<Item> dessertItems = itemDao.getByCategory(dessertCategory);

        assertEquals(2, teaItems.size());
        assertEquals(1, dessertItems.size());
    }

    public void testDelete() throws Exception {
        itemDao.delete(1L);
        itemDao.delete(2L);

        ITable initialItemsTable = getDataSet().getTable("items");
        ITable currentItemsTable = getConnection().createDataSet().getTable("items");

        assertFalse(initialItemsTable.getRowCount() == currentItemsTable.getRowCount());
        assertNull(itemDao.findById(1L));
        assertNull(itemDao.findById(2L));
    }

    public void testFindById() {
        assertNotNull(itemDao.findById(1L));
    }

    public void testUpdate() {
        Item initial = itemDao.findById(1L);

        Item expected = new Item(initial.getId(), initial.getTitle(), initial.getDescription(), dessertCategory, new Price(1_000d), UUID.randomUUID().toString());

        itemDao.update(expected);

        Item actual = itemDao.findById(1L);

        assertEquals(actual, expected);
        assertFalse(initial.equals(actual));
    }

    public void testInsert() throws Exception {
        Item item = new Item(EntityGenerator.getTitle(), EntityGenerator.getDescription(), dessertCategory, EntityGenerator.getPrice(), UUID.randomUUID().toString());
        itemDao.insert(item);

        ITable initialTable = getDataSet().getTable("items");
        ITable currentTable = getConnection().createDataSet().getTable("items");

        assertEquals(initialTable.getRowCount() + 1, currentTable.getRowCount());
    }


    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/data/itemsDataSet"));
    }
}
