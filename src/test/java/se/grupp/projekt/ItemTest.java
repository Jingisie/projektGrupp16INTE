package se.grupp.projekt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ItemTest {


    @Test
    void tomtSomNamnGerFel() {
        assertThrows(IllegalArgumentException.class, () -> new Item("", 1000));
    }
    @Test
    void negativtPrisGerFel(){
    assertThrows(IllegalArgumentException.class, () -> new Item("Mjölk", -1));
    }

}
