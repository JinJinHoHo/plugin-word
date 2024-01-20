package pe.pjh.ws.application.service;

import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.util.ExecuterParam1;

import java.nio.file.Path;

public class StatusService {

    private final Path workPath;
    private DataSetSetting currentDataSetSetting;

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
        changeDataSetSettingEvent.execute(this.currentDataSetSetting);
    }

    public Path getWorkPath() {
        return workPath;
    }
}
