package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Price;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class ItemInfo implements Validatable {

    private Title title;
    private Description description;
    private ItemCategory category;
    private Price price;

    public ItemInfo(Title title, Description description, ItemCategory category, Price price) {
        this.title = requireNonNull(title);
        this.description = requireNonNull(description);
        this.category = requireNonNull(category);
        this.price = requireNonNull(price);
    }

    @Override
    public boolean isValid() {
        return title.isValid()
                && description.isValid()
                && category.isValid()
                && price.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemInfo itemInfo = (ItemInfo) o;

        return Objects.equals(title, itemInfo.title) &&
                Objects.equals(description, itemInfo.description) &&
                Objects.equals(category, itemInfo.category) &&
                Objects.equals(price, itemInfo.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, category, price);
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "title=" + title +
                ", description=" + description +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
