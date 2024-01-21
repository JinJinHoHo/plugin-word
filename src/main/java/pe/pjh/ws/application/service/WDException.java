package pe.pjh.ws.application.service;

public class WDException extends RuntimeException{

    public WDException(String message) {
        super(message);
    }

    public WDException(String message,Throwable cause) {
        super(message,cause);
    }
}
