package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.Collection;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import pe.pjh.ws.adapter.out.WordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.Word;

import java.util.List;

public class LocalCouchbaseWordRepository implements WordRepository {

    final DataSource dataSource;

    public LocalCouchbaseWordRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void createWords(Database database, List<Word> words) throws CouchbaseLiteException {

        Collection collection = database.getCollection("word");
        if (collection == null) {
            collection = database.createCollection("word");
        }

        for (Word word : words){
            collection.save(word.getDocument());
        }
    }
}
