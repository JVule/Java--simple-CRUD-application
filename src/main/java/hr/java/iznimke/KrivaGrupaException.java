package hr.java.iznimke;

public class KrivaGrupaException extends Exception{
    public KrivaGrupaException() {
    }

    public KrivaGrupaException(String message) {
        super(message);
    }

    public KrivaGrupaException(String message, Throwable cause) {
        super(message, cause);
    }

    public KrivaGrupaException(Throwable cause) {
        super(cause);
    }
}
