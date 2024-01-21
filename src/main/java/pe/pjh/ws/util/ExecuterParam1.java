package pe.pjh.ws.util;

import com.couchbase.lite.CouchbaseLiteException;

@FunctionalInterface
public interface ExecuterParam1<T1> {
    /**
     * Runs this operation.
     */
    void execute(T1 s) throws CouchbaseLiteException;
}
