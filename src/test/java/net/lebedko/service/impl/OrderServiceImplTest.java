package net.lebedko.service.impl;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.OrderDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderBucket;
import net.lebedko.service.OrderItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private TransactionManager txManager;

    @Mock
    private InvoiceService invoiceService;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private OrderDao orderDao;

    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl(txManager, invoiceService, orderItemService, orderDao);
    }

    @Test
    public void getById() throws Exception {
        Long id = 123L;
        Order expected = EntityGenerator.getOrder();

        when(orderDao.findById(id)).thenReturn(expected);

        Order actual = orderService.getById(id);

        assertEquals(expected, actual);
    }


    @Test
    public void getByUserAndId() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();

        Order expected = EntityGenerator.getOrder();

        when(orderDao.getByOrderIdAndUser(id, user)).thenReturn(expected);

        Order actual = orderService.getByUserAndId(id, user);

        assertEquals(expected, actual);
    }

    @Test
    public void getByState() throws Exception {
        OrderState orderState = EntityGenerator.getOrderState();

        orderService.getByState(orderState);

        verify(orderDao).getByState(orderState);
    }

    @Test
    public void getPagedByUser() throws Exception {
        User user = EntityGenerator.getUser();
        Pageable pageable = EntityGenerator.getPageable();

        orderService.getByUser(user, pageable);

        verify(orderDao).getByUser(user, pageable);
    }

    @Test
    public void getByInvoice() throws Exception {
        Invoice invoice = EntityGenerator.getInvoice();
        Collection<Order> expected = Arrays.asList(new Order(invoice), new Order(invoice), new Order(invoice));

        when(orderDao.getByInvoice(invoice))
                .thenReturn(expected);

        Collection<Order> actual = orderService.getByInvoice(invoice);

        assertEquals(expected, actual);
    }

    @Test
    public void getPagedByState() throws Exception {
        OrderState orderState = EntityGenerator.getOrderState();
        Pageable pageable = EntityGenerator.getPageable();

        orderService.getByState(orderState, pageable);

        verify(orderDao).getByState(orderState, pageable);
    }


    @Test
    public void createOrder() throws Exception {
        User user = EntityGenerator.getUser();
        OrderBucket orderBucket = EntityGenerator.getOrderBucket();
        Invoice invoice = EntityGenerator.getInvoice();

        when(invoiceService.getUnpaidOrCreate(user))
                .thenReturn(invoice);

        orderService.createOrder(user, orderBucket);

        verify(invoiceService).getUnpaidOrCreate(user);
        verify(orderDao).insert(any(Order.class));
        verify(orderItemService, times(orderBucket.getContent().size())).insert(any(OrderItem.class));
    }

    @Test(expected = IllegalStateException.class)
    public void process_alreadyProcessedOrder() throws Exception {
        Long id = 123L;
        Order order = new Order(id, EntityGenerator.getInvoice(), OrderState.PROCESSED, LocalDateTime.now());

        when(orderDao.findById(id))
                .thenReturn(order);

        orderService.process(id);
    }

    @Test
    public void process_NewOrder() throws Exception {
        Long id = 123L;
        Order order = new Order(id, EntityGenerator.getInvoice(), OrderState.NEW, LocalDateTime.now());

        when(orderDao.findById(id))
                .thenReturn(order);

        orderService.process(id);

        verify(invoiceService).update(any(Invoice.class));
        verify(orderDao).update(any(Order.class));
    }

    @Test
    public void reject() throws Exception {
        Long id = 123L;
        Order order = new Order(id, EntityGenerator.getInvoice(), OrderState.NEW, LocalDateTime.now());

        when(orderDao.findById(id))
                .thenReturn(order);

        orderService.reject(id);

        verify(orderDao).update(any(Order.class));
    }

    @Test(expected = IllegalStateException.class)
    public void deleteByIdAndUser_processedOrRejectedOrder() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();

        when(orderDao.getByOrderIdAndUser(id, user))
                .thenReturn(new Order(id, EntityGenerator.getInvoice(), OrderState.PROCESSED, LocalDateTime.now()));

        orderService.delete(id, user);
    }

    @Test
    public void deleteByIdAndUser_modifiedOrNewOrder() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();

        when(orderDao.getByOrderIdAndUser(id, user))
                .thenReturn(new Order(id, EntityGenerator.getInvoice(), OrderState.NEW, LocalDateTime.now()));

        orderService.delete(id, user);

        verify(orderDao).delete(id);
    }

    @Test(expected = IllegalStateException.class)
    public void submitModifiedOrder_orderNotModified() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();
        Order order = new Order(id, new Invoice(user), OrderState.NEW, LocalDateTime.now());

        when(orderDao.getByOrderIdAndUser(id, user))
                .thenReturn(order);

        orderService.submitModifiedOrder(id, user);
    }

    @Test
    public void submitModifiedOrder() throws Exception {
        Long id = 123L;
        User user = EntityGenerator.getUser();
        Order order = new Order(id, new Invoice(user), OrderState.MODIFIED, LocalDateTime.now());

        when(orderDao.getByOrderIdAndUser(id, user))
                .thenReturn(order);

        orderService.submitModifiedOrder(id, user);

        verify(orderDao).getByOrderIdAndUser(id, user);
        verify(orderDao).update(any(Order.class));
    }


    @Test(expected = IllegalStateException.class)
    public void modify_notInNewState() throws Exception {
        Order order = new Order(EntityGenerator.getLong(), EntityGenerator.getInvoice(), OrderState.MODIFIED, LocalDateTime.now());
        Collection<OrderItem> orderItems = Collections.singletonList(
                new OrderItem(order, EntityGenerator.getItem(), EntityGenerator.getLong()));

        orderService.modify(orderItems);
    }

    @Test
    public void modify() throws Exception {
        Order order = new Order(
                EntityGenerator.getLong(),
                EntityGenerator.getInvoice(),
                OrderState.NEW,
                LocalDateTime.now());

        Collection<OrderItem> orderItems = Arrays.asList(
                new OrderItem(order, EntityGenerator.getItem(), EntityGenerator.getLong()),
                new OrderItem(order, EntityGenerator.getItem(), EntityGenerator.getLong()),
                new OrderItem(order, EntityGenerator.getItem(), EntityGenerator.getLong()));

        orderService.modify(orderItems);

        verify(orderItemService).deleteByOrder(order);
        verify(orderItemService, times(orderItems.size())).insert(any(OrderItem.class));
        verify(orderDao).update(any(Order.class));
    }
}