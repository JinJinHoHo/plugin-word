package pe.pjh.ws.application.exception;

public class DataSourceException extends WDException{

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message,cause);
    }

    public DataSourceException(Throwable cause) {
        super("데이터 소스 에러",cause);
    }
}
