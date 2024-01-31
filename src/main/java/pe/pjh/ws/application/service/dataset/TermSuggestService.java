package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.CouchbaseLiteException;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.exception.QueryException;
import pe.pjh.ws.application.port.out.TopicSearchPort;
import pe.pjh.ws.application.port.out.WordSearchPort;
import pe.pjh.ws.application.service.StatusService;

import java.util.List;
import java.util.stream.Collectors;

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

    public String requestSourceName(String documentName, Notation notation) throws QueryException {
        return requestSourceName(statusService.getCurrentTopicId(), documentName, notation);
    }

    public String requestSourceName(Integer topicNo, String documentName, Notation notation) {

        String[] documentWords = documentName.split(" ");

        List<String> wordList = getDataSource()
                .execute(database -> wordSearchPort.requestSourceName(database, topicNo, documentWords), List.class);
        return notation.convert(wordList);
    }

    public String requestDocumentName(String sourceName) throws QueryException {
        return requestDocumentName(statusService.getCurrentTopicId(), sourceName);
    }

    public String requestDocumentName(Integer topicNo, String sourceName) {

        //카멜 또는 파스칼 케이스로 되여 있는것을 분리 처리.
        String[] split = sourceName.replaceAll("([A-Za-z])([a-z]+)", " $1$2")
                .trim().toUpperCase()
                .split(" ");

        List<List<String>> wordList = getDataSource()
                .execute(database -> wordSearchPort.requestDocumentName(database, topicNo, split), List.class);


        return wordList.stream()
                .map(strings -> strings.isEmpty() ? "?" : strings.get(0))
                .collect(Collectors.joining(" "));
    }
}
