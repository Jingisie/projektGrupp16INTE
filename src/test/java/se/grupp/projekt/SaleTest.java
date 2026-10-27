
package se.grupp.projekt;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
 

class SaleTest {

    private Product product(String name, long ore) {
        return new Product(name, new Money(ore), new VatRate(25));
    }

    @Test
    void totalenForEnVaraArDessPris() {
        Sale sale = new Sale();
        sale.addProduct(product("Mjölk", 1250));

        assertEquals(new Money(1250), sale.getTotal());
    }

    @Test
    void summanArSammanlagdaPriser() {
        Sale sale = new Sale();

        sale.addProduct(product("Mjölk", 1500));
        sale.addProduct(product("Bröd", 2500));
        sale.addProduct(product("Äpple", 1000));

        assertEquals(new Money(5000), sale.getTotal());
    }

    @Test
    void tomForsaljningGerNollISumma() {
        Sale sale = new Sale();

        assertEquals(new Money(0), sale.getTotal());
    }

    @Test
    void varaUtanNamnGerFel() {
        assertThrows(IllegalArgumentException.class, () -> product(null, 2000));
    }

    @Test
    void varaMedNegativtPrisGerFel() {
        assertThrows(IllegalArgumentException.class, () -> product("Mjölk", -1000));
    }
}