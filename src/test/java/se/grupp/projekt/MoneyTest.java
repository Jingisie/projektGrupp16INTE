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
        Money money1 = new Money(1000);
        Money money2 = new Money(500);
        Money sum = new Money(money1.getAmountInOre() + money2.getAmountInOre());
        assertEquals(1500, sum.getAmountInOre());
    }

    @Test 
    void arLikaBeloppLika() {
        Money money1 = new Money(1000);
        Money money2 = new Money(1000);
        assertEquals(money1, money2);
    }
    
}
