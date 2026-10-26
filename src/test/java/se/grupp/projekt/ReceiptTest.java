package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptTest {

    @Test
    void subtotalIsTheSumOfProductPrices() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(15_500), receipt.getSubtotal());
    }

    // Kollar att kvittot summerar momsbeloppet från alla varor
    @Test
    void vatTotalIsTheSumOfProductVatAmounts() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(2_821), receipt.getVatTotal());
    }

    // Kollar att kvittots total blir rätt när ingen rabatt används.
    @Test
    void totalWithoutDiscountEqualsSubtotal() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(15_500), receipt.getTotal());
    }

    // Testar kvittots total med rabatt.
    @Test
    void totalWithTenPercentDiscountIsReduced() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(13_950), receipt.getTotal(10));
    }

    // Testar rabattbeloppet på kvittot.
    @Test
    void discountAmountIsCorrect() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(1_550), receipt.getDiscountAmount(10));
    }

    // Testar momsbeloppet när kvittot har rabatt.
    @Test
    void vatTotalAfterTenPercentDiscountIsCorrect() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(new Money(2_539), receipt.getVatTotal(10));
    }

    // Testar att kvittot lämnar varorna för utskrift.
    @Test
    void receiptProvidesProductsForPrinting() {
        List<Product> products = List.of(
                new Product("Coffee", new Money(12_500), new VatRate(25)),
                new Product("Bread", new Money(3_000), new VatRate(12))
        );

        Receipt receipt = new Receipt(products);

        assertEquals(products, receipt.getProducts());
    }
}