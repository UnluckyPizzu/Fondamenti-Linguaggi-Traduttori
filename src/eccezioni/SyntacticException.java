package eccezioni;

public class SyntacticException extends Exception {

    public SyntacticException(String message) {
        super(message);
    }

    public SyntacticException(String message, Throwable cause) {
        super(message, cause);
    }
}
