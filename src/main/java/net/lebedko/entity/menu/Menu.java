package net.lebedko.entity.menu;


import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static net.lebedko.entity.dish.Dish.DishCategory;

/**
 * alexandr.lebedko : 22.03.2017.
 */

public class Menu {

    private final Set<MenuItem> menuContent;

    public Menu(Collection<MenuItem> menuContent) {
        this.menuContent = requireNonNull(menuContent).stream().
                filter(Objects::nonNull).
                collect(Collectors.toSet());

    }

    public Set<MenuItem> getMenuContent() {
        return Collections.unmodifiableSet(menuContent);
    }

    public Set<MenuItem> getByDishCategory(DishCategory dishType) {
        return menuContent.stream().
                filter(mi -> mi.getDish().getCategory() == dishType).
                collect(Collectors.toSet());
    }

    public Set<MenuItem> getByActivity(boolean isActive) {
        return menuContent.stream().
                filter(mi -> mi.isActive() == isActive).
                collect(Collectors.toSet());
    }


    public void addMenuItem(MenuItem menuItem) {
        if (nonNull(menuItem))
            menuContent.add(menuItem);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuContent=" + menuContent +
                '}';
    }
}
