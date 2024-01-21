package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.port.out.WordManagerPort;
import pe.pjh.ws.application.service.StatusService;
import pe.pjh.ws.application.service.setting.DataSetSetting;

public class WordDicMngService extends AbstractDataSourceService {

    private static final Logger log = Logger.getInstance(WordDicMngService.class);

    WordManagerPort wordManagerPort;

    public WordDicMngService(
            DataSetManager dataSetManager,
            StatusService statusService,
            WordManagerPort wordManagerPort) {
        super(statusService, dataSetManager);
        this.wordManagerPort = wordManagerPort;

    }

    public Integer countWordByTopic(Integer topicNo) throws CouchbaseLiteException {

        return getDataSource()
                .execute(database -> {
                    try {
                        return wordManagerPort.countWordByTopic(database, topicNo);
                    } catch (CouchbaseLiteException e) {
                        throw new RuntimeException(e);
                    }
                }, Integer.class);
    }

}
