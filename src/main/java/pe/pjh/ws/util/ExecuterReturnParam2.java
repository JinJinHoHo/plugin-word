package pe.pjh.ws.util;

@FunctionalInterface
public interface ExecuterReturnParam2<T1, T2> {
    /**
     * Runs this operation.
     */
    T1 execute(T2 t2);
}
