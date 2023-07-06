package hr.java.iznimke;

public class NemaRoditeljException extends RuntimeException{
    public NemaRoditeljException() {
    }

    public NemaRoditeljException(String message) {
        super(message);
    }

    public NemaRoditeljException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemaRoditeljException(Throwable cause) {
        super(cause);
    }
}
