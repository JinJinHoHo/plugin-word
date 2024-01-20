package pe.pjh.ws.util;

@FunctionalInterface
public interface ExecuterParam2<T1, T2> {
    /**
     * Runs this operation.
     */
    void execute(T1 t1,T2 t2);
}
