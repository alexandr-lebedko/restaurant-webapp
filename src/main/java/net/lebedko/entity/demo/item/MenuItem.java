package net.lebedko.entity.demo.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.demo.general.Price;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 30.07.2017.
 */

//TODO: refactor class with Builder pattern
public class MenuItem implements Validatable {

    private long id;

    private ItemInfo info;
    private ItemState state;
    private String pictureId;

    public MenuItem(ItemInfo info, ItemState state) {
        this(0, info, state, null);
    }

    public MenuItem(long id, ItemInfo info, ItemState state, String pictureId) {
        this.id = id;
        this.info = info;
        this.state = state;
        this.pictureId = pictureId;
    }

    @Override
    public boolean isValid() {
        return nonNull(info)
                && nonNull(state)
                && info.isValid();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public long getId() {
        return id;
    }

    public ItemInfo getInfo() {
        return info;
    }

    public ItemState getState() {
        return state;
    }

    public String getPictureId() {
        return pictureId;
    }

    public Title getTitle() {
        return info.getTitle();
    }

    public Description getDescription() {
        return info.getDescription();
    }

    public Price getPrice() {
        return info.getPrice();
    }

    public Category getCategory() {
        return info.getCategory();
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", info=" + info +
                ", state=" + state +
                ", pictureId='" + pictureId + '\'' +
                '}';
    }
}
