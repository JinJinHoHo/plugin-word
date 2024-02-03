package pe.pjh.ws.util;

import com.couchbase.lite.CouchbaseLiteException;

@FunctionalInterface
public interface ExecuterReturnParam1<T1> {
    /**
     * Runs this operation.
     */
    T1 execute() throws CouchbaseLiteException;
}
