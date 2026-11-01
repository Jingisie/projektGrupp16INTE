package se.grupp.projekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sale {

    private List<Product> products;

    public Sale() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(
                "Produkten kan inte vara null"
            );
        }

        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
     public int getProductCount() {
        return products.size();
    }
public Money getTotal() {
        int total = 0;

        for (Product product : products) {
            total += product.getPrice().getAmountInOre();
        }

        return new Money(total);
    }
}