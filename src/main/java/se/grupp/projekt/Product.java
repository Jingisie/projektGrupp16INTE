package se.grupp.projekt;

public class Product {
    private final String name;
    private final Money price;
    private final VatRate vatRate;

    // Skapar en vara med namn, pris och moms.
    public Product(String name, Money price, VatRate vatRate) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Produktnamnet kan inte vara tomt");
        }

        this.name = name;
        this.price = price;
        this.vatRate = vatRate;
    }

    // Räknar ut varans moms.
    public Money getVatAmount() {
        return vatRate.calculateVat(price);
    }

    // Räknar ut varans moms efter rabatt.
    public Money getVatAmount(int discountPercentage) {
        Price originalPrice = () -> price;
        Money discountedPrice =
                new PercentageDiscount(originalPrice, discountPercentage).amount();

        return vatRate.calculateVat(discountedPrice);
    }

    // Hämtar varans pris.
    public Money getPrice() {
        return price;
    }

    // Visar varans namn och pris som text.
    @Override 
    public String toString() {
        return name + ": " + price;
    }
    
}
