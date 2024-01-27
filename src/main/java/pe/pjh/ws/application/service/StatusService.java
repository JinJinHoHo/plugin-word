package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.util.ExecuterParam1;

import java.nio.file.Path;

public class StatusService {

    private final Path workPath;
    private DataSetSetting currentDataSetSetting;

    private final Integer currentTopicId = BundleDataSet.CMN_STN_TRM_6TH.getTopicNo();

    private final ExecuterParam1<DataSetSetting> changeDataSetSettingEvent;

    /**
     * @param workPath 플러그인 작업 경로.
     * @param changeDataSetSettingEvent 데이터셋 변경 시 발생 이벤트
     */
    protected StatusService(
            Path workPath,
            ExecuterParam1<DataSetSetting> changeDataSetSettingEvent) {
        this.workPath = Path.of(workPath.toString(), "wordic");
        this.changeDataSetSettingEvent = changeDataSetSettingEvent;
    }

    public DataSetSetting getCurrentDataSetSetting() {
        return currentDataSetSetting;
    }

    public void setCurrentDataSetSetting(DataSetSetting currentDataSetSetting) {
        this.currentDataSetSetting = currentDataSetSetting;
        try {
            changeDataSetSettingEvent.execute(this.currentDataSetSetting);
        } catch (CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getWorkPath() {
        return workPath;
    }


    public Integer getCurrentTopicId() {
        return currentTopicId;
    }
}
