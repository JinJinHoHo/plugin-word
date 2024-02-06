package pe.pjh.ws.application.service.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingService {


    public static final String NAME_SUGG_PRE_FIX = "#";
    private final Map<String, DataSourceSetting> dataSourceSettingMap = new HashMap<>();

    public SettingService() {
    }

    public void setDataSetSettings(List<DataSourceSetting> dataSourceSettings) {
        dataSourceSettings.forEach(dataSourceSetting -> {
            dataSourceSettingMap.put(dataSourceSetting.getDataSetName(), dataSourceSetting);
        });
    }

    public DataSourceSetting getDataSetSetting(String dataSourceName) {
        return dataSourceSettingMap.get(dataSourceName);
    }
}
