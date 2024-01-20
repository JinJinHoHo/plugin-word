package pe.pjh.ws.application.service;

import pe.pjh.ws.application.port.out.TopicSearchPort;
import pe.pjh.ws.application.port.out.WordSearchPort;

public class TermSuggestService {

    TopicSearchPort topicSearchPort;
    WordSearchPort wordSearchPort;

    public TermSuggestService(TopicSearchPort topicSearchPort, WordSearchPort wordSearchPort) {
        this.topicSearchPort = topicSearchPort;
        this.wordSearchPort = wordSearchPort;
    }

    public String requestSourceName(String documentName){
        return null;
    }

    public String requestDocumentName(String SourceName){
        return null;
    }
}
