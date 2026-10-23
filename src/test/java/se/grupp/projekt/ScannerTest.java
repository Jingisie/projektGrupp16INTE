package se.grupp.projekt;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ScannerTest {
   private Scanner scanner;

 @Test
    void barcode_returnerarRättProduktNärStreckkodFinns() {
        assertSame("mjölk", scanner.barcode("1111"));
        assertSame("bröd", scanner.barcode("2222"));
    }
    @Test
void barcodeNull() {
    assertThrows(IllegalArgumentException.class, () -> scanner.barcode(null));
}
}


