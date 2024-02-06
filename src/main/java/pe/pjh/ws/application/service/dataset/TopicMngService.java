package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.Database;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.port.out.TopicMngPort;
import pe.pjh.ws.application.service.StatusService;

import java.util.List;

public class TopicMngService extends AbstractDataSourceService {

    TopicMngPort topicMngPort;

    public TopicMngService(
            DataSetManager dataSetManager,
            StatusService statusService,
                           TopicMngPort topicMngPort) {
        super(statusService, dataSetManager);
        this.topicMngPort = topicMngPort;
    }

    public List<Topic> findByTopic(){
       return getDataSource().execute(database -> topicMngPort.findByTopic(database),List.class);
    }
}
