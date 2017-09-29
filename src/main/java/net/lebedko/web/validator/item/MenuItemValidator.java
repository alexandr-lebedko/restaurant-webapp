package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Item;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class MenuItemValidator implements IValidator<Item> {
    private ItemInfoValidator itemInfoValidator;
    private ItemStateValidator itemStateValidator;

    public MenuItemValidator(ItemInfoValidator itemInfoValidator, ItemStateValidator itemStateValidator) {
        this.itemInfoValidator = itemInfoValidator;
        this.itemStateValidator = itemStateValidator;
    }
    public MenuItemValidator(){
        this(new ItemInfoValidator(), new ItemStateValidator());
    }

    @Override
    public void validate(Item item, Errors errors) {
        if (isNull(item)) {
            errors.register("nullMenuItem", PageErrorNames.NULL_MENU_ITEM);
            return;
        }

        if (item.isValid()) {
            return;
        }

        itemInfoValidator.validate(item.getInfo(), errors);
        itemStateValidator.validate(item.getState(), errors);
    }
}
