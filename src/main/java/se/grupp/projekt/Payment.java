package se.grupp.projekt;

public class Payment {

    private final PaymentTerminal terminal;
    private final Money amount;
    private PaymentState state = PaymentState.OPEN;

    public Payment(PaymentTerminal terminal, Money amount) {
        this.terminal = terminal; 
        this.amount = amount;
    }

    public void pay(){
        if(state == PaymentState.PAID) {
            throw new IllegalStateException("Betalningen är redan godkänd och kan inte genomföras igen.");
        }
        PaymentResult result = terminal.charge(amount);
        if (result.status() == PaymentStatus.APPROVED) {
            state = PaymentState.PAID;
        } else {
            state = PaymentState.DECLINED;
        }
    }

    public PaymentState getState() {
        return state; 
    }   
}
