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
            //en betalning med ett negativt belopp är inte tillåten, kasta ett undantag (K1)
            if (amount.getAmountInOre() < 0) {
                validateAmount(); //validera beloppet, detta kommer att kasta ett IllegalArgumentException med ett specifikt felmeddelande
            }   
            //en betalning med ett belopp på 0 är inte tillåten, kasta ett undantag (K2)
            if (amount.getAmountInOre() == 0) {
                validateAmount(); //validera beloppet, detta kommer att kasta ett IllegalArgumentException med ett specifikt felmeddelande
            }
            //skicka debiteringsförfrågan till terminalen och uppdatera tillståndet baserat på resultatet
            PaymentResult result;
            try{
                result = terminal.charge(amount);
            } catch (RuntimeException e) {
                //terminalen är inte tillgänglig, kastar ett eget undantag som inte behöver deklareras med throws
                throw new TerminalUnavailableException("Terminalen svarar inte", e);
            }
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
}
