package net.lebedko.entity.item;

/**
 * alexandr.lebedko : 05.08.2017.
 */
public class CategoryView {
    private long id;
    private String name;
    private String imageId;

    public CategoryView(long id, String name, String imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return "CategoryView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
