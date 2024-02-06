package pe.pjh.ws.application.port.out;

import com.couchbase.lite.Database;
import pe.pjh.ws.application.service.dataset.Topic;

import java.util.List;

public interface TopicMngPort {
    List<Topic> findByTopic(Database database);

}
