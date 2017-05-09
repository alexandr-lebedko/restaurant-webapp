package net.lebedko.dao.jdbc;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.connection.TestConnectionProvider;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.sql.SQLException;

import static net.lebedko.EntityGenerator.*;
import static net.lebedko.entity.dish.Dish.DishCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 04.05.2017.
 */
public class JdbcDishDaoTest {
    @ClassRule
    public static DataBaseResource dataBaseResource = new DataBaseResource();

    private TestConnectionProvider connectionProvider;
    private QueryTemplate template;
    private JdbcDishDao dishDao;

    @Before
    public void beforeTest() throws Exception{
        connectionProvider = dataBaseResource.getConnectionProvider();
        template = new QueryTemplate(connectionProvider);
        dishDao = new JdbcDishDao(template);
    }

    @After
    public void afterTest() throws Exception{
        template.update("DELETE FROM dishes");
        connectionProvider.closeConnection();
    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new Dish(null, null, null);
    }

    @Test
    public void findByIdTest() throws Exception {
        Dish dish = getDish();

        dish = dishDao.insert(dish);
        int id = dish.getId();


        Dish retrievedDish = dishDao.findById(id);
        assertThat("Retrieved and initial dish should be equal!",
                retrievedDish, is(dish));
    }


    @Test(expected = RuntimeException.class)
    public void insertInvalidDishTest()throws Exception  {
        Dish dish = mock(Dish.class);
        when(dish.isValid()).thenReturn(false);

        dishDao.insert(dish);
    }

    @Test
    public void insertValidDishTest() throws Exception {
        Dish dish = getDish();
        dish = dishDao.insert(dish);

        assertThat("Retrieved and initial dish should be equal!",
                dishDao.findById(dish.getId()), equalTo(dish));
    }

    @Test(expected = DataAccessException.class)
    public void insertSameDishWithSameTitleAndCategoryTwice() throws Exception {
        Title title = getTitle();
        DishCategory category = getDishCategory();

        Dish dishOne = new Dish(title, getDescription(), category);
        Dish dishTwo = new Dish(title, getDescription(), category);

        dishDao.insert(dishOne);
        dishDao.insert(dishTwo);
    }

    @Test
    public void deleteTest() throws Exception {
        Dish dish = getDish();
        dish = dishDao.insert(dish);

        int id = dish.getId();
        assertThat("Retrieved and initial dishes should be equals",
                dishDao.findById(id), is(dish));

        dishDao.delete(id);

        assertNull("Retrieved Dish should be null!", dishDao.findById(id));
    }

    @Test(expected = RuntimeException.class)
    public void updateWithInvalidDishArgumentTest() throws Exception {
        Dish dish = mock(Dish.class);
        when(dish.isValid()).thenReturn(false);

        dishDao.update(dish);
    }
}