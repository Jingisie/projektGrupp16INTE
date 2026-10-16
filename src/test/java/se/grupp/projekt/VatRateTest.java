package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VatRateTest {

@Test
void standardMomsPa125Kr() {
        VatRate vatRate = new VatRate(25);
        Money money = new Money(12500);
        Money expectedVat = new Money(2500);
        assertEquals(expectedVat, vatRate.calculateVat(money));
    }

    @Test 
    void negativMomsGerFel(){
        assertThrows(IllegalArgumentException.class, () -> new VatRate(-5));
    }


}