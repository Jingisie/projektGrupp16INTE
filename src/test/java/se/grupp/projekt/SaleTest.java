package se.grupp.projekt;

import org.junit.jupiter.api.Test;

import se.grupp.projekt.Product;
import se.grupp.projekt.Sale;

import static org.junit.jupiter.api.Assertions.assertEquals;
s
class SaleTest {


   
void getTotal_singleProduct_returnsItsPrice() {
    Sale sale = new Sale();
    sale.addProduct(new Product("Mjölk", 12.50));

    assertEquals(12.50, sale.getTotal(), 0.0001);

}

      @Test
    void getTotal_allProduct() {
        Sale sale = new Sale();

        sale.addProduct(new Product("Mjölk", 15.0));
        sale.addProduct(new Product("Bröd", 25.0));
        sale.addProduct(new Product("Äpple", 10.0));

        double result = sale.calculateTotal();

        assertEquals(50.0, result, 0.001);
}

