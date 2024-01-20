package pe.pjh.ws.application.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.Service;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;

import java.nio.file.Path;
import java.util.List;

@Service
public final class AppService {

    final SettingService settingService;

    final DataSetManager dataSetManager;

    final StatusService statusService;

    TermSuggestService termSuggestService;

    public static AppService getInstance() {
        return ApplicationManager.getApplication().getService(AppService.class);
    }

    public AppService() {
        this(Path.of(PathManager.getPluginsPath()));
    }

    public AppService(Path workPath) {

        dataSetManager = new DataSetManager();

        settingService = new SettingService();

        statusService = new StatusService(
                workPath, (setting) -> {
            termSuggestService = new TermSuggestService(
                    null,
                    dataSetManager.makeDateSet(setting).wordRepository()
            );
        });

        //Todo 기본 세팅 정보 - 차후 SettingService 로드하는 설정 필요.
        settingService.setDataSetSettings(
                List.of(new DataSetSetting(
                                BundleDataSet.CMN_STN_TRM_6TH.getName(),
                                new DataSetSetting.SourceSetting(statusService.getWorkPath(), SourceType.LocalCouchbaseLite)
                        )
                )
        );

        //Todo 기본 세팅 정보 - 차후 SettingService 로드하는 설정 필요.
        statusService.setCurrentDataSetSetting(settingService.getDataSetSettings().get(0));


    }

    public StatusService getStatusService() {
        return statusService;
    }

    public AppInitialize plugInInitialize() {
        return new AppInitialize(settingService, dataSetManager, statusService);
    }
}
