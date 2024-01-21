package pe.pjh.ws.application.port.out;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;

import java.util.List;

public interface WordSearchPort {

    List<String> requestSourceName(Database database, Integer topicNo, String[] docWords) throws CouchbaseLiteException;

    List<List> requestDocumentName(Database database, Integer topicNo, String[] sourceWords) throws CouchbaseLiteException;

}
