package pe.pjh.ws.application.port.out;

import com.couchbase.lite.Database;
import pe.pjh.ws.application.service.dataset.Word;

import java.util.List;

public interface WordManagerPort {
    Integer countWordByTopic(Database database, Integer Topic);

    List<Word> findByTopic(Database database, Integer Topic);
}
