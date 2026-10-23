
package se.grupp.projekt;
 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
 
class SaleTest {

    @Test
    void totalenForEnVaraArDessPris() {
        Sale sale = new Sale();
        sale.addItem(new ItemTest("Mjölk", 1250));
 
        assertEquals(1250, sale.getTotal());
    }
 
    @Test
    void summanArSammanlagdaPriser() {
        Sale sale = new Sale();
 
        sale.addItem(new Item("Mjölk", 1500));
        sale.addItem(new Item("Bröd", 2500));
        sale.addItem(new Item("Äpple", 1000));
 
        assertEquals(5000, sale.getTotal());
    }
 
    @Test
    void tomForsaljningGerNollISumma() {
        Sale sale = new Sale();
 
        assertEquals(0, sale.getTotal());
    }
 
    @Test
    void varaUtanNamnGerFel() {
        assertThrows(IllegalArgumentException.class, () -> new ItemTest(null, 2000));
    }
 
    @Test
    void varaMedNegativtPrisGerFel() {
        assertThrows(IllegalArgumentException.class, () -> new ItemTest("Mjölk", -1000));
    }
}