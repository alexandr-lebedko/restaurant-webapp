package net.lebedko.dao;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.jdbc.JdbcInvoiceDao;
import net.lebedko.dao.jdbc.JdbcItemDao;
import net.lebedko.dao.jdbc.JdbcOrderDao;
import net.lebedko.dao.jdbc.JdbcOrderItemDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.util.Collection;
import java.util.stream.Collectors;

public class JdbcOrderItemDaoTest extends AbstractJdbcTest {
    private JdbcOrderItemDao orderItemDao;
    private Invoice invoice;
    private Order orderOne;
    private Order orderTwo;
    private Item item;

    public void setUp() throws Exception {
        super.setUp();

        JdbcOrderDao orderDao = new JdbcOrderDao(queryTemplate);
        this.orderOne = orderDao.findById(1L);
        this.orderTwo = orderDao.findById(2L);

        this.invoice = new JdbcInvoiceDao(queryTemplate).findById(1L);
        this.item = new JdbcItemDao(queryTemplate).findById(1L);
        this.orderItemDao = new JdbcOrderItemDao(queryTemplate);

    }

    public void testFindById() {
        assertNotNull(orderItemDao.findById(1L));
    }

    public void testInsert() throws Exception {
        orderItemDao.insert(new OrderItem(orderOne, item, EntityGenerator.getLong()));

        ITable initialTable = getDataSet().getTable("order_items");
        ITable currentTable = getConnection().createDataSet().getTable("order_items");

        assertEquals(initialTable.getRowCount() + 1, currentTable.getRowCount());
    }

    public void testGetByOrder() {
        Collection<OrderItem> orderItemsByOrderOne = orderItemDao.getByOrder(orderOne);
        Collection<OrderItem> orderItemsByOrderTwo = orderItemDao.getByOrder(orderTwo);

        assertEquals(4, orderItemsByOrderOne.size());
        assertEquals(2, orderItemsByOrderTwo.size());
    }

    public void testDeleteByOrder() throws Exception {
        orderItemDao.deleteByOrder(orderOne);

        assertEquals(0, orderItemDao.getByOrder(orderOne).size());
    }

    public void testDeleteById() throws Exception {
        orderItemDao.delete(1L);

        ITable initialTable = getDataSet().getTable("order_items");
        ITable currentTable = getConnection().createDataSet().getTable("order_items");

        assertEquals(initialTable.getRowCount() - 1, currentTable.getRowCount());
    }

    public void testGetByInvoice() throws Exception {
        Collection<OrderItem> orderItems = orderItemDao.getByInvoice(invoice);

        assertEquals(6, orderItems.size());
    }

    public void testUpdateOrderItems() throws Exception {
        Order order = this.orderTwo;
        Collection<OrderItem> initial = orderItemDao.getByOrder(order);

        Collection<OrderItem> expected = initial.stream()
                .map(oi -> new OrderItem(oi.getId(), oi.getOrder(), oi.getItem(), EntityGenerator.getLong()))
                .collect(Collectors.toList());

        orderItemDao.update(expected);

        Collection<OrderItem> actual = orderItemDao.getByOrder(order);

        assertEquals(expected, actual);
    }

    public void testDeleteOrderItems() throws Exception {
        Collection<OrderItem> orderItems = orderItemDao.getByOrder(orderOne);

        orderItemDao.delete(orderItems);

        assertEquals(0, orderItemDao.getByOrder(orderOne).size());
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/data/orderItemsDataSet"));
    }
}