package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleTest {


   
void getTotal_singleProduct_returnsItsPrice() {
    Sale sale = new Sale();
    sale.addItem(new Item("Mjölk", 12.50));

    assertEquals(12.50, sale.getTotal());

}

      @Test
    void getTotal_allItem() {
        Sale sale = new Sale();

        sale.addItem(new Product("Mjölk", 15.0));
        sale.addItem(new Product("Bröd", 25.0));
        sale.addItem(new Product("Äpple", 10.0));

        double result = sale.calculateTotal();

        assertEquals(50.0, result);
}
    @Test
    public void testProductWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new Item(null, 20.0));
  }
   @Test
      public void testProductWithNegativePrice() {
         assertThrows(IllegalArgumentException.class, () -> new Item("Mjölk", -10.0));
      }
    }