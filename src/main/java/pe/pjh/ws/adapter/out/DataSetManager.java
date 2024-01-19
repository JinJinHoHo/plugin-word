package pe.pjh.ws.adapter.out;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.couchbase.LocalCouchbaseTopicRepository;
import pe.pjh.ws.adapter.out.couchbase.LocalCouchbaseWordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.adapter.out.datasource.LocalCouchbaseDataSource;
import pe.pjh.ws.application.WDException;
import pe.pjh.ws.application.service.setting.DataSetSetting;

import java.util.HashMap;
import java.util.Map;

@Service
public final class DataSetManager {

    private static final Logger LOG = Logger.getInstance(DataSetManager.class);

    private final Map<String, DataSourceRepository> repositoryMap = new HashMap<>();

    public static DataSetManager getInstance() {
        return ApplicationManager.getApplication().getService(DataSetManager.class);
    }

    public static TopicRepository getTopicRepository(String dataSetName) {
        return getInstance().repositoryMap.get(dataSetName).topicRepository();
    }

    public static WordRepository getWordRepository(String dataSetName) {
        return getInstance().repositoryMap.get(dataSetName).wordRepository();
    }

    public DataSourceRepository makeDateSet(DataSetSetting setting) {
        DataSourceRepository dataSourceRepository = switch (setting.getSourceSetting().getDataSourceType()) {
            case LocalCouchbaseLite -> {
                DataSource ds = new LocalCouchbaseDataSource(setting.getSourceSetting());
                yield new DataSourceRepository(
                        ds,
                        new LocalCouchbaseTopicRepository(ds),
                        new LocalCouchbaseWordRepository(ds));
            }
            default -> null;
        };

        if (dataSourceRepository != null) {
            LOG.info(setting.getDataSetName() + " 초기화");
            repositoryMap.put(setting.getDataSetName(), dataSourceRepository);
            return dataSourceRepository;
        }

        throw new WDException(setting.getDataSetName() + " 설정 오류.");
    }


    public record DataSourceRepository(
            DataSource dataSource,
            TopicRepository topicRepository,
            WordRepository wordRepository) {
    }
}
