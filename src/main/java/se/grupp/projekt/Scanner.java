package se.grupp.projekt;

import java.util.Map;

public class Scanner {
    private Map<String, Product> scanner;


    public Scanner(Map<String, Product> scanner) {
        this.scanner = scanner;
    }


    public Product barcode(String barcode) {
        return scanner.get(barcode);
    }
}


