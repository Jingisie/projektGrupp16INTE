package se.grupp.projekt;

public class Receipt {

    private final Sale sale;

    public Receipt(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale får inte vara null");
        }
        this.sale = sale;
    }
  
}
