package se.grupp.projekt;

public class Item {
    private String name ;
    private int  priceInOre;
    public Item(String name, long priceInOre) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        }
        if (priceInOre < 0) {
            throw new IllegalArgumentException("Priset får inte vara negativt");
        }
        this.name = name;
        this.priceInOre = (int) priceInOre;
    }
    
    public String getName() {
        return name;
    }
 
    public int getPriceInOre() {
        return priceInOre;
    }


}
