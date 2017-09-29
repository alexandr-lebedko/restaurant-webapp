package net.lebedko.web.validator.item;

import net.lebedko.entity.item.ItemInfo;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;


public class ItemInfoValidator implements IValidator<ItemInfo> {
    private TitleValidator titleValidator;
    private DescriptionValidator descriptionValidator;
    private PriceValidator priceValidator;
    private CategoryValidator categoryValidator;

    public ItemInfoValidator() {
        this(new TitleValidator(), new DescriptionValidator(), new PriceValidator(), new CategoryValidator());
    }

    public ItemInfoValidator(TitleValidator titleValidator, DescriptionValidator descriptionValidator, PriceValidator priceValidator, CategoryValidator categoryValidator) {
        this.titleValidator = titleValidator;
        this.descriptionValidator = descriptionValidator;
        this.priceValidator = priceValidator;
        this.categoryValidator = categoryValidator;
    }


    @Override
    public void validate(ItemInfo info, Errors errors) {
        if (isNull(info)) {
            errors.register("null-itemInfo", PageErrorNames.NULL_ITEM_INFO);
            return;
        }

        if (info.isValid()) {
            return;
        }

        titleValidator.validate(info.getTitle(), errors);
        descriptionValidator.validate(info.getDescription(), errors);
        priceValidator.validate(info.getPrice(), errors);
        categoryValidator.validate(info.getCategory(), errors);
    }

}
