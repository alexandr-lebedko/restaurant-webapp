package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;

import java.util.Objects;

/**
 * alexandr.lebedko : 30.07.2017.
 */

public class ItemCategory implements Validatable {

    private long id;

    private Title value;

    public ItemCategory(String value) {
        this.value = new Title(value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return value.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ItemCategory that = (ItemCategory) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
