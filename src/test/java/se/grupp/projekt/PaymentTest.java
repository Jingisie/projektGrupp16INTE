package se.grupp.projekt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Mock PaymentTerminal terminal;

    @Test 
    void approvedPaymentGivesPaidState() {
        Money amount = new Money(12000);
        when(terminal.charge(amount))
            .thenReturn(new PaymentResult(PaymentStatus.APPROVED));

        Payment payment = new Payment(terminal, amount);
        payment.pay();

        assertEquals(PaymentState.PAID, payment.getState());
    }
    
}
