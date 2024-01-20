package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;

@FunctionalInterface
public interface ExecuterTestContext<T1, T2, T3> {
    /**
     * Runs this operation.
     */
    void execute(T1 t1, T2 t2, T3 t3) throws CouchbaseLiteException;
}
