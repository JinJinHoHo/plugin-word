package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.CouchbaseLiteException;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.port.out.TopicSearchPort;
import pe.pjh.ws.application.port.out.WordSearchPort;
import pe.pjh.ws.application.service.StatusService;

import java.util.List;

public class TermSuggestService extends AbstractDataSourceService {

    TopicSearchPort topicSearchPort;
    WordSearchPort wordSearchPort;

    public TermSuggestService(
            DataSetManager dataSetManager,
            StatusService statusService,
            TopicSearchPort topicSearchPort, WordSearchPort wordSearchPort) {
        super(statusService, dataSetManager);
        this.topicSearchPort = topicSearchPort;
        this.wordSearchPort = wordSearchPort;
    }

    public String requestSourceName(Integer topicNo, String documentName) throws CouchbaseLiteException {
        System.out.println(documentName);
        System.out.println( getDataSource()
                .execute(database -> {
                    try {
                        return wordSearchPort.requestSourceName(database,topicNo,documentName.split(" "));
                    } catch (CouchbaseLiteException e) {
                        throw new RuntimeException(e);
                    }
                }, List.class));;
        return null;
    }

    public String requestDocumentName(String SourceName) {
        return null;
    }
}
