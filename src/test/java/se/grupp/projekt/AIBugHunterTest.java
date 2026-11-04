
package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class AIBugHunterTest {

    @Test
    void testInvalidWeights() {

        System.out.println("Testing invalid weights...");

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(-10, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NaN, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.POSITIVE_INFINITY, Unit.GRAM));

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(Double.NEGATIVE_INFINITY, Unit.GRAM));
    }

    @Test
    void testUnitConversions() {

        System.out.println("Testing unit conversions...");

        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {

            double value = random.nextDouble() * 10000;

            Weight weight = Weight.of(value, Unit.GRAM);

            double converted = weight.in(Unit.KILO);

            double convertedBack =
                    Weight.of(converted, Unit.KILO)
                            .in(Unit.GRAM);

            assertEquals(value, convertedBack, 0.000001);
        }
    }

    @Test
    void testWeightAddition() {

        System.out.println("Testing weight addition...");

        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {

            double a = random.nextDouble() * 10000;
            double b = random.nextDouble() * 10000;

            Weight w1 = Weight.of(a, Unit.GRAM);
            Weight w2 = Weight.of(b, Unit.GRAM);

            Weight result = Weight.add(w1, w2);

            assertEquals(a + b, result.in(Unit.GRAM), 0.000001);
        }
    }

    @Test
    void testWeightComparison() {

        System.out.println("Testing weight comparison...");

        Weight kilo = Weight.of(1, Unit.KILO);
        Weight grams = Weight.of(1000, Unit.GRAM);

        assertEquals(kilo, grams);
        assertEquals(0, kilo.compareTo(grams));

        assertTrue(
                Weight.of(2, Unit.KILO)
                        .compareTo(Weight.of(1, Unit.KILO)) > 0
        );
    }

    @Test
    void testWeightPricedItem() {

        System.out.println("Testing WeightPricedItem...");

        WeightPricedItem item = new WeightPricedItem(
                "Apple",
                new Money(1000),
                Unit.KILO
        );

        Money result = item.getPriceFor(
                Weight.of(500, Unit.GRAM)
        );

        assertEquals(500, result.getAmountInOre());

        assertThrows(IllegalArgumentException.class,
                () -> Weight.of(-1, Unit.GRAM));
    }

    @Test
    void testWeighingService() throws ScaleException {

        System.out.println("Testing WeighingService...");

        final int[] attempts = {0};

        Scale failingScale = () -> {
            attempts[0]++;
            throw new ScaleException("Sensor failure");
        };

        WeighingService service =
                new WeighingService(failingScale);

        WeightPricedItem item = new WeightPricedItem(
                "Apple",
                new Money(1000),
                Unit.KILO
        );

        assertThrows(
                ScaleException.class,
                () -> service.priceOf(item)
        );

        assertEquals(3, attempts[0]);
    }
}
