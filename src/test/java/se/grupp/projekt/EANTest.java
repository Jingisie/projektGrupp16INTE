
package se.grupp.projekt;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class EANTest {
    @Test
    void korrektEan() {
        assertDoesNotThrow(() -> EAN.validera("7312340000002"));
    }

    @Test
    void felForetagsprefix() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera("7312350000009"));
        assertEquals("Fel företags prefix", e.getMessage());
    }

    @Test
    void felKontrollsiffra() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera("7312340000001"));
        assertEquals("Fel kontrollsiffra", e.getMessage());
    }

  
    @Test
    void felaktigtTecken() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera("731234A000002"));
        assertEquals("Felaktigt tecken", e.getMessage());
    }

   
    @Test
    void farreAn13Tecken() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera("731234000002"));
        assertEquals("EAN måste vara 13 tecken", e.getMessage());
    }

    @Test
    void flerAn13Tecken() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera("73123400000002"));
        assertEquals("EAN måste vara 13 tecken", e.getMessage());
    }

    
    @Test
    void kolla_null() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera(null));
        assertEquals("EAN får inte vara null eller tom", e.getMessage());
    }

   
    @Test
    void tomStrang() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> EAN.validera(""));
        assertEquals("EAN får inte vara null eller tom", e.getMessage());
    }
}



