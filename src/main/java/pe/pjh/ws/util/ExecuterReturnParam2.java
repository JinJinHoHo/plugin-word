package pe.pjh.ws.util;

@FunctionalInterface
public interface ExecuterReturnParam2<T1, T2> {
    /**
     * Runs this operation.
     */
    T2 execute(T1 t1);
}
