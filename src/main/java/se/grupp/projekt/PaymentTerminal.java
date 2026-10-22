package se.grupp.projekt;

public interface PaymentTerminal {
    PaymentResult charge(Money amount);
}
