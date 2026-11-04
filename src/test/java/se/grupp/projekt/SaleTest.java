
package se.grupp.projekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private Sale sale;
    private Product milk;
    private Product bread;

    @BeforeEach
    void setUp() {
        sale = new Sale();
        milk = new Product("Mjölk", new Money(1250), new VatRate(12), "7312340000002");   // 12,50 kr
        bread = new Product("Bröd", new Money(3000), new VatRate(12), "7312340000002");   // 30,00 kr
    }
      // Ny sale är tom
    @Test
    void nySale_ärTom() {
        assertEquals(0, sale.getProductCount());
        assertTrue(sale.getProducts().isEmpty());
    }
     @Test
    void flerProdukter_allaSparas() {
        sale.addProduct(milk);
        sale.addProduct(bread);

        assertEquals(2, sale.getProductCount());
        assertSame(milk, sale.getProducts().get(0));
        assertSame(bread, sale.getProducts().get(1));
    }
    @Test
    void sammaProduktavTva() {
        sale.addProduct(milk);
        sale.addProduct(milk);

        assertEquals(2, sale.getProductCount());
    }
      @Test
    void nullProdukt_kastarFel() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> sale.addProduct(null));

        assertEquals("Produkten kan inte vara null", e.getMessage());
        assertEquals(0, sale.getProductCount());
    
    }
       @Test
    void getTotal_tomSale_ärNoll() {
        assertEquals(new Money(0), sale.getTotal());
    }
    @Test
    void enProdukt_ProduktensPris() {
        sale.addProduct(milk);

        assertEquals(new Money(1250), sale.getTotal());
    }
    @Test
    void flerProdukter_SummanAvPriserna() {
        sale.addProduct(milk);
        sale.addProduct(bread);

        assertEquals(new Money(4250), sale.getTotal());
    }

}