package se.grupp.projekt;
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
 


