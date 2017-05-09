package net.lebedko.entity.menu;

import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Price;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 22.03.2017.
 */

public class MenuItem implements Entity, Validatable {

    private int id;
    private final Dish dish;
    private final Price price;
    private final boolean active;

    public MenuItem(Dish dish, Price price, boolean active) {
        this(0, dish, price, active);
    }

    public MenuItem(int id, Dish dish, Price price, boolean active) {
        this.id = id;
        this.dish = requireNonNull(dish, "Dish cannot be null!");
        this.price = requireNonNull(price, "Price cannot be null!");
        this.active = active;
    }

    public Dish getDish() {
        return dish;
    }

    public boolean isActive() {
        return active;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return dish.isValid() &&
                price.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(dish, menuItem.dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                " " + dish +
                ", active=" + active +
                '}';
    }
}
