package pe.pjh.ws.adapter.out;

import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.couchbase.LocalCouchbaseTopicRepository;
import pe.pjh.ws.adapter.out.couchbase.LocalCouchbaseWordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.adapter.out.datasource.LocalCouchbaseDataSource;
import pe.pjh.ws.application.service.WDException;
import pe.pjh.ws.application.service.setting.DataSetSetting;

import java.util.HashMap;
import java.util.Map;

public final class DataSetManager {

    private static final Logger LOG = Logger.getInstance(DataSetManager.class);

    private final Map<String, DataSet> repositoryMap = new HashMap<>();

    public DataSet makeDateSet(DataSetSetting setting) {

        if (repositoryMap.containsKey(setting.getDataSetName())) {
            return repositoryMap.get(setting.getDataSetName());
        }
        DataSet dataSet = switch (setting.getSourceSetting().getDataSourceType()) {
            case LocalCouchbaseLite -> {
                DataSource ds = new LocalCouchbaseDataSource(setting.getSourceSetting());
                yield new DataSet(
                        ds,
                        new LocalCouchbaseTopicRepository(ds),
                        new LocalCouchbaseWordRepository(ds));
            }
            default -> null;
        };

        if (dataSet != null) {
            LOG.info(setting.getDataSetName() + " 초기화");
            repositoryMap.put(setting.getDataSetName(), dataSet);
            return dataSet;
        }

        throw new WDException(setting.getDataSetName() + " 설정 오류.");
    }


    public record DataSet(
            DataSource dataSource,
            TopicRepository topicRepository,
            WordRepository wordRepository) {
    }
}
