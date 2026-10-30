package se.grupp.projekt;

@FunctionalInterface
public interface Price {
    // Hämtar prisbeloppet.
    Money amount();
}
