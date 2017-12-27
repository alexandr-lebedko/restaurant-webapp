package net.lebedko.dao;

import net.lebedko.dao.jdbc.JdbcInvoiceDao;
import net.lebedko.dao.jdbc.JdbcOrderDao;
import net.lebedko.dao.jdbc.JdbcUserDao;
import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.time.LocalDateTime;
import java.util.Collection;

public class JdbcOrderDaoTest extends AbstractJdbcTest {
    private JdbcOrderDao orderDao;
    private Invoice invoice;
    private User user;

    public void setUp() throws Exception {
        super.setUp();
        this.orderDao = new JdbcOrderDao(queryTemplate);
        this.invoice = new JdbcInvoiceDao(queryTemplate).findById(1L);
        this.user = new JdbcUserDao(queryTemplate).findByEmail(new EmailAddress("test.user@gmail.com"));
    }

    public void testGetByState() throws Exception {
        Collection<Order> newOrders = orderDao.getByState(OrderState.NEW);
        Collection<Order> processedOrders = orderDao.getByState(OrderState.PROCESSED);

        assertEquals(2, newOrders.size());
        assertEquals(2, processedOrders.size());
    }

    public void testGetPagedByState() throws Exception {
        Page<Order> pagedNewOrders = orderDao.getByState(OrderState.NEW, new Pageable(1));
        Page<Order> pagedModifiedOrders = orderDao.getByState(OrderState.MODIFIED, new Pageable(1));
        Page<Order> pagedRejectedOrders = orderDao.getByState(OrderState.REJECTED, new Pageable(1));

        assertEquals(2, pagedNewOrders.getContent().size());
        assertEquals(1, pagedModifiedOrders.getContent().size());
        assertEquals(0, pagedRejectedOrders.getContent().size());
    }

    public void testGetByInvoice() throws Exception {
        Collection<Order> orders = orderDao.getByInvoice(invoice);

        assertEquals(5, orders.size());
    }

    public void testFindById() throws Exception {
        assertNotNull(orderDao.findById(1L));
        assertNotNull(orderDao.findById(2L));
        assertNull(orderDao.findById(-1L));
    }

    public void testGetByUser() throws Exception {
        Page<Order> pagedOrdersByUser = orderDao.getByUser(user, new Pageable(1, 10));

        assertEquals(5, pagedOrdersByUser.getContent().size());
    }

    public void testGetByOrderIdAndUser() throws Exception {
        assertNotNull(orderDao.getByOrderIdAndUser(1L, user));
        assertNotNull(orderDao.getByOrderIdAndUser(2L, user));
        assertNull(orderDao.getByOrderIdAndUser(-3L, user));
    }

    public void testDelete() throws Exception {
        orderDao.delete(1L);
        orderDao.delete(2L);

        ITable actualTable = getConnection().createDataSet().getTable("orders");
        ITable initialTable = getDataSet().getTable("orders");

        assertFalse(initialTable.getRowCount() == actualTable.getRowCount());
        assertNull(orderDao.findById(1L));
        assertNull(orderDao.findById(2L));
    }

    public void testInsert() throws Exception {
        orderDao.insert(new Order(invoice));

        ITable initialTable = getDataSet().getTable("orders");
        ITable actualTable = getConnection().createDataSet().getTable("orders");
        assertFalse(initialTable.getRowCount() == actualTable.getRowCount());
    }


    public void testUpdate() throws Exception {
        Long id = 1L;
        Order initial= orderDao.findById(id);
        orderDao.update(new Order(id, invoice, OrderState.REJECTED, LocalDateTime.now()));
        Order actual = orderDao.findById(id);

        assertFalse(initial.equals(actual));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/data/ordersDataSet.xml"));
    }
}