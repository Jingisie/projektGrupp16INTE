
package se.grupp.projekt;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class AIBugHunterTest {

    private final Random random = new Random();

    @Test
    void testExtremeWeights() {
        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NaN, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.POSITIVE_INFINITY, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NEGATIVE_INFINITY, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(-1, Unit.GRAM));
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

        assertEquals(0.000003, result.in(Unit.GRAM), 1e-12);
    }

    @Test
    void testAllUnitConversions() {
        for (Unit from : Unit.values()) {
            for (Unit to : Unit.values()) {
                double value = 123.456;

                Weight original = Weight.of(value, from);
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
    void testRandomAddition() {
        for (int i = 0; i < 1000; i++) {
            double a = random.nextDouble() * 10000;
            double b = random.nextDouble() * 10000;

            Weight w1 = Weight.of(a, Unit.GRAM);
            Weight w2 = Weight.of(b, Unit.GRAM);

            Weight result = Weight.add(w1, w2);

            assertEquals(a + b, result.in(Unit.GRAM), 1e-8);
        }
    }

    @Test
    void testComparisonConsistency() {
        Weight a = Weight.of(1, Unit.KILO);
        Weight b = Weight.of(1000, Unit.GRAM);

        assertEquals(0, a.compareTo(b));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testPriceRounding() {
        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1000),
                Unit.KILO
        );

        Money result = item.getPriceFor(
                Weight.of(500, Unit.GRAM)
        );

        assertEquals(500, result.getAmountInOre());
    }

    @Test
    void testScaleFailsTwiceThenSucceeds() throws Exception {
        AtomicInteger attempts = new AtomicInteger();

        Scale scale = () -> {
            if (attempts.incrementAndGet() <= 2) {
                throw new ScaleException("Temporary failure");
            }

            return Weight.of(100, Unit.GRAM);
        };

        WeighingService service = new WeighingService(scale);

        WeightPricedItem item = new WeightPricedItem(
                "Test",
                new Money(1000),
                Unit.KILO
        );

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
                "Test",
                new Money(1000),
                Unit.KILO
        );

        assertThrows(ScaleException.class,
                () -> service.priceOf(item));

        assertEquals(3, attempts.get());
    }
}
