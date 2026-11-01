package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test 
    void raknarUtEgenMoms() {
        Product product = new Product("Testprodukt", new Money(12500), new VatRate(25), "7312340000002");
        assertEquals(new Money(2500), product.getVatAmount());
    }

    @Test 
    void tomtNamnGerFel() {
        assertThrows(IllegalArgumentException.class, () -> new Product("", new Money(1000), new VatRate(25), "7312340000002"));
    }

    @Test 
    void varaVisasMedNamnOchPris() {
        Product product = new Product("Testprodukt", new Money(12500), new VatRate(25), "7312340000002");
        assertEquals("Testprodukt: 125, 00 kr", product.toString());
    }

    @Test 
    void korrektEan() {
        Product product = new Product("Banan", new Money(1000), new VatRate(25), "7312340000002");
        assertEquals("7312340000002", product.getEan());
    }

    @Test
    void felForetagsPrefix() {          // K2
        assertEanRejected("7312350000009", "Fel företagsprefix");
}

    private void assertEanRejected(String ean, String expectedMessage) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Product("Banan", new Money(1000), new VatRate(25), ean));
    assertEquals(expectedMessage, exception.getMessage());
}

    @Test
    void felKontrollsiffra() {          // K3
        assertEanRejected("7312340000001", "Fel kontrollsiffra");
    }

    @Test
    void felaktigtTecken() {            // K4
        assertEanRejected("731234A000002", "Felaktigt tecken");
    }

    @Test
    void farreAn13Tecken() {            // K5
        assertEanRejected("731234000002", "För få tecken");
    }

    @Test
    void flerAn13Tecken() {             // K6
        assertEanRejected("73123400000002", "För många tecken");
    }

    @Test
    void nullEan() {                    // K7
        assertEanRejected(null, "EAN-koden får inte vara tom");
    }

    @Test
    void tomEan() {                     // K8
        assertEanRejected("", "EAN-koden får inte vara tom");
    }
 }
