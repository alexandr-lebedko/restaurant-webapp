package net.lebedko.entity.item;

/**
 * alexandr.lebedko : 14.09.2017.
 */
public class ItemView {
    private long id;
    private String title;
    private String description;
    private double price;
    private String pictureId;
    private String category;
    private String state;

    public ItemView() {
    }

    public ItemView(long id,
                    String title,
                    String description,
                    double price,
                    String pictureId,
                    String category,
                    String state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.pictureId = pictureId;
        this.category = category;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
