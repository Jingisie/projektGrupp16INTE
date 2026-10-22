import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleTest {

    @Test
    void getTotal_emptySale_returnsZero() {
        Sale sale = new Sale();

        assertEquals(0.0, sale.getTotal(), 0.0001);
    }
}

