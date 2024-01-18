package pe.pjh.ws.adapter.out.datasource;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import pe.pjh.ws.application.service.setting.DataSourceSetting;

import java.util.HashMap;
import java.util.Map;

@Service
public final class DataSourceFactory {

    private final Map<String, DataSource> repositoryMap = new HashMap<>();

    private static DataSourceFactory getInstance(DataSourceSetting dataSourceSetting) {
        return ApplicationManager.getApplication().getService(DataSourceFactory.class);
    }

}
