package se.grupp.projekt;

public class VatRate {

    private final int percentage;
    
    public VatRate(int percentage) {
        this.percentage = percentage;
    }

    public Money calculateVat(Money money) {
        long vatAmountInOre = (money.getAmountInOre() * percentage) / (100 + percentage);
        return new Money(vatAmountInOre);
    }
}
