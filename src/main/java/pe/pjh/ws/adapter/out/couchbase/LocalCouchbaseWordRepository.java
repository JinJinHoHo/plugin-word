package pe.pjh.ws.adapter.out.couchbase;

import pe.pjh.ws.adapter.out.WordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.adapter.out.datasource.LocalCouchbaseDataSource;
import pe.pjh.ws.application.Word;

import java.util.List;

public class LocalCouchbaseWordRepository implements WordRepository {

    final DataSource dataSource;

    public LocalCouchbaseWordRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void processBatch(List<Word> words) {

    }
}
