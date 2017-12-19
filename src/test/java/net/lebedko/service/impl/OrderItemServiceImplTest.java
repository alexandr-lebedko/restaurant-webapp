package net.lebedko.service.impl;

import net.lebedko.EntityGenerator;
import net.lebedko.dao.OrderItemDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceImplTest {
    @Mock
    private TransactionManager txManager;

    @Mock
    private OrderItemDao orderItemDao;

    private OrderItemServiceImpl orderItemService;

    @Before
    public void setUp() {
        orderItemService = new OrderItemServiceImpl(txManager, orderItemDao);
    }

    @Test
    public void getOrderItemsByInvoice() throws Exception {
        Invoice invoice = EntityGenerator.getInvoice();

        orderItemService.getOrderItems(invoice);
        verify(orderItemDao).getByInvoice(invoice);
    }

    @Test
    public void getOrderItemsByOrder() throws Exception {
        Order order = EntityGenerator.getOrder();

        orderItemService.getOrderItems(order);
        verify(orderItemDao).getByOrder(order);
    }

    @Test
    public void insert() throws Exception {
        OrderItem orderItem = EntityGenerator.getOrderItem();

        orderItemService.insert(orderItem);
        verify(orderItemDao).insert(orderItem);
    }

    @Test
    public void delete() throws Exception {
        Collection<OrderItem> orderItems = Arrays.asList(EntityGenerator.getOrderItem(), EntityGenerator.getOrderItem());

        orderItemService.delete(orderItems);
        verify(orderItemDao).delete(orderItems);
    }

    @Test
    public void deleteByOrder() throws Exception {
        Order order = EntityGenerator.getOrder();

        orderItemService.deleteByOrder(order);
        verify(orderItemDao).deleteByOrder(order);
    }

    @Test
    public void update() throws Exception {
        Collection<OrderItem> orderItems = Arrays.asList(EntityGenerator.getOrderItem(), EntityGenerator.getOrderItem());

        orderItemService.update(orderItems);
        verify(orderItemDao).update(orderItems);
    }
}