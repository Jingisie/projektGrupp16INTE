package se.grupp.projekt;

public class Payment {
    public Payment(PaymentTerminal terminal, Money amount){

    }

    public void pay(){

    }

    public PaymentState getState(){
        return PaymentState.OPEN;
    }   
}
