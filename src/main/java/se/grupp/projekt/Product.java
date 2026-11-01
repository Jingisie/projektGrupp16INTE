package se.grupp.projekt;

public class Product {
    private static final String COMPANY_PREFIX = "731234";
    private static final int EAN_LENGTH = 13;

    private final String name;
    private final Money price;
    private final VatRate vatRate;
    private final String ean;

    public Product(String name, Money price, VatRate vatRate, String ean) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Produktnamnet kan inte vara tomt");
        }
        validateEan(ean);

        this.name = name;
        this.price = price;
        this.vatRate = vatRate;
        this.ean = ean;
    }

    // Ordningen spelar roll: varje ogiltig klass ska ge sitt eget felmeddelande
    private static void validateEan(String ean) {
        if (ean == null || ean.isEmpty()) {
            throw new IllegalArgumentException("EAN-koden får inte vara tom");
        }
        if (ean.length() < EAN_LENGTH) {
            throw new IllegalArgumentException("För få tecken");
        }
        if (ean.length() > EAN_LENGTH) {
            throw new IllegalArgumentException("För många tecken");
        }
        for (char c : ean.toCharArray()) {
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("Felaktigt tecken");
            }
        }
        if (!ean.startsWith(COMPANY_PREFIX)) {
            throw new IllegalArgumentException("Fel företagsprefix");
        }
        if (!hasValidCheckDigit(ean)) {
            throw new IllegalArgumentException("Fel kontrollsiffra");
        }
    }

    // Vikterna 1,3,1,3... på de 12 första siffrorna; kontrollsiffran gör summan delbar med 10
    private static boolean hasValidCheckDigit(String ean) {
        int sum = 0;
        for (int i = 0; i < EAN_LENGTH - 1; i++) {
            int digit = ean.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int expected = (10 - sum % 10) % 10;
        return expected == ean.charAt(EAN_LENGTH - 1) - '0';
    }

    public Money getVatAmount() {
        return vatRate.calculateVat(price);
    }

    public String getEan() {
        return ean;
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}