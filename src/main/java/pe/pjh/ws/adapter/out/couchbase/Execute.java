package pe.pjh.ws.adapter.out.couchbase;

@FunctionalInterface
public interface Execute<T, S> {
    /**
     * Runs this operation.
     */
    T execute(S s);
}
