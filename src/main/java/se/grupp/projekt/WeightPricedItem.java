package se.grupp.projekt;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeightPricedItem {

    private final String name;
    private final Money pricePerUnit;
    private final Unit priceUnit;

    public WeightPricedItem(String name, Money pricePerUnit, Unit priceUnit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (pricePerUnit == null || pricePerUnit.getAmountInOre() < 0) {
            throw new IllegalArgumentException("Price cannot be null or negative");
        }
        if (priceUnit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.priceUnit = priceUnit;
    }

    public String getName() {
        return name;
    }


    public Money getPriceFor(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        BigDecimal ore = BigDecimal.valueOf(weight.in(priceUnit))
                .multiply(BigDecimal.valueOf(pricePerUnit.getAmountInOre()))
                .setScale(0, RoundingMode.HALF_UP);
        return new Money(ore.longValueExact());
    }
}