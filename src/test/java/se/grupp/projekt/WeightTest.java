



package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class WeightTest {
	
	
	@ParameterizedTest
	@CsvSource({"GRAM,1", "HEKTO, 100", "KILO, 1000", "TON, 1000000"})
	void unitTest(Unit unit, long expected) {
		assertEquals(expected, unit.gramsPerUnit());
	}
	
	@ParameterizedTest
	@CsvSource({"GRAM", "HEKTO", "KILO", "TON"})
	void inFunctionFromDifferentUnits(Unit unit) {
		Weight weight = Weight.of(10, unit); 
		assertEquals(weight.in(Unit.GRAM), 10*unit.gramsPerUnit());
	}
	
	@ParameterizedTest
	@CsvSource({"GRAM", "HEKTO", "KILO", "TON"})
	void inFunctionInDifferentUnits(Unit unit) {
		Weight weight = Weight.of(1000000, Unit.GRAM);
		assertEquals(1000000/unit.gramsPerUnit(), weight.in(unit)); 
	}
	
	@Test
	void constructNegativeWeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Weight.of(-1, Unit.GRAM);
		});
	}
	
	@Test
	void addWeights()	{
		Weight w1 = Weight.of(100, Unit.GRAM);
		Weight w2 = Weight.of(2, Unit.HEKTO);
		
		Weight w3 = Weight.add(w1, w2);
		
		assertEquals(300, w3.in(Unit.GRAM));
	}
	
	@Test
	void compareWeightsSameUnits() {
		Weight w1 = Weight.of(1, Unit.GRAM );
		Weight w2 = Weight.of(2, Unit.GRAM);
		Weight w3 = Weight.of(1, Unit.GRAM);
		
		assertTrue(w1.compareTo(w2) < 0);
		assertTrue(w2.compareTo(w1) > 0);
		assertTrue(w1.compareTo(w3) == 0);
		
	}
	
	@Test 
	void compareWeightsDifferentUnits() {
		Weight w1 = Weight.of(1, Unit.HEKTO );
		Weight w2 = Weight.of(100, Unit.GRAM);
		Weight w3 = Weight.of(2, Unit.KILO);
		Weight w4 = Weight.of(3000, Unit.GRAM);
		Weight w5 = Weight.of(2000, Unit.KILO);
		Weight w6 = Weight.of(1, Unit.TON );
		
		assert(w1.compareTo(w2) == 0);
		assert(w3.compareTo(w4) < 0);
		assert(w5.compareTo(w6) > 0);
		
	}
	
	@Test
	void constructWithNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			Weight.of(1, null);
		});
	}
	
	@Test
	void zeroWeight() {
		Weight w1 = Weight.of(0, Unit.HEKTO );
		assertEquals(0, w1.in(Unit.KILO ));
	}
	
	
	@Test 
	void equals() {
		Weight w1 = Weight.of(1000, Unit.GRAM );
		Weight w2 = Weight.of(1, Unit.KILO );
		
		assertTrue(w1.equals(w2));
	}
	
	@Test
	void decimal() {
		Weight w1 = Weight.of(0.5, Unit.KILO );
		
		assertEquals(500, w1.in(Unit.GRAM ));
	}
	
	@Test
	void equalWeightsHaveSameHashCode() {
		Weight w1 = Weight.of(1, Unit.KILO );
		Weight w2 = Weight.of(1000, Unit.GRAM );
		
		assertEquals(w1.hashCode(), w2.hashCode());
	}
	
	@Test
	void InFunctionNullIllegalArgumentException() {
		Weight weight = Weight.of(1, Unit.KILO);
		
		assertThrows(IllegalArgumentException.class, ()-> {
			weight.in(null);
		});
	}
	
	@Test 
	void notEquals() {
		Weight w1 = Weight.of(1, Unit.KILO );
		Weight w2 = Weight.of(2, Unit.KILO );
		
		assertFalse(w1.equals(w2));
	}
	
	@Test
	void nullInEqualsReturnsFalse() {
		Weight weight = Weight.of(1,  Unit.KILO);
		
		assertFalse(weight.equals(null));
	}
	
	@Test
	void objectEqualsItself() {
		Weight w = Weight.of(1,  Unit.KILO );
		
		assertTrue(w.equals(w));
	}
	
}


