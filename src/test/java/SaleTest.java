import org.junit.jupiter.api.Test;

import se.grupp.projekt.Product;
import se.grupp.projekt.Sale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleTest {

    @Test
    void getTotal_emptySale_returnsZero() {
        Sale sale = new Sale();

        assertEquals(0.0, sale.getTotal(), 0.0001);
    }
    @Test
void getTotal_singleProduct_returnsItsPrice() {
    Sale sale = new Sale();
    sale.addProduct(new Product("Mjölk", 12.50));

    assertEquals(12.50, sale.getTotal(), 0.0001);
}
}

