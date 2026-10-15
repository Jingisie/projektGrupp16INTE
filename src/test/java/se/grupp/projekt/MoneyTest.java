package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    @Test 
    void sparasBeloppetIOre() {
        Money money = new Money(1250);
        assertEquals(1250, money.getAmountInOre());
    }
    
}
