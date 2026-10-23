package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



public class WeighingServiceTest {
	
	
	@Test
	void priceOfWighedUtem() throws Exception {
		Scale scale = mock(Scale.class );
		when(scale.weigh()).thenReturn(Weight.of(500, Unit.GRAM));
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		WeighingService service = new WeighingService(scale);
		
		assertEquals(new Money(1000), service.priceOf(item));
	}

	@Test
	void triesThreeTimes() throws Exception {
		Scale scale = mock(Scale.class );
		when(scale.weigh())
			.thenThrow(new ScaleException("Något fel vid vägning"))
			.thenThrow(new ScaleException("Något fel vid vägning"))
			.thenReturn(Weight.of(500, Unit.GRAM ));
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		WeighingService service = new WeighingService(scale);
		
		assertEquals(new Money(1000), service.priceOf(item));
		
		verify(scale, times(3)).weigh();
	}
	
	@Test
	void succedesTimes() throws Exception {
		Scale scale = mock(Scale.class );
		when(scale.weigh())
			.thenThrow(new ScaleException("Något fel vid vägning"))
			.thenReturn(Weight.of(500, Unit.GRAM ));
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		WeighingService service = new WeighingService(scale);
		
		assertEquals(new Money(1000), service.priceOf(item));
		
		verify(scale, times(2)).weigh();
	}
	
	
	
	@Test
	void givesUpAfterThreeFailedAttemts() throws Exception {
		Scale scale = mock(Scale.class);
		when(scale.weigh()).thenThrow(new ScaleException("Något fel vid vägning"));
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		WeighingService service = new WeighingService(scale);
		
		assertThrows(ScaleException.class, () -> {
			service.priceOf(item);
		});
		
		verify(scale, times(3)).weigh();
	}
	
	@Test
	void succecfullWeighingOnlyOneTry() throws Exception {
		Scale scale = mock(Scale.class );
		when(scale.weigh()).thenReturn(Weight.of(1, Unit.GRAM ));
		WeightPricedItem item = new WeightPricedItem("Bananer", new Money(2000), Unit.KILO );
		WeighingService service = new WeighingService(scale);
		
		service.priceOf(item);
		
		verify(scale,times(1)).weigh();
	}
	
	@Test
	void nullScaleGivesIllegalArgumentException() {
		
		assertThrows(IllegalArgumentException.class, ()-> {
			new WeighingService(null);
		});
		
	}
	
	@Test
	void nullItemGivesIllegalArgumentException() throws Exception {
		Scale scale = mock(Scale.class);
		when(scale.weigh()).thenReturn(Weight.of(1, Unit.GRAM));
		WeighingService service = new WeighingService(scale);
		
		assertThrows(IllegalArgumentException.class, ()-> {
			service.priceOf(null);
		});
	}
	
	
	
	
	
	
}
