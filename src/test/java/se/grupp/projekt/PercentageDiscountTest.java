package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PercentageDiscountTest {

    // Testar rabatt på ett vanligt pris.
    @Test
    void tenPercentDiscountReducesPriceByTenPercent() {
        Money originalPrice = new Money(10_000);

        Money discountedPrice = PercentageDiscount.apply(originalPrice, 10);

        assertEquals(new Money(9_000), discountedPrice);
    }

    // Testar att noll procents rabatt lämnar priset oförändrat.
    @Test
    void zeroPercentDiscountKeepsOriginalPrice() {
        Money originalPrice = new Money(10_000);

        Money discountedPrice = PercentageDiscount.apply(originalPrice, 0);

        assertEquals(new Money(10_000), discountedPrice);
    }

    // Testar att rabatt över 100 procent avvisas.
    @Test
    void rejectsDiscountAboveOneHundredPercent() {
        Money originalPrice = new Money(10_000);

        assertThrows(IllegalArgumentException.class,
                () -> PercentageDiscount.apply(originalPrice, 101));
    }

    // Testar att negativ rabatt avvisas.
    @Test
    void rejectsNegativeDiscount() {
        Money originalPrice = new Money(10_000);

        assertThrows(IllegalArgumentException.class,
                () -> PercentageDiscount.apply(originalPrice, -1));
    }

    // Testar att 100 procents rabatt ger noll kronor.
    @Test
    void oneHundredPercentDiscountReducesPriceToZero() {
        Money originalPrice = new Money(10_000);

        Money discountedPrice = PercentageDiscount.apply(originalPrice, 100);

        assertEquals(new Money(0), discountedPrice);
    }

    // Testar att rabatten kan omsluta ett pris.
    @Test
    void percentageDiscountWrapsAnotherPrice() {
        Price originalPrice = () -> new Money(10_000);
        Price discountedPrice = new PercentageDiscount(originalPrice, 10);

        assertEquals(new Money(9_000), discountedPrice.amount());
    }

    // Testar att rabatten använder priset som skickats in.
    @Test
    void percentageDiscountUsesInjectedPrice() {
        Price originalPrice = mock(Price.class);
        when(originalPrice.amount()).thenReturn(new Money(10_000));

        PercentageDiscount discount = new PercentageDiscount(originalPrice, 10);

        assertEquals(new Money(9_000), discount.amount());
        verify(originalPrice).amount();
    }
}
