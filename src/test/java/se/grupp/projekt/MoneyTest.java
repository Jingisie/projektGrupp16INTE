package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    @Test 
    void sparasBeloppetIOre() {
        Money money = new Money(1250);
        assertEquals(1250, money.getAmountInOre());
    }

    @Test
    void adderaTvaBelopp() {
        assertEquals(new Money(2500), new Money(1000).add(new Money(1500)));
    }

    @Test 
    void arLikaBeloppLika() {
        Money money1 = new Money(1000);
        Money money2 = new Money(1000);
        assertEquals(money1, money2);
    }

    @Test 
        void visasSomKr()  {
            assertEquals("10.00 kr", new Money(1000).toString());
        }
    
}
