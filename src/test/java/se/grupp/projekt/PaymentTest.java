package se.grupp.projekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Mock PaymentTerminal terminal;

    private Money amount;
    private Payment payment;

    @BeforeEach 
    void setUp() {
        amount = new Money(12000);
        payment = new Payment(terminal, amount);
    }

    @Test 
    void approvedPaymentGivesPaidState() {
        when(terminal.charge(amount))
            .thenReturn(new PaymentResult(PaymentStatus.APPROVED));

        payment.pay();

        assertEquals(PaymentState.PAID, payment.getState());
    }

    @Test 
    void declinedPaymentGivesDeclinedState(){
        Money amount = new Money(12000);
        when(terminal.charge(amount))
            .thenReturn(new PaymentResult(PaymentStatus.DECLINED));

        Payment payment = new Payment(terminal, amount);
        payment.pay();

        assertEquals(PaymentState.DECLINED, payment.getState());
    }
    
}
