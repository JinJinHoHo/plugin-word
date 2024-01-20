package pe.pjh.ws.application.service.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.Service;
import pe.pjh.ws.application.service.BundleDataSet;

import java.util.List;

@Service
public final class SettingService {


    private String workPath;
    private List<DataSetSetting> dataSetSettings;

    public static SettingService getInstance() {
        return ApplicationManager.getApplication().getService(SettingService.class);
    }

    public SettingService() {
        this(PathManager.getPluginsPath());
    }

    public SettingService(String workPath) {
        this.workPath = "%s/wordic".formatted(workPath);
    }

    public void setDataSetSettings(List<DataSetSetting> dataSetSettings) {
        this.dataSetSettings = dataSetSettings;
    }

    public String getWorkPath() {
        return workPath;
    }

    public List<DataSetSetting> getDataSetSettings() {
        return dataSetSettings;
    }
}
