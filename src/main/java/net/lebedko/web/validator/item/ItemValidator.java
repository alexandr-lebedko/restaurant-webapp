package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Item;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

public class ItemValidator implements Validator<Item> {
    private TitleValidator titleValidator;
    private DescriptionValidator descriptionValidator;
    private PriceValidator priceValidator;

    public ItemValidator(TitleValidator titleValidator, DescriptionValidator descriptionValidator, PriceValidator priceValidator) {
        this.titleValidator = titleValidator;
        this.descriptionValidator = descriptionValidator;
        this.priceValidator = priceValidator;
    }

    public ItemValidator() {
        this(new TitleValidator(), new DescriptionValidator(), new PriceValidator());
    }

    @Override
    public void validate(Item item, Errors errors) {
        titleValidator.validate(item.getTitle(), errors);
        descriptionValidator.validate(item.getDescription(), errors);
        priceValidator.validate(item.getPrice(), errors);
    }
}