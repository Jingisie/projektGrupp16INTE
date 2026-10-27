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
            rows.append(product.getName())
                .append("  ")
                .append(product.getPrice())   
                .append("\n");
        }

        return "Totalt: " + sale.getTotal() + "\n" + rows;
    }
}
