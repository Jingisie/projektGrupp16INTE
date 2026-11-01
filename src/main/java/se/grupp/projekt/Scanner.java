package se.grupp.projekt;
import java.util.Map;

public class Scanner {

    private Sale sale;
    private Map<String, Product> register;
  public Scanner(Sale sale, Map<String, Product> register) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale kan inte vara null");
        }
if (register == null) {
            throw new IllegalArgumentException("Registret kan inte vara null");
        }
this.sale = sale;
this.register = register;
}
public void scan(String ean) {
EAN.validera(ean);
Product product = register.get(ean);
if (product == null) {
throw new IllegalArgumentException("Produkten hittades inte");
  }
 sale.addProduct(product);
 }
}



