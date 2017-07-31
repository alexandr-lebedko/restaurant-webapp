package net.lebedko.dao.jdbc;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.connection.TestConnectionProvider;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.menu.MenuItem;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import org.junit.*;

import java.util.List;

import static net.lebedko.EntityGenerator.*;
import static net.lebedko.entity.order.Order.OrderStatus.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 04.05.2017.
 */
public class JdbcOrderDaoTest {

    @ClassRule
    public static DataBaseResource dataBaseResource = new DataBaseResource();

    private TestConnectionProvider connectionProvider;
    private QueryTemplate template;
    private JdbcOrderDao orderDao;

    private MenuItem menuItemOne;
    private MenuItem menuItemTwo;

    private User user;

    @Before
    public void setUp() throws Exception {
        connectionProvider = dataBaseResource.getConnectionProvider();
        template = new QueryTemplate(connectionProvider);

        orderDao = new JdbcOrderDao(template);
        JdbcDishDao dishDao = new JdbcDishDao(template);
        JdbcMenuDao menuDao = new JdbcMenuDao(template);
        JdbcUserDao userDao = new JdbcUserDao(template);

        user = new User(getFullName(), getEmailAddresses()[0], getPassword(), getUserRole());
        userDao.insert(user);

        Dish dishOne = new Dish(getTitles()[0], getDescription(), getDishCategory());
        Dish dishTwo = new Dish(getTitles()[1], getDescription(), getDishCategory());

        dishDao.insert(dishOne);
        dishDao.insert(dishTwo);

        menuItemOne = new MenuItem(dishOne, getPrice(), getBoolean());
        menuItemTwo = new MenuItem(dishTwo, getPrice(), getBoolean());

        menuDao.insert(menuItemOne);
        menuDao.insert(menuItemTwo);
    }

    @After
    public void tearDown() throws Exception {
        template.update("DELETE FROM users");
        template.update("DELETE FROM order_items");
        template.update("DELETE FROM orders");
        template.update("DELETE FROM menu_items");
        template.update("DELETE FROM dishes");
        connectionProvider.closeConnection();
    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new JdbcOrderDao(null);
    }

    @Test(expected = RuntimeException.class)
    public void insertInvalidOrderTest() throws Exception {
        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(false);
        orderDao.insert(order);
    }

