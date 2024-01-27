package pe.pjh.ws.application.exception;

public class QueryException extends WDException{

    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message,cause);
    }

    public QueryException(Throwable cause) {
        super("데이터 소스 에러",cause);
    }
}
