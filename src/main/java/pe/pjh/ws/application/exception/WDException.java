package pe.pjh.ws.application.exception;

public class WDException extends RuntimeException{

    public WDException(String message) {
        super(message);
    }

    public WDException(String message,Throwable cause) {
        super(message,cause);
    }
}
