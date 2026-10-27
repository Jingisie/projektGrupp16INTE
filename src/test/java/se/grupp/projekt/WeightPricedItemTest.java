package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class WeightPricedItemTest {
	
	
	@Test
	void hasName() {
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		
		assertEquals("Bananer", item.getName());
	}
	
	
	@Test
	void priceSameUnit() {
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		
		assertEquals(new Money(2000), item.getPriceFor(Weight.of(1, Unit.KILO )));
	}
	
	@Test
	void priceDifferentUnit() {
		WeightPricedItem item = new WeightPricedItem("Appelsiner", new Money(500), Unit.HEKTO );
		
		assertEquals(new Money(5000), item.getPriceFor(Weight.of(1000, Unit.GRAM )));	
	}
	
	@Test
	void zeroWeightIsFree() {
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(100), Unit.KILO );
		assertEquals(new Money(0), item.getPriceFor(Weight.of(0, Unit.GRAM )));
	}
	
	@Test
	void nullName() {
		assertThrows(IllegalArgumentException.class, () -> {
			WeightPricedItem item = new WeightPricedItem(null, new Money(100), Unit.KILO );
		});
	}
	
	@Test
	void negativeMoneythrowsIllegalArgument() {
		assertThrows(IllegalArgumentException.class, () -> {
			WeightPricedItem item = new WeightPricedItem("Bananer", new Money(-2000), Unit.KILO );
		});
		
	}
	
	@Test
	void nullPriceThrowsIlegalArgumentException() {
		assertThrows(IllegalArgumentException.class, ()-> {
			WeightPricedItem item = new WeightPricedItem("Bananer", null, Unit.KILO );
		});
	}
	
	@Test
	void nullUnitThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, ()-> {
			WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), null);
		});
	}
	
	@Test
	void nullWeightInGetPriceForThrowsIllegalArgumentException() {
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO);
		
		assertThrows(IllegalArgumentException.class, () -> {
			item.getPriceFor(null);
		});
	}
	
	@Test
	void nameIsBlankThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			WeightPricedItem item = new WeightPricedItem("", new Money(2000), Unit.KILO);
		});
		
	}
}
