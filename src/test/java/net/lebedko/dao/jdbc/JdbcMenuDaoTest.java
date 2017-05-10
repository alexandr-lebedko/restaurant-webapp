package net.lebedko.dao.jdbc;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.EntityExistsException;
import net.lebedko.dao.connection.TestConnectionProvider;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Title;
import net.lebedko.entity.menu.Menu;
import net.lebedko.entity.menu.MenuItem;
import org.junit.*;

import static net.lebedko.EntityGenerator.*;
import static net.lebedko.entity.dish.Dish.DishCategory.DESSERT;
import static net.lebedko.entity.dish.Dish.DishCategory.MAIN;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 04.05.2017.
 */
public class JdbcMenuDaoTest {

    @ClassRule
    public static final DataBaseResource dataBaseResource = new DataBaseResource();

    private TestConnectionProvider connectionProvider;
    private QueryTemplate template;
    private JdbcDishDao dishDao;
    private JdbcMenuDao menuDao;

    private Dish dishOne;
    private Dish dishTwo;
    private Dish dishThree;

    @Before
    public void beforeTest() throws Exception {
        connectionProvider = dataBaseResource.getConnectionProvider();

        template = new QueryTemplate(connectionProvider);
        dishDao = new JdbcDishDao(template);
        menuDao = new JdbcMenuDao(template);
        dishOne = new Dish(getTitles()[0], getDescription(), getDishCategory());
        dishTwo = new Dish(getTitles()[1], getDescription(), getDishCategory());
        dishThree = new Dish(getTitles()[2], getDescription(), getDishCategory());
        dishDao.insert(dishOne);
        dishDao.insert(dishTwo);
        dishDao.insert(dishThree);
    }

    @After
    public void afterTest() throws Exception{
        template.update("DELETE FROM menu_items");
        template.update("DELETE FROM dishes");
        connectionProvider.closeConnection();
    }

