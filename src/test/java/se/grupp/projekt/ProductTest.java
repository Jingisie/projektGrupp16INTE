package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test 
    void raknarUtEgenMoms() {
        Product product = new Product("Testprodukt", new Money(12500), new VatRate(25));
        assertEquals(new Money(2500), product.getVatAmount());
    }

    
}
