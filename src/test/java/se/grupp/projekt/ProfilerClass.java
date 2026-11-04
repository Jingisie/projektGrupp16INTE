package se.grupp.projekt;

import java.util.Random;

// Simulerar många kunder i kassan så att VisualVM kan mäta var tiden går.
// Varje kund: skannar varor, väger lösvikt, räknar moms och betalar.
public class ProfilerClass {

    private static final String[] NAMES = {"Banan", "Mjölk", "Bröd", "Kaffe", "Ost"};
    private static final long[] PRICES = {1000, 1890, 2990, 5490, 8900}; // i öre
    private static final String[] EANS = {
        "7312340000002", "7312340000019", "7312340000026", "7312340000033", "7312340000040"
    };

    public static void main(String[] args) throws Exception {
        System.out.println("Koppla in VisualVM, tryck sedan Enter...");
        System.in.read();

        Random random = new Random(42); // fast frö ger samma körning varje gång

        // Låtsasvåg: väger 200–2000 g, men misslyckas ibland (testar omförsöken i WeighingService)
        Scale scale = () -> {
            if (random.nextInt(10) == 0) {
                throw new ScaleException("Instabil vikt");
            }
            return Weight.of(200 + random.nextInt(1800), Unit.GRAM);
        };
        WeighingService weighing = new WeighingService(scale);
        WeightPricedItem godis = new WeightPricedItem("Lösgodis", new Money(1290), Unit.HEKTO);

        // Låtsasterminal: nekar var tionde betalning
        PaymentTerminal terminal = amount -> random.nextInt(10) == 0
                ? new PaymentResult(PaymentStatus.DECLINED)
                : new PaymentResult(PaymentStatus.APPROVED);

        System.out.println("Simulerar kunder i 60 sekunder...");
        long customers = 0;
        long end = System.currentTimeMillis() + 60_000;
        while (System.currentTimeMillis() < end) {
            serveCustomer(random, weighing, godis, terminal);
            customers++;
        }
        System.out.println("Klart. Kunder: " + customers);
    }

    private static void serveCustomer(Random random, WeighingService weighing,
                                      WeightPricedItem weighedItem, PaymentTerminal terminal) {
        Money total = new Money(0);
        Money vat = new Money(0);

        // Skanna 1–10 varor
        int items = 1 + random.nextInt(10);
        for (int i = 0; i < items; i++) {
            int index = random.nextInt(NAMES.length);
            Money price = new Money(PRICES[index]);
            Product product = new Product(NAMES[index], price, new VatRate(25), EANS[index]);
            total = total.add(price);
            vat = vat.add(product.getVatAmount());
        }

        // Väg en lösviktsvara
        try {
            total = total.add(weighing.priceOf(weighedItem));
        } catch (ScaleException e) {
            // vågen misslyckades tre gånger, kunden hoppar över varan
        }

        // Betala, och försök igen om kortet nekas
        Payment payment = new Payment(terminal, total);
        while (payment.getState() != PaymentState.PAID) {
            payment.pay();
        }
    }
}