package pe.pjh.ws.application.port.out;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;

public interface WordManagerPort {
    Integer countWordByTopic(Database database, Integer Topic) throws CouchbaseLiteException;
}
