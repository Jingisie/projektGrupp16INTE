package se.grupp.projekt;

public class Product {
    private final String name;
    private final Money price;
    private final VatRate vatRate;

    public Product(String name, Money price, VatRate vatRate) {
        this.name = name;
        this.price = price;
        this.vatRate = vatRate;
    }

    public Money getVatAmount() {
        return vatRate.calculateVat(price);
    }
    
}
