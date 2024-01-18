package pe.pjh.ws.adapter.out.couchbase;

import pe.pjh.ws.adapter.out.TopicRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.adapter.out.datasource.LocalCouchbaseDataSource;


public class LocalCouchbaseTopicRepository implements TopicRepository {

    final DataSource dataSource;

    public LocalCouchbaseTopicRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
