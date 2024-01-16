package pe.pjh.ws.util;

@FunctionalInterface
public interface ExecuterParam1<T1> {
    /**
     * Runs this operation.
     */
    void execute(T1 s) throws Exception;
}
