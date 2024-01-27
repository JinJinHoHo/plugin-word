package pe.pjh.ws.application.service.dataset;

import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.port.out.WordManagerPort;
import pe.pjh.ws.application.service.StatusService;

public class WordDicMngService extends AbstractDataSourceService {

    private static final Logger log = Logger.getInstance(WordDicMngService.class);

    private final WordManagerPort wordManagerPort;

    public WordDicMngService(
            DataSetManager dataSetManager,
            StatusService statusService,
            WordManagerPort wordManagerPort) {
        super(statusService, dataSetManager);
        this.wordManagerPort = wordManagerPort;

    }

    public Integer countWordByTopic(Integer topicNo) {
        return getDataSource().execute(
                database -> wordManagerPort.countWordByTopic(database, topicNo),
                Integer.class
        );
    }

}
