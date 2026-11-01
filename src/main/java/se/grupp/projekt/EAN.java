package se.grupp.projekt;

public class EAN {
private static final String Foretagspr= "731234";
 public static void validera(String ean) {
        if (ean == null || ean.isEmpty()) {
            throw new IllegalArgumentException("EAN får inte vara null eller tom");
        }


        if (ean.length() != 13) {
            throw new IllegalArgumentException("EAN måste vara 13 tecken");
        }
        if (!ean.matches("\\d+")) {
            throw new IllegalArgumentException("Felaktigt tecken");
        }
        if (!ean.startsWith(Foretagspr)) {
            throw new IllegalArgumentException("Fel företags prefix");
        }
        if (!harRattKontrollsiffra(ean)) {
            throw new IllegalArgumentException("Fel kontrollsiffra");
        }
 }
        public static boolean arGiltig(String ean) {
        try {
            validera(ean);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    private static boolean harRattKontrollsiffra(String ean) {
    int summa = 0;
    for (int i = 0; i < 12; i++) {
        int siffra = ean.charAt(i) - '0';
        // Växelvis vikt 1 och 3
        summa += (i % 2 == 0) ? siffra : siffra * 3;
    }
    int kontrollsiffra = (10 - (summa % 10)) % 10;
    return kontrollsiffra == ean.charAt(12) - '0';

    
}

}