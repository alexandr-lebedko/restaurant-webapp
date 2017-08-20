package net.lebedko.entity.demo.item;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class CategoryView {
    private long id;
    private String value;

    public CategoryView(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
