package net.lebedko.entity.demo.item;

import net.lebedko.entity.Validatable;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class MenuItem implements Validatable {

    private long id;

    private ItemInfo info;
    private ItemState state;
    private String pictureId;

    public MenuItem(ItemInfo info, ItemState state, String pictureId) {
        this(0, info, state, pictureId);
    }


    public MenuItem(long id, ItemInfo info, ItemState state, String pictureId) {
        this.id = id;
        this.info = requireNonNull(info);
        this.state = requireNonNull(state);
        this.pictureId = requireNonNull(pictureId);
    }

    @Override
    public boolean isValid() {
        return info.isValid();
    }


}
