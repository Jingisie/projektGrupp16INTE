package se.grupp.projekt;

@FunctionalInterface
public interface DiscountCalculator {
    // Räknar ut rabatt på ett belopp.
    Money calculate(Money subtotal, int percentage);
}
