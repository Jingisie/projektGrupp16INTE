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

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Money)) return false;
        Money money = (Money) obj;
        return amountInOre == money.amountInOre;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(amountInOre);
    }

    @Override 
    public String toString() {
        return String.format("%d, %02d kr", amountInOre / 100, amountInOre % 100);
    }
    
}
