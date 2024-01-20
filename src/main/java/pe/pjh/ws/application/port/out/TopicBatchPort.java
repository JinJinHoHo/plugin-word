package pe.pjh.ws.application.port.out;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import pe.pjh.ws.application.Topic;

public interface TopicBatchPort {
    void createTopic(Database database, Topic topic) throws CouchbaseLiteException;
}
