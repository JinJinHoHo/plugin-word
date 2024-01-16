package pe.pjh.ws.application.port.out;

import pe.pjh.ws.application.Word;

import java.util.List;

public interface WordBatchPort {

    void processBatch(List<Word> words);
}
