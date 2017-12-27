package net.lebedko.dao;

import net.lebedko.dao.jdbc.JdbcInvoiceDao;
import net.lebedko.dao.jdbc.JdbcUserDao;
import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.EmailAddress;
import net.lebedko.entity.user.User;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class JdbcInvoiceDaoTest extends AbstractJdbcTest {
    private JdbcInvoiceDao invoiceDao;
    private JdbcUserDao userDao;
    private User userOne;
    private User userTwo;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.invoiceDao = new JdbcInvoiceDao(queryTemplate);
        this.userDao = new JdbcUserDao(queryTemplate);
        this.userOne = userDao.findByEmail(new EmailAddress("test.user@gmail.com"));
        this.userTwo = userDao.findByEmail(new EmailAddress("user@gmail.com"));
    }

    public void testInsert() throws Exception {
        invoiceDao.insert(new Invoice(userOne));

        ITable actualTable = getConnection().createDataSet().getTable("invoices");
        ITable initialTable = getDataSet().getTable("invoices");

        assertNotEquals(actualTable, initialTable);
    }

    public void testGetByUserAndState() throws Exception {
        User user = userTwo;
        InvoiceState state= InvoiceState.UNPAID;

        Invoice invoice = invoiceDao.getByUserAndState(user, state);

        assertNotNull(invoice);
        assertEquals(invoice.getUser(), user);
        assertEquals(invoice.getState(), state);
    }


    public void testFindById() throws Exception {
        assertNotNull(invoiceDao.findById(1L));
    }


    public void testUpdate() throws Exception {
        Long id = 1L;

        Invoice initial = invoiceDao.findById(id);

        invoiceDao.update(new Invoice(id, userOne, InvoiceState.PAID, new Price(333.3), LocalDateTime.now()));

        Invoice actual = invoiceDao.findById(id);

        assertNotEquals(actual, initial);
    }


    public void testGetByUser() throws Exception {
        Page<Invoice> invoices = invoiceDao.getByUser(
                userDao.findByEmail(new EmailAddress("test.user@gmail.com")),
                new Pageable(1));

        assertTrue(invoices.getContent().size() == 2);
    }

    public void testGetByState() throws Exception {
        Page<Invoice> unpaidInvoices = invoiceDao.getByState(InvoiceState.UNPAID, new Pageable(1));
        Page<Invoice> paidInvoices = invoiceDao.getByState(InvoiceState.PAID, new Pageable(1));

        assertTrue(unpaidInvoices.getContent().size() == 3);
        assertTrue(paidInvoices.getContent().size() == 1);
    }

    public void testDelete() throws Exception {
        Long id = 1L;
        invoiceDao.delete(id);

        ITable actualTable = getConnection().createDataSet().getTable("invoices");
        ITable initialTable = getDataSet().getTable("invoices");

        assertFalse(initialTable.equals(actualTable));
        assertNull(invoiceDao.findById(id));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass().getResourceAsStream("/data/invoicesDataSet.xml"));
    }
}