    @Test
    public void insertAndFindByIdTest() throws Exception {
        Order order = new Order(user, getOrderStatus());

        orderDao.insert(order);
        int id = order.getId();

        Order retrievedOrder = orderDao.findById(id);
        assertThat("Retrieved and initial orders should be equal!",
                retrievedOrder, is(order));
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateWithInvalidOrderArgumentTest() throws Exception {
        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(false);

        orderDao.update(order);
    }

    @Test
    public void updateValidOrderTest() throws Exception {
        Order initialOrder = new Order(user, ACTIVE);

        orderDao.insert(initialOrder);
        int id = initialOrder.getId();

        Order updatedOrder = new Order(id, user, FINISHED);
        orderDao.update(updatedOrder);

        Order retrievedOrder = orderDao.findById(id);
        assertThat("Retrieved order should be equal to updated and not to initial orders!",
                retrievedOrder,
                allOf(
                        is(updatedOrder),
                        not(initialOrder))
        );
    }

    @Test
    public void deleteOrderTest() throws Exception {
        Order order = new Order(user, ACTIVE);
        orderDao.insert(order);

        int id = order.getId();

        orderDao.delete(id);

        Order fetchedOrder = orderDao.findById(id);
        Assert.assertNull("Fetched order should be equal to null!", fetchedOrder);
    }

    @Test
    public void findOrdersByStatusTest() throws Exception {
        Order activeOrder = new Order(user, ACTIVE);
        Order finishedOrder = new Order(user, FINISHED);
        Order closedOrder = new Order(user, CLOSED);

        orderDao.insert(activeOrder);
        orderDao.insert(activeOrder);

        orderDao.insert(finishedOrder);
        orderDao.insert(finishedOrder);
        orderDao.insert(finishedOrder);

        orderDao.insert(closedOrder);


        List<Order> activeOrders = orderDao.findOrdersByStatus(ACTIVE);
        List<Order> finishedOrders = orderDao.findOrdersByStatus(FINISHED);
        List<Order> closedOrders = orderDao.findOrdersByStatus(CLOSED);

        int activeOrdersSize = activeOrders.size();
        int finishedOrdersSize = finishedOrders.size();
        int closedOrdersSize = closedOrders.size();

        assertThat("Active orders list size should be equal to 2", activeOrdersSize, is(2));
        assertThat("Finished orders list size should be equal to 3", finishedOrdersSize, is(3));
        assertThat("Closed orders list size should be equal to 1", closedOrdersSize, is(1));
    }


    @Test
    public void insertAndGetOrderItemTest() throws Exception {
        Order order = new Order(user, ACTIVE);
        OrderItem orderItemOne = new OrderItem(order, menuItemOne, 2, false);
        OrderItem orderItemTwo = new OrderItem(order, menuItemTwo, 1, false);

        orderDao.insert(order);
        orderDao.insertOrderItem(orderItemOne);
        orderDao.insertOrderItem(orderItemTwo);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int size = orderItems.size();
        assertThat("Order Items list size should be equal to 2!", size, is(2));
    }

    @Test(expected = DataAccessException.class)
    public void insertOrderItemWithNonPersistedOrder() throws Exception {
        Order order = new Order(user, getOrderStatus());
        OrderItem orderItem = new OrderItem(order, menuItemTwo, 2, getBoolean());
        orderDao.insertOrderItem(orderItem);
    }

    @Test
    public void insertOrderItemsWithDifferentMenuItemsAndStatus() throws Exception {
        Order order = new Order(user, ACTIVE);
        OrderItem orderItemOne = new OrderItem(order, menuItemOne, 2, getBoolean());
        OrderItem orderItemTwo = new OrderItem(order, menuItemTwo, 2, getBoolean());

        orderDao.insert(order);
        orderDao.insertOrderItem(orderItemOne);
        orderDao.insertOrderItem(orderItemTwo);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int size = orderItems.size();

        assertThat("Order items list should contain three elements!", size, is(2));
    }

    @Test
    public void insertOrderItemsWithSameMenuItemAndDifferentStatus() throws DataAccessException {
        Order order = new Order(user, getOrderStatus());
        OrderItem orderItemOne = new OrderItem(order, menuItemOne, 2, true);
        OrderItem orderItemTwo = new OrderItem(order, menuItemOne, 2, false);

        orderDao.insert(order);
        orderDao.insertOrderItem(orderItemOne);
        orderDao.insertOrderItem(orderItemTwo);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int size = orderItems.size();

        assertThat("Order item list should contain two elements", size, is(2));
    }

    @Test
    public void insertOrderItemsWithSameMenuItemAndSameStatus() throws DataAccessException {
        Order order = new Order(user, getOrderStatus());
        OrderItem orderItemOne = new OrderItem(order, menuItemOne, 10, true);
        OrderItem orderItemTwo = new OrderItem(order, menuItemOne, 5, true);

        int totalQuantity = orderItemOne.getQuantity() + orderItemTwo.getQuantity();

        orderDao.insert(order);
        orderDao.insertOrderItem(orderItemOne);
        orderDao.insertOrderItem(orderItemTwo);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int size = orderItems.size();

        assertThat("Order item list should contain one element!", size, is(1));

        OrderItem fetchedOrderItem = orderItems.get(0);
        int fetchedQuantity = fetchedOrderItem.getQuantity();

        assertThat("Fetched order item quantity value should be equal to totalQuantity",
                fetchedQuantity, is(totalQuantity));
    }

    @Test
    public void updateOrderItemTest() throws DataAccessException {
        Order order = new Order(user, getOrderStatus());
        int initialQuantity = 5;
        boolean initialStatus = true;
        OrderItem initial = new OrderItem(order, menuItemOne, initialQuantity, initialStatus);

        orderDao.insert(order);
        orderDao.insertOrderItem(initial);
        int id = initial.getId();

        OrderItem updated = new OrderItem(id, order, menuItemTwo, initialQuantity + 5, !initialStatus);
        orderDao.updateOrderItem(updated);

        OrderItem fetchedOrderItem = orderDao.getOrderItemsByOrder(order).get(0);

        assertThat("Fetched order items is equals to updated and not equals to initial order items",
                fetchedOrderItem, allOf(
                        is(updated),
                        not(initial)
                ));
    }


    @Test
    public void updateOrderWithSameMenuItemsAndDifferentProcessedStatus() throws DataAccessException {
        Order order = new Order(user, getOrderStatus());
        OrderItem processed = new OrderItem(order, menuItemOne, 10, true);
        OrderItem unprocessed = new OrderItem(order, menuItemOne, 5, false);

        int totalQuantity = processed.getQuantity() + unprocessed.getQuantity();

        orderDao.insert(order);
        orderDao.insertOrderItem(processed);
        orderDao.insertOrderItem(unprocessed);

        OrderItem updatedUnprocessed = new OrderItem(unprocessed.getId(), order, menuItemOne, 5, true);

        orderDao.updateOrderItem(updatedUnprocessed);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int fetchedQuantity = orderItems.get(0).getQuantity();
        int size = orderItems.size();

        assertThat("Two order items with same order, menu item and OrderItem#isProcessed() should be merged into one",
                size, is(1));

        assertThat("Quantity of two order items with same order, menu item and OrderItem#isProcessed() should be summed",
                fetchedQuantity, is(totalQuantity));
    }

    @Test
    public void deleteOrderItemTest() throws DataAccessException {
        Order order = new Order(user, ACTIVE);
        OrderItem orderItemOne = new OrderItem(order, menuItemOne, 3, getBoolean());
        OrderItem orderItemTwo = new OrderItem(order, menuItemTwo, 7, getBoolean());

        orderDao.insert(order);
        orderDao.insertOrderItem(orderItemOne);
        orderDao.insertOrderItem(orderItemTwo);

        orderDao.deleteOrderItem(orderItemOne.getId());

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrder(order);
        int size = orderItems.size();

        assertThat("Order item list should contain one element", size, is(1));


        orderDao.deleteOrderItem(orderItemTwo.getId());

        orderItems = orderDao.getOrderItemsByOrder(order);
        size = orderItems.size();

        assertThat("Order item list should contain zero elements", size, is(0));
    }

    public void deleteOrderAndCascadeOrderItemsTest() {
        //TODO
    }
}