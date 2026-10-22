package se.grupp.projekt;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<Item> items;

    public Sale() {
        this.items = new ArrayList<>();
    }
      public void addItem(Product item) {
          items.add(item);
    }
    public double getTotal(){
        double total= 0.0;
    for (Item t : items){
        total += t.getPrice();
    }
    return total;
}

}


