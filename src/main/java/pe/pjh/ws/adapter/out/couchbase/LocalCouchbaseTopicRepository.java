package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.Collection;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.MutableDocument;
import pe.pjh.ws.adapter.out.TopicRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.Topic;


public class LocalCouchbaseTopicRepository implements TopicRepository {

    final DataSource dataSource;

    public LocalCouchbaseTopicRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTopic(Database database, Topic topic) throws CouchbaseLiteException {

        Collection collection = database.getCollection("topic");
        if(collection==null) return;

        collection.save(topic.getDocument());
    }
}
