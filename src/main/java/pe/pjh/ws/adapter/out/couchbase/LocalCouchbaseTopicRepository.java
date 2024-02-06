package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.*;
import pe.pjh.ws.adapter.out.TopicRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.exception.DataSourceException;
import pe.pjh.ws.application.service.dataset.Topic;

import java.util.List;


public class LocalCouchbaseTopicRepository extends AbstractCouchbase implements TopicRepository {

    public LocalCouchbaseTopicRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCollectionName() {
        return "topic";
    }

    public void createTopic(Database database, Topic topic) {

        try {
            getCollection(database).save(topic.getDocument());
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }

    public List<Topic> findByTopic(Database database) {
        Query query = QueryBuilder
                .select(SelectResult.all())
                .from(com.couchbase.lite.DataSource.collection(getCollection(database)));
        try (ResultSet results = query.execute()) {
            return results.allResults().stream()
                    .map(Topic::new).toList();
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }
}
