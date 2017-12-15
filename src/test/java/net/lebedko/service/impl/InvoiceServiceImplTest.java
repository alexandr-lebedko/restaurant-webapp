package net.lebedko.service.impl;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.TransactionManagerStub;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceImplTest {
    private TransactionManager txManager = new TransactionManagerStub();

    @Mock
    private InvoiceDao invoiceDao;
    @Mock
    private OrderService orderService;
    @Mock
    private Invoice invoice;

    private InvoiceServiceImpl invoiceService;

    @Before
    public void setUp() {
        invoiceService = new InvoiceServiceImpl(txManager, invoiceDao);
        invoiceService.setOrderService(orderService);
    }

    @Test
    public void getInvoiceById() throws Exception {
        Long id = 123L;

        invoiceService.getInvoice(id);

        verify(invoiceDao).findById(id);
    }

    @Test
    public void getInvoiceByIdAndUser_invoiceExists() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();

        when(invoiceDao.findById(id))
                .thenReturn(new Invoice(user));

        invoiceService.getInvoice(id, user);

        verify(invoiceDao).findById(id);
    }


    @Test(expected = NoSuchElementException.class)
    public void getInvoiceByIdAndUser_InvoiceNotRelatesToUser() throws Exception {
        Long id = 123L;

        User oneUser = EntityGenerator.getUser();
        User anotherUser = EntityGenerator.getUser();

        assertNotEquals(oneUser, anotherUser);

        when(invoiceDao.findById(id))
                .thenReturn(new Invoice(oneUser));

        invoiceService.getInvoice(id, anotherUser);
    }

    @Test(expected = NoSuchElementException.class)
    public void getInvoiceByIdAndUser_InvoiceWithSuchIdNotExists() {
        Long id = 123L;

        when(invoiceDao.findById(id))
                .thenReturn(null);

        invoiceService.getInvoice(id, EntityGenerator.getUser());
    }

    @Test(expected = IllegalStateException.class)
    public void payInvoice_InvoiceAlreadyPaid() {
        Long id = 123L;
        User user = EntityGenerator.getUser();
        Invoice paidInvoice = new Invoice(
                id,
                user,
                InvoiceState.PAID,
                EntityGenerator.getPrice(),
                LocalDateTime.now());

        when(invoiceDao.findById(id))
                .thenReturn(paidInvoice);

        invoiceService.payInvoice(id, user);
    }

    @Test(expected = IllegalStateException.class)
    public void payInvoice_InvoiceHasUnprocessedOrders() {
        Long id = 123L;
        User user = EntityGenerator.getUser();
        Invoice invoice = new Invoice(
                id,
                user,
                InvoiceState.UNPAID,
                EntityGenerator.getPrice(),
                LocalDateTime.now());

        when(invoiceDao.findById(id))
                .thenReturn(invoice);

        when(orderService.getByInvoice(invoice))
                .thenReturn(asList(new Order(123L, invoice, OrderState.NEW, LocalDateTime.now())));

        invoiceService.payInvoice(id, user);
    }

    @Test(expected = NoSuchElementException.class)
    public void payInvoice_InvoiceWithSuchIdNotExists() {
        Long id = 123L;

        when(invoiceDao.findById(id))
                .thenReturn(null);

        invoiceService.payInvoice(id, EntityGenerator.getUser());
    }

    @Test(expected = NoSuchElementException.class)
    public void payInvoice_InvoiceNotRelatesToUser() {
        Long id = 123L;

        User oneUser = EntityGenerator.getUser();
        User anotherUser = EntityGenerator.getUser();

        when(invoiceDao.findById(id))
                .thenReturn(new Invoice(oneUser));

        invoiceService.payInvoice(id, anotherUser);
    }

    @Test
    public void payInvoice_InvoiceRelatesToUser_InvoiceUnpaid_OrderAreProcessed() {
        Long id = 123L;
        User user = EntityGenerator.getUser();
        Invoice invoice = new Invoice(user);

        when(invoiceDao.findById(id))
                .thenReturn(invoice);
        when(orderService.getByInvoice(invoice))
                .thenReturn(asList(new Order(id, invoice, OrderState.PROCESSED, LocalDateTime.now())));

        invoiceService.payInvoice(id, user);
    }

    @Test
    public void getInvoices() throws Exception {
        User user = EntityGenerator.getUser();
        Pageable pageable = EntityGenerator.getPageable();

        invoiceService.getInvoices(user, pageable);

        verify(invoiceDao).getByUser(user, pageable);
    }

    @Test
    public void getByState() throws Exception {
        InvoiceState state = EntityGenerator.getInvoiceState();
        Pageable pageable = EntityGenerator.getPageable();

        invoiceService.getByState(state, pageable);

        verify(invoiceDao).getByState(state, pageable);
    }

    @Test
    public void getUnpaidOrCreate_WhenUnpaidExists() throws Exception {
        User user = EntityGenerator.getUser();
        Invoice invoice = new Invoice(user, InvoiceState.UNPAID, EntityGenerator.getPrice(), LocalDateTime.now());

        when(invoiceDao.getByUserAndState(user, InvoiceState.UNPAID))
                .thenReturn(invoice);

        assertEquals(invoice, invoiceService.getUnpaidOrCreate(user));
    }

    @Test
    public void getUnpaidOrCreate_WhenUnpaidNotExists() {
        User user = EntityGenerator.getUser();
        Invoice expected = new Invoice(user);

        when(invoiceDao.getByUserAndState(user, InvoiceState.UNPAID))
                .thenReturn(null);
        when(invoiceDao.insert(any()))
                .thenReturn(expected);

        Invoice actual = invoiceService.getUnpaidOrCreate(user);

        assertEquals(actual, expected);
    }

    @Test
    public void update() throws Exception {
        invoiceService.update(invoice);

        verify(invoiceDao).update(invoice);
    }

    @Test
    public void delete() throws Exception {
        invoiceService.delete(invoice);

        verify(invoiceDao).delete(invoice.getId());
    }

}