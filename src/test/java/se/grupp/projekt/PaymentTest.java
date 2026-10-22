package se.grupp.projekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PaymentTest {

    //gör om till konstanter för att undvika att skapa nya objekt varje gång
    private static final PaymentResult APPROVED = new PaymentResult(PaymentStatus.APPROVED);
    private static final PaymentResult DECLINED = new PaymentResult(PaymentStatus.DECLINED);

    //En mockad PaymentTerminal som vi kan använda för att simulera olika scenarion
    @Mock PaymentTerminal terminal;

    private Money amount;
    private Payment payment;

    //Körs innan varje testfall för att skapa en ny Payment-instans med en mockad betalning (payment) och ett belopp
    @BeforeEach 
    void setUp() {
        amount = new Money(12000);
        payment = new Payment(terminal, amount);
    }

    //En godkänd betalning ska leda till att betalningen får statusen "PAID"
    @Test 
    void approvedPaymentGivesPaidState() {
        //terminalen ska returnera en godkänd PaymentResult när charge() anropas med amount
        when(terminal.charge(amount))
            .thenReturn(APPROVED);

        //genomför betalningen
        payment.pay();

        //kontrollera att betalningens status är "PAID"
        assertEquals(PaymentState.PAID, payment.getState());
    }

    //nekad betalning ska leda till att betalningen får statusen "DECLINED"
    @Test 
    void declinedPaymentGivesDeclinedState(){
        //arrangera mocken att returnera en declined PaymentResult när charge() anropas med amount
        when(terminal.charge(amount))
            .thenReturn(DECLINED);

        //försök att genomföra betalningen
        payment.pay();

        //betalningen ska nu vara markerad som "DECLINED"
        assertEquals(PaymentState.DECLINED, payment.getState());
    }

    @Test 
    void customerMayRetryPaymentAfterDeclinedPayment() {
        //mocken spelar två olika scenarion, först nekad betalning och sedan godkänd betalning
        when(terminal.charge(amount))
            //nekat
            .thenReturn(DECLINED)
            //godkänt
            .thenReturn(APPROVED);

        payment.pay();
        payment.pay();

        assertEquals(PaymentState.PAID, payment.getState());
        //kolla om terminalen anropas 2 gånger
        verify(terminal, times(2)).charge(amount);
    }

    @Test 
    void cantPayAfterApprovedPayment() {
        //mocken returnerar en godkänd betalning
        when(terminal.charge(amount))
            .thenReturn(APPROVED);
        payment.pay();
        
        //andra anropet till pay() ska kasta ett IllegalStateException eftersom betalningen redan är godkänd
        assertThrows(IllegalStateException.class, payment::pay); 

        //kontrollera att terminalen bara anropas en gång
        verify(terminal, times(1)).charge(amount);

    }
    
}
