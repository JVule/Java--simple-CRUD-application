package hr.java.iznimke;

public class NemaDjetetaException extends RuntimeException{
    public NemaDjetetaException() {
    }

    public NemaDjetetaException(String message) {
        super(message);
    }

    public NemaDjetetaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemaDjetetaException(Throwable cause) {
        super(cause);
    }
}
