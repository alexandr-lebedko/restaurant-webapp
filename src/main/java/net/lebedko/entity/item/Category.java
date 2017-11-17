package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.StringI18N;

import java.util.Objects;

/**
 * alexandr.lebedko : 30.07.2017.
 */

public class Category implements Validatable {

    private Long id;
    private Title value;
    private String imageId;


    public Category(StringI18N title) {
        this(0L, title, null);
    }

    public Category(StringI18N title, String imageId) {
        this(0L, title, imageId);
    }

    public Category(Long id, StringI18N title, String imageId) {
        this.id = id;
        this.value = new Title(title);
        this.imageId = imageId;
    }


    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return value.isValid();
    }

    public String getImageId() {
        return imageId;
    }

    //TODO::refactor method name
    public StringI18N getValue() {
        return value.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(value, category.value) &&
                Objects.equals(imageId, category.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, imageId);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
