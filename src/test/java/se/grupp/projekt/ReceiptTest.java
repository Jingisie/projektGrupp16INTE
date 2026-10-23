package se.grupp.projekt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptTest {
 private Receipt receipt;
    private Sale saleWithTotal(int ore) {
        Sale sale = new Sale();
        sale.addItem(new Item("Vara", ore));
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
 
}
 


