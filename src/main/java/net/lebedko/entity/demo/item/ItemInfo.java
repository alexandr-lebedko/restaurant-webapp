package net.lebedko.entity.demo.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.demo.general.Price;

import java.util.Objects;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class ItemInfo implements Validatable {

    private Title title;
    private Description description;
    private Category category;
    private Price price;

    public ItemInfo(Title title, Description description, Category category, Price price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    @Override
    public boolean isValid() {
        return nonNull(title)
                && nonNull(description)
                && nonNull(category)
                && nonNull(price)
                && title.isValid()
                && description.isValid()
                && category.isValid()
                && price.isValid();
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Price getPrice() {
        return price;
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
