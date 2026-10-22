package se.grupp.projekt;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ItemTest {


    @Test
    void itemKanSkapas() {
        assertNotNull(new Item());

}
}
