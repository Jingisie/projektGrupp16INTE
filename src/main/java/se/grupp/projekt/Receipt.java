package se.grupp.projekt;

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
}
