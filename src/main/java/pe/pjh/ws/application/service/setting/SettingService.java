package pe.pjh.ws.application.service.setting;

import java.util.List;

public class SettingService {


    public static final String NAME_SUGG_PRE_FIX = "#";
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
