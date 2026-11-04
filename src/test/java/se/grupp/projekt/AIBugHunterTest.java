
package se.grupp.projekt;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class AIBugHunterTest {

    private final Random random = new Random(12345);

    // 1. OGILTIGA VIKTER

    @Test
    void testInvalidWeights() {
        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(-1, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NaN, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.POSITIVE_INFINITY, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NEGATIVE_INFINITY, Unit.GRAM));
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(10, null));
    }

    @Test
    void testNegativeZero() {
        assertDoesNotThrow(() -> Weight.of(-0.0, Unit.GRAM));
    }

    // 2. EXTREMA VÄRDEN

    @Test
    void testVeryLargeWeight() {
        assertDoesNotThrow(
                () -> Weight.of(Double.MAX_VALUE, Unit.GRAM));
    }

    @Test
    void testAdditionOverflow() {
        Weight a = Weight.of(Double.MAX_VALUE, Unit.GRAM);
        Weight b = Weight.of(Double.MAX_VALUE, Unit.GRAM);

        assertThrows(IllegalArgumentException.class,
                () -> Weight.add(a, b));
    }

    @Test
    void testVerySmallWeights() {
        Weight a = Weight.of(0.000001, Unit.GRAM);
        Weight b = Weight.of(0.000002, Unit.GRAM);

        Weight result = Weight.add(a, b);

        assertEquals(0.000003,
                result.in(Unit.GRAM), 1e-12);
    }

    @Test
    void testAddingZero() {
        Weight a = Weight.of(123.45, Unit.GRAM);
        Weight zero = Weight.of(0, Unit.KILO);

        Weight result = Weight.add(a, zero);

        assertEquals(123.45, result.in(Unit.GRAM), 1e-8);
    }

    // 3. ENHETSKONVERTERING

    @Test
    void testAllUnitConversions() {
        for (Unit from : Unit.values()) {
            for (Unit to : Unit.values()) {
                Weight original = Weight.of(123.456, from);

                double converted = original.in(to);

                Weight convertedBack = Weight.of(converted, to);

                assertEquals(
                        original.in(Unit.GRAM),
                        convertedBack.in(Unit.GRAM),
                        1e-8
                );
            }
        }
    }

    @Test
    void testUnitConversionRoundTripRandom() {
        for (int i = 0; i < 1000; i++) {
            Unit unit = Unit.values()[
                    random.nextInt(Unit.values().length)];

            double value = random.nextDouble() * 100000;

            Weight original = Weight.of(value, unit);

            for (Unit target : Unit.values()) {
                double converted = original.in(target);

                Weight back = Weight.of(converted, target);

                double expected = original.in(Unit.GRAM);
                double actual = back.in(Unit.GRAM);

                double tolerance = Math.max(
                        1e-7,
                        Math.abs(expected) * 1e-12
                );

                assertEquals(expected, actual, tolerance);
            }
        }
    }

    // 4. ADDITION

    @Test
    void testAdditionWithDifferentUnits() {
        Weight a = Weight.of(1, Unit.KILO);
        Weight b = Weight.of(500, Unit.GRAM);

        Weight result = Weight.add(a, b);

        assertEquals(1500, result.in(Unit.GRAM), 1e-8);
    }

    @Test
    void testAdditionIsCommutative() {
        for (int i = 0; i < 1000; i++) {
            Weight a = Weight.of(
                    random.nextDouble() * 10000, Unit.GRAM);

            Weight b = Weight.of(
                    random.nextDouble() * 10000, Unit.GRAM);

            Weight ab = Weight.add(a, b);
            Weight ba = Weight.add(b, a);

            assertEquals(
                    ab.in(Unit.GRAM),
                    ba.in(Unit.GRAM),
                    1e-8
            );
        }
    }

    @Test
    void testAdditionIsAssociative() {
        for (int i = 0; i < 1000; i++) {
            Weight a = Weight.of(random.nextDouble() * 1000,
                    Unit.GRAM);
            Weight b = Weight.of(random.nextDouble() * 1000,
                    Unit.GRAM);
            Weight c = Weight.of(random.nextDouble() * 1000,
                    Unit.GRAM);

            Weight left = Weight.add(
                    Weight.add(a, b), c);

            Weight right = Weight.add(
                    a, Weight.add(b, c));

            assertEquals(
                    left.in(Unit.GRAM),
                    right.in(Unit.GRAM),
                    1e-8
            );
        }
    }

    // 5. JÄMFÖRELSER

    @Test
    void testComparisonDifferentUnits() {
        Weight a = Weight.of(1, Unit.KILO);
        Weight b = Weight.of(1000, Unit.GRAM);

        assertEquals(0, a.compareTo(b));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testComparisonOrder() {
        Weight small = Weight.of(500, Unit.GRAM);
        Weight medium = Weight.of(1, Unit.KILO);
        Weight large = Weight.of(2, Unit.KILO);

        assertTrue(small.compareTo(medium) < 0);
        assertTrue(medium.compareTo(large) < 0);
        assertTrue(large.compareTo(small) > 0);
    }

    @Test
    void testComparisonConsistency() {
        Weight a = Weight.of(1000, Unit.GRAM);
        Weight b = Weight.of(1, Unit.KILO);
        Weight c = Weight.of(0.001, Unit.TON);

        assertEquals(0, a.compareTo(b));
        assertEquals(0, b.compareTo(c));
        assertEquals(0, a.compareTo(c));
    }

    @Test
    void testAlmostEqualWeights() {
        Weight a = Weight.of(1.0, Unit.GRAM);
        Weight b = Weight.of(1.0000000001, Unit.GRAM);

        assertTrue(a.compareTo(b) < 0);
    }

    // 6. PRISBERÄKNING

    @Test
    void testPriceCalculation() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1000),
                Unit.KILO
        );

        Money result = item.getPriceFor(
                Weight.of(500, Unit.GRAM));

        assertEquals(500, result.getAmountInOre());
    }

    @Test
    void testPriceForZeroWeight() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1000),
                Unit.KILO
        );

        Money result = item.getPriceFor(
                Weight.of(0, Unit.GRAM));

        assertEquals(0, result.getAmountInOre());
    }

    @Test
    void testPriceRoundingHalfUp() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1),
                Unit.KILO
        );

        Money result = item.getPriceFor(
                Weight.of(500, Unit.GRAM));

        assertEquals(1, result.getAmountInOre());
    }

    @Test
    void testPriceWithNullWeight() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1000),
                Unit.KILO
        );

        assertThrows(IllegalArgumentException.class,
                () -> item.getPriceFor(null));
    }

    @Test
    void testPriceOverflow() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(Long.MAX_VALUE),
                Unit.GRAM
        );

        assertThrows(ArithmeticException.class,
                () -> item.getPriceFor(
                        Weight.of(2, Unit.GRAM)));
    }

    // 7. VÅGEN OCH WEIGHINGSERVICE

    @Test
    void testScaleFailsTwiceThenSucceeds() {
        AtomicInteger attempts = new AtomicInteger();

        Scale scale = () -> {
            if (attempts.incrementAndGet() <= 2) {
                throw new ScaleException("Temporary failure");
            }

            return Weight.of(100, Unit.GRAM);
        };

        WeighingService service = new WeighingService(scale);

        WeightPricedItem item = new WeightPricedItem(
                "Test", new Money(1000), Unit.KILO);

        assertDoesNotThrow(() -> service.priceOf(item));
        assertEquals(3, attempts.get());
    }

    @Test
    void testScaleAlwaysFails() {
        AtomicInteger attempts = new AtomicInteger();

        Scale scale = () -> {
            attempts.incrementAndGet();
            throw new ScaleException("Failure");
        };

        WeighingService service = new WeighingService(scale);

        WeightPricedItem item = new WeightPricedItem(
                "Test", new Money(1000), Unit.KILO);

        assertThrows(ScaleException.class,
                () -> service.priceOf(item));

        assertEquals(3, attempts.get());
    }

    @Test
    void testScaleFailsOnceThenSucceeds() {
        AtomicInteger attempts = new AtomicInteger();

        Scale scale = () -> {
            if (attempts.incrementAndGet() == 1) {
                throw new ScaleException("Temporary failure");
            }

            return Weight.of(200, Unit.GRAM);
        };

        WeighingService service = new WeighingService(scale);

        WeightPricedItem item = new WeightPricedItem(
                "Test", new Money(1000), Unit.KILO);

        assertDoesNotThrow(() -> service.priceOf(item));
        assertEquals(2, attempts.get());
    }

   

    @Test
    void testScaleReturnsNull() {
        Scale scale = () -> null;

        WeighingService service = new WeighingService(scale);

        WeightPricedItem item = new WeightPricedItem(
                "Test", new Money(1000), Unit.KILO);

        assertThrows(IllegalArgumentException.class,
                () -> service.priceOf(item));
    }
}
