package pe.pjh.ws.application.service.setting;

import java.util.List;

public class SettingService {


    private List<DataSetSetting> dataSetSettings;

    public SettingService() {
    }

    public void setDataSetSettings(List<DataSetSetting> dataSetSettings) {
        this.dataSetSettings = dataSetSettings;
    }

    public List<DataSetSetting> getDataSetSettings() {
        return dataSetSettings;
    }
}
