package se.grupp.projekt;
<<<<<<< HEAD
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptTest {
 private Receipt receipt;
    private Sale saleWithTotal(int ore) {
        Sale sale = new Sale();
        sale.addProduct(new Product("Vara", ore));
        
        return sale;
    }

 
    @Test
    void kvittotInnehallerTotalsumman() {
        Receipt receipt = new Receipt(saleWithTotal(1250));
 
        assertTrue(receipt.format().contains("12,50 kr"));
    }
       @Test
    void kvittotBorjarMedTextenTotalt() {
        Receipt receipt = new Receipt(saleWithTotal(1250));
 
        assertTrue(receipt.format().startsWith("Totalt"));
    }
       @Test
    void fleraProdukterSummerasTillTotal() {
        Sale sale = new Sale();
   sale.addProduct(new Product("Vara", new Money(ore), new VatRate(25)));
   sale.addProduct(new Product("Mjölk", new Money(1500), new VatRate(25)));
        String text = new Receipt(sale).format();

        assertTrue(text.contains("Mjölk"));
        assertTrue(text.contains("Bröd"));
        assertTrue(text.contains("40,00 kr"));
    }
     @Test
    void nullForsaljningKastarException() {
        assertThrows(IllegalArgumentException.class, () -> new Receipt(null));
    }
 
}
 


=======

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
>>>>>>> d8ef0c661f889520515066d98436f5517f2bcce3
