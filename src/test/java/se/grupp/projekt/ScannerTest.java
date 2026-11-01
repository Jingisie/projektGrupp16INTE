package se.grupp.projekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
public class ScannerTest {
    private static final String MILK_EAN = "7312340000002";
    private static final String BREAD_EAN = "7312340000019";

    private Sale sale;
    private Scanner scanner;
    private Product milk;
    private Product bread;

    @BeforeEach
    void setUp() {
        sale = new Sale();
        milk = new Product("Mjölk", new Money(1250), new VatRate(12), MILK_EAN);
        bread = new Product("Bröd", new Money(3000), new VatRate(12), BREAD_EAN);

        Map<String, Product> register = Map.of(
            MILK_EAN, milk,
            BREAD_EAN, bread);

        scanner = new Scanner(sale, register);
    }
     public void assertOgiltig(String ean, String forvantatMeddelande) {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> scanner.scan(ean));
        }
    @Test
    void korrektEan_varanLäggsTill() {
        scanner.scan(MILK_EAN);

        assertEquals(1, sale.getProductCount());
        assertSame(milk, sale.getProducts().get(0));
    }        


    
   @Test
void felForetagsprefix() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan("7312350000009"));

    assertAll(
        () -> assertEquals("Fel företags prefix", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}


@Test
void felKontrollsiffra() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan("7312340000001"));

    assertAll(
        () -> assertEquals("Fel kontrollsiffra", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}

@Test
void felaktigtTecken() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan("731234A000002"));

    assertAll(
        () -> assertEquals("Felaktigt tecken", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}

@Test
void farreAn13Tecken() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan("731234000002"));

    assertAll(
        () -> assertEquals("EAN måste vara 13 tecken", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}


@Test
void flerAn13Tecken() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan("73123400000002"));

    assertAll(
        () -> assertEquals("EAN måste vara 13 tecken", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}

@Test
void kolla_null() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan(null));

    assertAll(
        () -> assertEquals("EAN får inte vara null eller tom", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}


@Test
void tomStrang() {
    IllegalArgumentException e = assertThrowsExactly(
        IllegalArgumentException.class,
        () -> scanner.scan(""));

    assertAll(
        () -> assertEquals("EAN får inte vara null eller tom", e.getMessage()),
        () -> assertEquals(0, sale.getProductCount())
    );
}

}

