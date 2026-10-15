package se.grupp.projekt;

public class Money {
    //double ger problem med avrundning, därför används long och beloppet sparas i öre
    private final long amountInOre;

    public Money(long amountInOre) {
        this.amountInOre = amountInOre;
    }

    public long getAmountInOre() {
        return amountInOre;
    }

    public Money add(Money other) {
        return new Money(this.amountInOre + other.amountInOre);
    }
    
}
