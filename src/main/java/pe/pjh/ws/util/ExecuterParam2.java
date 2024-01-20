package pe.pjh.ws.util;

import com.couchbase.lite.CouchbaseLiteException;

@FunctionalInterface
public interface ExecuterParam2<T1, T2> {
    /**
     * Runs this operation.
     */
    T1 execute(T2 t2) throws CouchbaseLiteException;
}
