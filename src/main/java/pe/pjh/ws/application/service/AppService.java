package pe.pjh.ws.application.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.Service;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.dataset.TermSuggestService;
import pe.pjh.ws.application.service.dataset.TopicMngService;
import pe.pjh.ws.application.service.dataset.WordDicMngService;
import pe.pjh.ws.application.service.setting.DataSourceSetting;
import pe.pjh.ws.application.service.setting.SettingService;
import pe.pjh.ws.application.service.setting.ConnecterSetting;
import pe.pjh.ws.application.service.setting.ConnecterSourceType;

import java.nio.file.Path;
import java.util.List;

@Service
public final class AppService {

    final SettingService settingService;

    final DataSetManager dataSetManager;

    final StatusService statusService;

    TermSuggestService termSuggestService;

    WordDicMngService wordDicMngService;

    TopicMngService topicMngService;

    public static AppService getInstance() {
        return ApplicationManager.getApplication().getService(AppService.class);
    }

    /**
     * 인텔리데이 플러그인 작업 디렉토리 기반으로 서비스클래스 순차적으로 초기화.
     */
    public AppService() {
        this(Path.of(PathManager.getPluginsPath()));

        AppInitializer initializer = this.getInitializer();

        //어플 초기화.
        initializer.startUp();

        //테스트용 번들 데이터 설치.
        initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);
    }

    /**
     * 작업 디렉토리 기반으로 서비스클래스 순차적으로 초기화.
     *
     * @param workPath 작업디렉토리
     */
    public AppService(Path workPath) {

        dataSetManager = new DataSetManager();
        settingService = new SettingService();

        statusService = new StatusService(
                workPath,
                (setting) -> {
                    DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(setting);

                    //변경된 데이터 셋으로
                    wordDicMngService = new WordDicMngService(
                            dataSetManager,
                            getStatusService(),
                            dataSet.wordRepository());

                    termSuggestService = new TermSuggestService(
                            dataSetManager,
                            getStatusService(),
                            dataSet.topicRepository(),
                            dataSet.wordRepository()
                    );
                    topicMngService = new TopicMngService(
                            dataSetManager,
                            getStatusService(),
                            dataSet.topicRepository()
                    );
                }
        );

        String testName = "TestDataSource";
        //Todo 기본 세팅 정보 - 차후 SettingService 로드하는 설정 필요.
        settingService.setDataSetSettings(
                List.of(new DataSourceSetting(
                        testName,
                                new ConnecterSetting(statusService.getWorkPath(), ConnecterSourceType.LocalCouchbaseLite)
                        )
                )
        );

        //Todo 기본 세팅 정보 - 차후 SettingService 로드하는 설정 필요.
        statusService.setCurrentDataSetSetting(settingService.getDataSetSetting(testName));
    }

    public StatusService getStatusService() {
        return statusService;
    }

    public TermSuggestService getTermSuggestService() {
        return termSuggestService;
    }

    public WordDicMngService getWordDicMngService() {
        return wordDicMngService;
    }

    public TopicMngService getTopicMngService() {
        return topicMngService;
    }

    public AppInitializer getInitializer() {
        return new AppInitializer(settingService, dataSetManager, statusService);
    }
}
