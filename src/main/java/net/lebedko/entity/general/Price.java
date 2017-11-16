package net.lebedko.entity.general;

import net.lebedko.entity.Validatable;

import static java.util.Optional.ofNullable;

/**
 * alexandr.lebedko : 30.07.2017.
 */

public class Price implements Validatable {
    private Double value;

    public Price(Double value) {
        this.value = ofNullable(value).orElse(-1.);
        trimToTwoDecimals();
    }

    private void trimToTwoDecimals() {
        value = ((int) (value * 100)) / 100.0;
    }

    @Override
    public boolean isValid() {
        return value >= 0;
    }

    public Double getValue() {
        return value;
    }

    public static Price sum(Price price1, Price price2) {
        return new Price(price1.getValue() + price2.getValue());
    }

    @Override
    public String toString() {
        return "Price{" +
                +value +
                '}';
    }
}
