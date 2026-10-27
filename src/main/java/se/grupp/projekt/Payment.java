package se.grupp.projekt;

//Representerar en betalning av något belopp som kan genomföras via en PaymentTerminal.
public class Payment {

    private final PaymentTerminal terminal; //terminalen som utför debiteringen
    private final Money amount; //beloppet som ska betalas
    private PaymentState state = PaymentState.OPEN; //ny betalning startar alltid i "OPEN"-tillståndet

    public Payment(PaymentTerminal terminal, Money amount) {
        this.terminal = terminal; 
        this.amount = amount;
    }

    //Försöker genomföra betalningen via terminalen. Om betalningen är godkänd ändras tillståndet till "PAID", annars till "DECLINED".
        public void pay(){
            //en redan genomförd betalning (PAID) kan inte genomföras igen, kasta ett undantag
            if(state == PaymentState.PAID) {
                throw new IllegalStateException("Betalningen är redan godkänd och kan inte genomföras igen.");
            }
            
            // validera beloppet innan betalningen genomförs (K1 eller K2 kastar ett IllegalArgumentException med ett specifikt felmeddelande)
            validateAmount(); 
            
            // betalningen väntar svar från terminalen
            state = PaymentState.AWAITING_PAYMENT;
            
            // skicka debiteringsförfrågan till terminalen och uppdatera tillståndet baserat på resultatet
            PaymentResult result = chargeTerminal();
            if (result.status() == PaymentStatus.APPROVED) {
                state = PaymentState.PAID;
            } else {
                state = PaymentState.DECLINED;
            }
        }

    public PaymentState getState() {
        return state; 
    }   

    private void validateAmount() {
        if (amount.getAmountInOre() < 0) {
            throw new IllegalArgumentException("Beloppet får ej vara negativt.");
        }
        if (amount.getAmountInOre() == 0) {
            throw new IllegalArgumentException("Beloppet får ej vara 0.");
        }
    }

    private PaymentResult chargeTerminal() {
        try {
            return terminal.charge(amount);
        }
        catch(RuntimeException e) {
            //terminalen har kraschat, återställ så att kassören kan starta om betalningen 
            state = PaymentState.OPEN; 
            throw new TerminalUnavailableException("Terminalen svarar inte", e);
        }
    }
}
