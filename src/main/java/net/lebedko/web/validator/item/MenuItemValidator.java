package net.lebedko.web.validator.item;

import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class MenuItemValidator implements IValidator<MenuItem> {
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
    public void validate(MenuItem menuItem, Errors errors) {
        if (isNull(menuItem)) {
            errors.register("nullMenuItem", PageErrorNames.NULL_MENU_ITEM);
            return;
        }

        if (menuItem.isValid()) {
            return;
        }

        itemInfoValidator.validate(menuItem.getInfo(), errors);
        itemStateValidator.validate(menuItem.getState(), errors);
    }
}
