package net.lebedko.entity.general;

import java.util.Objects;

import static java.util.Optional.ofNullable;

public class Price {
    private Double value;

    public Price(Double value) {
        this.value = ofNullable(value).orElse(-1.);
        trim();
    }

    private void trim() {
        value = ((int) (value * 100)) / 100.0;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Price{" +
                +value +
                '}';
    }

    public static Price sum(Price price1, Price price2) {
        return new Price(price1.getValue() + price2.getValue());
    }
}
