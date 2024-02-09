package pe.pjh.ws.application.port.out;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;

import java.util.List;

public interface WordSearchPort {

    List<String> requestSourceName(Database database, String topicId, String[] docWords);

    List<List<String>> requestDocumentName(Database database, String topicId, String[] sourceWords);

}
