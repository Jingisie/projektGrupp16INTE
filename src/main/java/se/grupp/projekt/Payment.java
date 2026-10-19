package se.grupp.projekt;

public class Payment {

    private final PaymentTerminal terminal;
    private final Money amount;
    private PaymentState state = PaymentState.OPEN;

    public Payment(PaymentTerminal terminal, Money amount){
        this.terminal = terminal;
        this.amount = amount;
    }

    public void pay(){
        terminal.charge(amount);
        state = PaymentState.PAID;
    }

    public PaymentState getState(){
        return state; 
    }   
}
