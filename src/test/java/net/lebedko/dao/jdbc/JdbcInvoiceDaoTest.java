package net.lebedko.dao.jdbc;

import net.lebedko.dao.exception.UniqueViolationException;
import net.lebedko.dao.connection.TestConnectionProvider;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceView;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.user.User;
import org.junit.*;

import java.util.List;

import static net.lebedko.EntityGenerator.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 05.05.2017.
 */
public class JdbcInvoiceDaoTest {

    @ClassRule
    public static DataBaseResource dataBaseResource = new DataBaseResource();

    private TestConnectionProvider connectionProvider;
    private QueryTemplate template;
    private JdbcInvoiceDao invoiceDao;

    private User user;
    private Order orderOne;
    private Order orderTwo;

    @Before
    public void beforeTest() throws Exception {
        connectionProvider = dataBaseResource.getConnectionProvider();
        template = new QueryTemplate(connectionProvider);
        invoiceDao = new JdbcInvoiceDao(template);
        JdbcOrderDao orderDao = new JdbcOrderDao(template);
        JdbcUserDao userDao = new JdbcUserDao(template);

        user = new User(getFullName(), getEmailAddresses()[0], getPassword(), getUserRole());
        userDao.insert(user);

        orderOne = new Order(user, getOrderStatus());
        orderTwo = new Order(user, getOrderStatus());
        orderDao.insert(orderOne);
        orderDao.insert(orderTwo);
    }


    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new JdbcInvoiceDao(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertInvalidInvoiceTest() throws Exception {
        Invoice invoice = mock(Invoice.class);
        when(invoice.isPaid()).thenReturn(false);
        invoiceDao.insert(invoice);
    }

    @Test
    public void insertAndGetByIdTest() throws Exception {
        Invoice invoice = new Invoice(orderOne, getBoolean());
        invoiceDao.insert(invoice);

        InvoiceView fetchedInvoiceView = invoiceDao.findById(invoice.getId());

        assertThat(fetchedInvoiceView.getCreationDateTime(), is(invoice.getCreationDateTime()));
        assertThat(fetchedInvoiceView.getOrderId(), is(invoice.getOrderId()));
        assertThat(fetchedInvoiceView.isPaid(), is(invoice.isPaid()));
        assertThat(fetchedInvoiceView.getPrice(), is(invoice.getPrice()));
    }

    @Test
    public void delete()throws Exception  {
        Invoice invoice = new Invoice(orderOne, getBoolean());
        invoiceDao.insert(invoice);

        int id = invoice.getId();
        invoiceDao.delete(id);

        InvoiceView fetchedInvoice = invoiceDao.findById(id);
        Assert.assertNull(fetchedInvoice);
    }

    @Test
    public void update() throws Exception {
        Invoice initialInvoice = new Invoice(orderOne, false);
        invoiceDao.insert(initialInvoice);
        int id = initialInvoice.getId();

        Invoice updatedInvoice = new Invoice(id, orderOne, true, initialInvoice.getCreationDateTime());
        invoiceDao.update(updatedInvoice);

        InvoiceView fetchedInvoice = invoiceDao.findById(id);
        assertThat(fetchedInvoice.isPaid(),
                allOf(is(updatedInvoice.isPaid()), not(initialInvoice.isPaid())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithInvalidInvoiceArgument() throws Exception {
        Invoice invoice = mock(Invoice.class);
        when(invoice.isValid()).thenReturn(false);
        invoiceDao.update(invoice);
    }

    @Test(expected = UniqueViolationException.class)
    public void insertInvoicesWithSameOrderTest() throws Exception {
        Invoice invoiceOne = new Invoice(orderOne, true);
        Invoice invoiceTwo = new Invoice(orderOne, false);

        invoiceDao.insert(invoiceOne);
        invoiceDao.insert(invoiceTwo);
    }

    @Test
    public void getUnpaidInvoices() throws Exception {
        Invoice paidInvoice = new Invoice(orderTwo, true);
        Invoice unpaidInvoice = new Invoice(orderOne, false);

        invoiceDao.insert(paidInvoice);
        invoiceDao.insert(unpaidInvoice);

        List<InvoiceView> unpaidInvoices = invoiceDao.getUnpaidInvoices();
        int size = unpaidInvoices.size();
        assertThat("Unpaid invoices list should contain one element", size, is(1));
    }

    @Test
    public void getUnpaidInvoicesByClient() throws Exception {
        Invoice invoiceOne = new Invoice(orderOne, false);
        Invoice invoiceTwo = new Invoice(orderTwo, false);

        invoiceDao.insert(invoiceOne);
        invoiceDao.insert(invoiceTwo);

        List<InvoiceView> unpaidInvoices = invoiceDao.getUnpaidInvoicesByUser(user);
        int size = unpaidInvoices.size();
        assertThat("Unpaid invoices list should contain 2 elements", size, is(2));

    }

    @After
    public void afterTest() throws Exception {
        template.update("DELETE FROM invoices");
        template.update("DELETE FROM order_items");
        template.update("DELETE FROM orders");
        template.update("DELETE FROM menu_items");
        template.update("DELETE FROM users");
        connectionProvider.closeConnection();
    }

}