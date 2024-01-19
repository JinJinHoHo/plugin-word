package pe.pjh.ws.application.service.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public final class SettingService {


    private String homePath;
    private List<DataSetSetting> dataSetSettings;

    public static SettingService getInstance() {
        return ApplicationManager.getApplication().getService(SettingService.class);
    }

    public SettingService() {
        this(PathManager.getPluginsPath(), List.of(new DataSetSetting()));
    }

    public SettingService(String workPath, List<DataSetSetting> dataSetSettings) {
        homePath = "%s/wordic".formatted(workPath);
        this.dataSetSettings = dataSetSettings;
    }


    public String getHomePath() {
        return homePath;
    }

    public List<DataSetSetting> getDataSetSettings() {
        return dataSetSettings;
    }
}
