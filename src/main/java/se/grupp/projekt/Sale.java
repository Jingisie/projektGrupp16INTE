package se.grupp.projekt;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Money getTotal() {
        Money total = new Money(0);
        for (Product product : products) {
            total = total.add(product.getPrice());
        }
        return total;
    }
     public static void main(String[] args) {
 }
}
