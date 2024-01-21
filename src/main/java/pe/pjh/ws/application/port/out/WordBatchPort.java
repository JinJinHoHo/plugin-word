package pe.pjh.ws.application.port.out;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import pe.pjh.ws.application.service.dataset.Word;

import java.util.List;

public interface WordBatchPort {

    /**
     * 단어 일괄 생성.
     * @param database
     * @param words
     * @throws CouchbaseLiteException
     */
    void createWords(Database database, List<Word> words) throws CouchbaseLiteException;
}