    @Test(expected = RuntimeException.class)
    public void insertWithInvalidMenuItemArgumentTest() throws Exception {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.isValid()).thenReturn(false);
        menuDao.insert(menuItem);
    }

    @Test
    public void insertAndFindByIdTest() throws Exception {

        MenuItem menuItem = new MenuItem(dishOne, getPrice(), getBoolean());
        menuItem = menuDao.insert(menuItem);

        MenuItem retrievedMenuItem = menuDao.findById(menuItem.getId());
        assertThat("Retrieved and initial menu items should be equal",
                retrievedMenuItem, equalTo(menuItem));
    }


    @Test(expected = EntityExistsException.class)
    public void insertMenuItemsWithSameDish() throws Exception {

        MenuItem menuItem = new MenuItem(dishOne, getPrice(), getBoolean());
        MenuItem anotherMenuItem = new MenuItem(dishOne, getPrice(), getBoolean());

        menuDao.insert(menuItem);
        menuDao.insert(anotherMenuItem);
    }


    @Test
    public void updateTest() throws Exception {

        MenuItem initialItem = new MenuItem(dishOne, getPrice(), getBoolean());
        menuDao.insert(initialItem);
        int id = initialItem.getId();

        MenuItem updatedItem = new MenuItem(id, dishTwo, getPrice(), getBoolean());
        menuDao.update(updatedItem);

        MenuItem retrievedItem = menuDao.findById(id);
        assertThat("Retrieved value should be equal to updated value and not equal to initial value",
                retrievedItem,
                allOf(
                        equalTo(updatedItem), not(equalTo(initialItem))
                ));
    }

    @Test(expected = DataAccessException.class)
    public void updateViolatingUniqueConstraint() throws Exception {
        MenuItem menuItemOne = new MenuItem(dishOne, getPrice(), getBoolean());
        MenuItem menuItemTwo = new MenuItem(dishTwo, getPrice(), getBoolean());

        menuDao.insert(menuItemOne);
        menuDao.insert(menuItemTwo);

        int id = menuItemTwo.getId();
        MenuItem updatedMenuItemTwo = new MenuItem(id, dishOne, getPrice(), getBoolean());
        menuDao.update(updatedMenuItemTwo);
    }

    @Test(expected = RuntimeException.class)
    public void updateWithInvalidMenuItemArgumentTest() throws Exception {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.isValid()).thenReturn(false);

        menuDao.update(menuItem);
    }

    @Test
    public void deleteTest() throws Exception {
        MenuItem menuItem = new MenuItem(dishOne, getPrice(), getBoolean());
        menuDao.insert(menuItem);

        int id = menuItem.getId();
        menuDao.delete(id);

        MenuItem retrievedMenuItem = menuDao.findById(id);
        Assert.assertNull("Retrieved Item should be null!", retrievedMenuItem);
    }

    @Test
    public void getAllMenuTest() throws Exception {
        MenuItem menuItemOne = new MenuItem(dishOne, getPrice(), true);
        MenuItem menuItemTwo = new MenuItem(dishTwo, getPrice(), false);
        MenuItem menuItemThree = new MenuItem(dishThree, getPrice(), getBoolean());

        menuDao.insert(menuItemOne);
        menuDao.insert(menuItemTwo);
        menuDao.insert(menuItemThree);

        Menu menu = menuDao.getAllMenu();
        int size = menu.getMenuContent().size();
        assertThat("Size of menu content should equal to 3!", size, is(3));
    }

    @Test
    public void getActiveMenu() throws Exception {
        MenuItem activeItemOne = new MenuItem(dishOne, getPrice(), true);
        MenuItem activeItemTwo = new MenuItem(dishTwo, getPrice(), true);

        MenuItem inactiveItem = new MenuItem(dishThree, getPrice(), false);


        menuDao.insert(activeItemOne);
        menuDao.insert(activeItemTwo);
        menuDao.insert(inactiveItem);

        Menu menu = menuDao.getActiveMenu();
        int size = menu.getMenuContent().size();

        assertThat("Menu content size should be equal to 2!", size, is(2));
    }

    @Test
    public void getStopListMenuTest() throws Exception {
        MenuItem inactiveItemOne = new MenuItem(dishOne, getPrice(), false);
        MenuItem inactiveItemTwo = new MenuItem(dishTwo, getPrice(), false);
        MenuItem activeItem = new MenuItem(dishThree, getPrice(), true);

        menuDao.insert(inactiveItemOne);
        menuDao.insert(inactiveItemTwo);
        menuDao.insert(activeItem);

        Menu stopList = menuDao.getStopListMenu();
        int size = stopList.getMenuContent().size();

        assertThat("Size of stop list menu should be equal to 2",
                size, is(2));
    }

    @Test
    public void getByCategory() throws Exception {
        Dish dessert = new Dish(new Title("Panna Cotta"), getDescription(), DESSERT);
        Dish mainOne = new Dish(new Title("Ocean Crab"), getDescription(), MAIN);
        Dish mainTwo = new Dish(new Title("Creamy scallops"), getDescription(), MAIN);

        dishDao.insert(mainOne);
        dishDao.insert(mainTwo);
        dishDao.insert(dessert);

        menuDao.insert(new MenuItem(dessert, getPrice(), getBoolean()));
        menuDao.insert(new MenuItem(mainOne, getPrice(), getBoolean()));
        menuDao.insert(new MenuItem(mainTwo, getPrice(), getBoolean()));

        Menu mainMenu = menuDao.getByCategory(MAIN);
        Menu dessertMenu = menuDao.getByCategory(DESSERT);

        int mainMenuSize = mainMenu.getMenuContent().size();
        int dessertMenuSize = dessertMenu.getMenuContent().size();

        assertThat("Main category menu size should be equal to 2!",
                mainMenuSize, is(2));
        assertThat("Dessert menu size should be equal to 1",
                dessertMenuSize, is(1));
    }

}