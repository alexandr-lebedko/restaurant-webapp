package net.lebedko.entity.general;

import net.lebedko.entity.Validatable;

import java.util.Objects;

/**
 * alexandr.lebedko : 21.03.2017.
 */

public class Price implements Validatable {

    private static final int ACCURACY = 100;

    private final double amount;

    public Price(double amount) {
        if (!Double.isNaN(amount) && !Double.isInfinite(amount))
            this.amount = (double) Math.round(amount * ACCURACY) / ACCURACY;
        else
            this.amount = amount;
    }

    public double getDoubleValue() {
        return amount;
    }

    public Price sum(Price other) {
        if (other == null && !other.isValid()) {
            throw new IllegalArgumentException("Price either is null or invalid!");
        }
        return new Price(this.amount + other.amount);
    }

    public Price multiplyBy(int times) {
        if (times <= 0)
            throw new IllegalArgumentException("Price cannot be multiplied neither by zero nor by negative number!");
        return new Price(this.amount * times);
    }


    @Override
    public boolean isValid() {
        return amount >= 0 &&
                amount != Double.NaN &&
                !Double.isInfinite(amount) &&
                !Double.isNaN(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "Price=" + getDoubleValue();
    }
}
