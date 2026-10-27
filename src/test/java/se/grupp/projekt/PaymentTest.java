package se.grupp.projekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
class PaymentTest {

    // gör om till konstanter för att undvika att skapa nya objekt varje gång
    private static final PaymentResult APPROVED = new PaymentResult(PaymentStatus.APPROVED);
    private static final PaymentResult DECLINED = new PaymentResult(PaymentStatus.DECLINED);
    private static final PaymentResult ERROR = new PaymentResult(PaymentStatus.ERROR);

    // En mockad PaymentTerminal som vi kan använda för att simulera olika scenarion
    @Mock PaymentTerminal terminal;

    private Money amount;
    private Payment payment;

    // Körs innan varje testfall för att skapa en ny Payment-instans med en mockad betalning (payment) och ett belopp
    @BeforeEach 
    void setUp() {
        amount = new Money(12000);
        payment = new Payment(terminal, amount);
    }

    // En godkänd betalning ska leda till att betalningen får statusen "PAID"
    @Test 
    void approvedPaymentGivesPaidState() {
        // terminalen ska returnera en godkänd PaymentResult när charge() anropas med amount
        when(terminal.charge(amount))
            .thenReturn(APPROVED);

        // genomför betalningen
        payment.pay();

        // kontrollera att betalningens status är "PAID"
        assertEquals(PaymentState.PAID, payment.getState());
    }

    // nekad betalning ska leda till att betalningen får statusen "DECLINED"
    @Test 
    void declinedPaymentGivesDeclinedState(){
        // arrangera mocken att returnera en declined PaymentResult när charge() anropas med amount
        when(terminal.charge(amount))
            .thenReturn(DECLINED);

        // försök att genomföra betalningen
        payment.pay();

        // betalningen ska nu vara markerad som "DECLINED"
        assertEquals(PaymentState.DECLINED, payment.getState());
    }

    // Ersätter @Test med @ParameterizedTest och @EnumSource för att testa flera scenarion med olika PaymentStatus
    // Alltså försök igen efter DECLINED , försök igen efter ERROR
    @ParameterizedTest(name = "försök igen efter {0}")
    // Var värdena kommer ifrån
    @EnumSource(value = PaymentStatus.class, names = {"DECLINED", "ERROR"})
    void customerMayRetryPaymentAfterDeclinedPayment(PaymentStatus failedStatus) {
        // mocken spelar två olika scenarion, först nekad betalning och sedan godkänd betalning
        when(terminal.charge(amount))
            // misslyckad betalning (DECLINED eller ERROR)
            .thenReturn(new PaymentResult(failedStatus))
            // godkänt
            .thenReturn(APPROVED);

        payment.pay();
        payment.pay();

        assertEquals(PaymentState.PAID, payment.getState());
        // kolla om terminalen anropas 2 gånger
        verify(terminal, times(2)).charge(amount);
    }

    @Test 
    void cantPayAfterApprovedPayment() {
        // mocken returnerar en godkänd betalning
        when(terminal.charge(amount))
            .thenReturn(APPROVED);
        payment.pay();
        
        // andra anropet till pay() ska kasta ett IllegalStateException eftersom betalningen redan är godkänd
        assertThrows(IllegalStateException.class, payment::pay); 

        // kontrollera att terminalen bara anropas en gång
        verify(terminal, times(1)).charge(amount);

    }

    @ParameterizedTest
    @ValueSource(ints = {-250, -1})
    void negativeAmountThrowsException(long amountInOre) {
        //Skapa en Payment med ett negativt belopp (K1, testvärde -250 & -1 öre)
        Payment negativePayment = new Payment(terminal, new Money(amountInOre));

        // Utför och kontrollera: pay() ska kasta ett IllegalArgumentException med ett specifikt felmeddelande 
        // assertThrows returnerar det kastade undantaget, vilket vi kan använda för att kontrollera meddelandet
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, negativePayment::pay);

        // Rätt meddelande är vad som skiljer K1 (negativt) från K2 (0) 
        assertEquals("Beloppet får ej vara negativt.", exception.getMessage());

        // Mockito: terminalen får inte anropas oavsett beloppet
        verify(terminal, never()).charge(any());
    }

    @Test 
    void zeroAmountIsRejected(){

        // Skapa en Payment med ett belopp på 0 öre (K2, testvärde 0 öre, även ett gränsvärde)
        Payment zeroPayment = new Payment(terminal, new Money(0));

        // Utför och kontrollera: pay() ska kasta ett IllegalArgumentException med ett specifikt felmeddelande
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, zeroPayment::pay);

        // Rätt meddelande är vad som skiljer K2 (0) från K1 (negativt)
        assertEquals("Beloppet får ej vara 0.", exception.getMessage());

        // Mockito: temrinalen får inte anropas oavsett beloppet
        verify(terminal, never()).charge(any());
    }

    @Test 
    void smallestValidAmountIsAccepted() {
       // minsta möjliga belopp som är giltigt (1 öre)
       Money oneOre = new Money(1);
       Payment smallestPayment = new Payment(terminal, oneOre);

       when(terminal.charge(oneOre))
            .thenReturn(APPROVED);

        // utför betalningen, detta ska inte kasta något undantag eftersom beloppet är giltigt
        smallestPayment.pay();
        
        // ontrollera att betalningens status är "PAID" efter en godkänd betalning
        assertEquals(PaymentState.PAID, smallestPayment.getState());
        verify(terminal).charge(oneOre);
    }

    @Test
    void terminalErrorGivesStateDeclined() {
        // terminalen returnerar ett fel (ERROR) när charge() anropas med amount (t ex nätverksfel eller liknande   )
        when(terminal.charge(amount))
            .thenReturn(ERROR);

        // försök att genomföra betalningen
        payment.pay();

        // betalningen ska nu vara markerad som "DECLINED"
        assertEquals(PaymentState.DECLINED, payment.getState());
    }
}
