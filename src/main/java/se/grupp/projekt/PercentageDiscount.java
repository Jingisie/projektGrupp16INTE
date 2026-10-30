package se.grupp.projekt;

// Omsluter ett pris och drar av en procentuell rabatt.
public class PercentageDiscount implements Price {
    private final Price originalPrice;
    private final int percentage;

    // Skapar en rabatt som omsluter ett pris.
    public PercentageDiscount(Price originalPrice, int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(
                    "Discount must be between 0 and 100 percent"
            );
        }

        this.originalPrice = originalPrice;
        this.percentage = percentage;
    }

    // Returnerar priset efter rabatt.
    @Override
    public Money amount() {
        long discountedAmountInOre =
                originalPrice.amount().getAmountInOre() * (100 - percentage) / 100;

        return new Money(discountedAmountInOre);
    }

    // Beräknar priset efter rabatt.
    public static Money apply(Money originalPrice, int percentage) {
        Price price = () -> originalPrice;
        return new PercentageDiscount(price, percentage).amount();
    }
}
