package se.grupp.projekt;

public class Weight implements Comparable<Weight> {

    private final double value;
    private final Unit unit;

    private Weight(double value, Unit unit) {
        if (value < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public static Weight of(double value, Unit unit) {
        return new Weight(value, unit);
    }

    public double in(Unit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        return value * unit.gramsPerUnit() / targetUnit.gramsPerUnit();
    }
    
    public static Weight add(Weight w1, Weight w2) {
        double totalGrams = w1.in(Unit.GRAM) + w2.in(Unit.GRAM);
        return Weight.of(totalGrams, Unit.GRAM);
    }
    
    @Override
    public int compareTo(Weight other) {
        return Double.compare(
            this.in(Unit.GRAM),
            other.in(Unit.GRAM)
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Weight other)) {
            return false;
        }

        return Double.compare(
            this.in(Unit.GRAM),
            other.in(Unit.GRAM)
        ) == 0;
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(in(Unit.GRAM));
    }
    
 
}