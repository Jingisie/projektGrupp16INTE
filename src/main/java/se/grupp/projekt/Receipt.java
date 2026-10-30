package se.grupp.projekt;

<<<<<<< HEAD
public class Receipt {

    private final Sale sale;

    public Receipt(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale får inte vara tom");
        }
        this.sale = sale;
    }

    public String format() {
        StringBuilder rows = new StringBuilder();

        for (Product product : sale.getProducts()) {
            rows.append(product.getName());
              rows.append("  ");
                rows.append(product.getPrice());   
                rows.append("\n");
        }

        return "Totalt: " + sale.getTotal() + "\n" + rows;
    }
     public static void main(String[] args) {
 }
=======
import java.util.List;

public class Receipt {
    private final List<Product> products;

    // Skapar ett kvitto med varorna i köpet.
    public Receipt(List<Product> products) {
        this.products = List.copyOf(products);
    }

    // Räknar ihop priserna för kvittots varor.
    public Money getSubtotal() {
        Money subtotal = new Money(0);

        for (Product product : products) {
            subtotal = subtotal.add(product.getPrice());
        }

        return subtotal;
    }

    // Räknar ihop momsen för alla varor.
    public Money getVatTotal() {
        Money vatTotal = new Money(0);

        for (Product product : products) {
            vatTotal = vatTotal.add(product.getVatAmount());
        }

        return vatTotal;
    }

    // Räknar ihop momsen efter rabatt för alla varor.
    public Money getVatTotal(int discountPercentage) {
        Money vatTotal = new Money(0);

        for (Product product : products) {
            vatTotal = vatTotal.add(product.getVatAmount(discountPercentage));
        }

        return vatTotal;
    }

    // Returnerar kvittots totalbelopp.
    public Money getTotal() {
        return getSubtotal();
    }

    // Returnerar kvittots total efter rabatt.
    public Money getTotal(int discountPercentage) {
        Price originalPrice = this::getSubtotal;
        return new PercentageDiscount(originalPrice, discountPercentage).amount();
    }

    // Räknar ut rabattbeloppet på kvittot.
    public Money getDiscountAmount(int discountPercentage) {
        long discountInOre = getSubtotal().getAmountInOre()
                - getTotal(discountPercentage).getAmountInOre();

        return new Money(discountInOre);
    }

    // Hämtar varorna som ska visas på kvittot.
    public List<Product> getProducts() {
        return products;
    }
>>>>>>> d8ef0c661f889520515066d98436f5517f2bcce3
}
