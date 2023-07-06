package hr.java.iznimke;

public class KriviRoditeljException extends RuntimeException{
    public KriviRoditeljException() {
    }

    public KriviRoditeljException(String message) {
        super(message);
    }

    public KriviRoditeljException(String message, Throwable cause) {
        super(message, cause);
    }

    public KriviRoditeljException(Throwable cause) {
        super(cause);
    }
}
