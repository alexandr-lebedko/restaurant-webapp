package net.lebedko.entity.item;

import net.lebedko.entity.general.Price;

import java.util.Objects;

public class Item {
    private Long id;
    private Title title;
    private Description description;
    private Category category;
    private Price price;
    private String imageId;

    public Item(Title title, Description description, Category category, Price price, String imageId) {
        this(null, title, description,category, price, imageId);
    }

    public Item(Long id, Title title, Description description, Category category, Price price, String imageId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageId = imageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getImageId() {
        return imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(title, item.title) &&
                Objects.equals(description, item.description) &&
                Objects.equals(category, item.category) &&
                Objects.equals(price, item.price) &&
                Objects.equals(imageId, item.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, category, price, imageId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title=" + title +
                ", price=" + price +
                '}';
    }
}