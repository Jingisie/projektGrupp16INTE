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

