package se.grupp.projekt;

// Gör till ett eget undantag som inte behöver deklareras med throws 
public class TerminalUnavailableException extends RuntimeException {
    // cause sparar terminalens ursprungliga fel inuti vårt eget undantag, så ingen viktig info försvinner av misstag
    public TerminalUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
