package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;

@FunctionalInterface
public interface ExecuterTestContext<T1, T2> {
    /**
     * Runs this operation.
     */
    void execute(T1 t1, T2 t2) throws CouchbaseLiteException;
}
