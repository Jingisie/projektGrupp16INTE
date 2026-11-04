package se.grupp.projekt;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

class ReceiptTest {

    @Test
    void subtotalIsTheSumOfProductPrices() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(15_500), receipt.getSubtotal());
    }

    // Kollar att kvittot summerar momsbeloppet från alla varor
    @Test
    void vatTotalIsTheSumOfProductVatAmounts() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(2_821), receipt.getVatTotal());
    }

    // Kollar att kvittots total blir rätt när ingen rabatt används.
    @Test
    void totalWithoutDiscountEqualsSubtotal() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(15_500), receipt.getTotal());
    }

    // Testar kvittots total med rabatt.
    @Test
    void totalWithTenPercentDiscountIsReduced() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(13_950), receipt.getTotal(10));
    }

    // Testar rabattbeloppet på kvittot.
    @Test
    void discountAmountIsCorrect() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"   ),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(1_550), receipt.getDiscountAmount(10));
    }

    // Kollar att kvittot skickar rabatten vidare till varorna.
    @Test
    void vatTotalAfterTenPercentDiscountUsesDiscountForEachProduct() {
        Product coffee = spy(new Product(
                "Coffee", new Money(12_500), new VatRate(25), "7312340000002"));
        Product bread = spy(new Product(
                "Bread", new Money(3_000), new VatRate(12), "7312340000019"));

        Receipt receipt = new Receipt(List.of(coffee, bread));

        assertEquals(new Money(2_539), receipt.getVatTotal(10));
        verify(coffee).getVatAmount(10);
        verify(bread).getVatAmount(10);
    }

    // Testar att kvittot lämnar varorna för utskrift.
    @Test
    void receiptProvidesProductsForPrinting() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000002")
        );

        Receipt receipt = new Receipt(products);

        assertEquals(products, receipt.getProducts());
    }

    // Kollar att kvittot använder den inskickade rabattberäknaren.
    @Test
    void totalUsesInjectedDiscountCalculator() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25), "7312340000002"),
                new Product("Bread", new Money(3_000), new VatRate(12), "7312340000019")
        );

        DiscountCalculator calculator = mock(DiscountCalculator.class);
        when(calculator.calculate(new Money(15_500), 10))
                .thenReturn(new Money(12_345));

        Receipt receipt = new Receipt(products, calculator);

        assertEquals(new Money(12_345), receipt.getTotal(10));
        verify(calculator).calculate(new Money(15_500), 10);
    }
}
