package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.setting.DataSourceSetting;
import pe.pjh.ws.util.ExecuterParam1;

import java.nio.file.Path;

public class StatusService {

    private final Path workPath;
    private DataSourceSetting currentDataSourceSetting;

    private final String currentTopicId = BundleDataSet.CMN_STN_TRM_6TH.getTopicId();

    private final ExecuterParam1<DataSourceSetting> changeDataSetSettingEvent;

    /**
     * @param workPath 플러그인 작업 경로.
     * @param changeDataSetSettingEvent 데이터셋 변경 시 발생 이벤트
     */
    protected StatusService(
            Path workPath,
            ExecuterParam1<DataSourceSetting> changeDataSetSettingEvent) {
        this.workPath = Path.of(workPath.toString(), "wordic");
        this.changeDataSetSettingEvent = changeDataSetSettingEvent;
    }

    public DataSourceSetting getCurrentDataSetSetting() {
        return currentDataSourceSetting;
    }

    public void setCurrentDataSetSetting(DataSourceSetting currentDataSourceSetting) {
        this.currentDataSourceSetting = currentDataSourceSetting;
        try {
            changeDataSetSettingEvent.execute(this.currentDataSourceSetting);
        } catch (CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getWorkPath() {
        return workPath;
    }


    public String getCurrentTopicId() {
        return currentTopicId;
    }
}
