package net.lebedko.entity.dish;


import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Description;
import net.lebedko.entity.general.Title;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 18.03.2017.
 */

public class Dish implements Entity, Validatable {

    public enum DishCategory {
        MAIN, SIDE, SALAD, SOUP, STARTER, DESSERT
    }

    private int id;
    private Title title;
    private Description description;

    private DishCategory category;

    public Dish(Title title, Description description, DishCategory type) {
        this(0, title, description, type);
    }

    public Dish(int id, Title title, Description description, DishCategory type) {
        this.id = id;
        this.title = requireNonNull(title);
        this.description = requireNonNull(description);
        this.category = requireNonNull(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public DishCategory getCategory() {
        return category;
    }

    @Override
    public boolean isValid() {
        return title.isValid() &&
                description.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(title, dish.title) &&
                Objects.equals(description, dish.description) &&
                category == dish.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, category);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", " + title +
                ", " + description +
                ", " + category +
                '}';
    }
}
