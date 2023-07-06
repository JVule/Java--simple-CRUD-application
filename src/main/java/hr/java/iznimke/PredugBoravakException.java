package hr.java.iznimke;

public class PredugBoravakException extends Exception{
    public PredugBoravakException() {
    }

    public PredugBoravakException(String message) {
        super(message);
    }

    public PredugBoravakException(String message, Throwable cause) {
        super(message, cause);
    }

    public PredugBoravakException(Throwable cause) {
        super(cause);
    }
}
