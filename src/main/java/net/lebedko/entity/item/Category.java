package net.lebedko.entity.item;

import net.lebedko.entity.general.StringI18N;

import java.util.Objects;

public class Category {
    private Long id;
    private StringI18N title;

    public Category(StringI18N title) {
        this(0L, title);
    }

    public Category(Long id, StringI18N title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StringI18N getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return title.toString();
    }
}